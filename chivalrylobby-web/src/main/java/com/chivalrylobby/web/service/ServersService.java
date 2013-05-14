package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.chivalrylobby.web.clapi.RefreshServerData;
import com.chivalrylobby.web.clapi.RegisterServerData;
import com.chivalrylobby.web.clapi.RemoveServerData;
import com.chivalrylobby.web.entity.Server;
import com.google.appengine.api.datastore.KeyFactory;

@Component("serversService")
public class ServersService {
	private final Logger log = Logger.getLogger(ServersService.class
			.getSimpleName());

	@Autowired
	private CacheManager cacheManager;

	private final String cacheName = "servers";

	@Autowired
	private WebApplicationContext context;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Transactional
	public void deleteStaleServers() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -6);

		int deleted = entityManager
				.createQuery("DELETE FROM Server s WHERE s.lastupdate < :date")
				.setParameter("date", cal.getTimeInMillis()).executeUpdate();

		if (deleted > 0) {
			cacheManager.getCache(cacheName).evict("cachedServers");
			cacheManager.getCache(cacheName).evict("getOnlineServers");
		}
	}

	/**
	 * Get Online servers (it has a protective cache)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Cacheable(value = cacheName, key = "#root.methodName")
	public List<Server> getOnlineServers() {
		List<Server> servers = new ArrayList<>();
		Cache cache = cacheManager.getCache(cacheName);

		// First, try the cache
		try {
			Set<Long> cachedServers = (Set<Long>) cache.get("cachedServers")
					.get();

			for (Long id : cachedServers) {
				servers.add(getServerFromCache(id));
			}

			refreshStatics(servers);

			return servers;
		} catch (Exception e) {
			// Do nothing
		}

		Query query = entityManager
				.createQuery("SELECT s FROM Server s WHERE s.online = true");

		Set<Long> cachedServers = new TreeSet<>();
		for (Server temp : (List<Server>) query.getResultList()) {
			servers.add(temp);

			// Add it to the cache
			Long id = temp.getKey().getId();
			cache.put(id, temp);
			cachedServers.add(id);
		}

		// Put server id list into the cache
		cache.put("cachedServers", cachedServers);

		refreshStatics(servers);

		return servers;
	}

	/**
	 * Gets a server by Key
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@CachePut(value = cacheName, key = "#id")
	public Server getServer(long id) throws Exception {
		try {
			Server ret = entityManager.find(Server.class,
					KeyFactory.createKey(Server.class.getSimpleName(), id));

			// Evict online servers cache
			cacheManager.getCache(cacheName).evict("getOnlineServers");
			return ret;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets a server from the database by IP and PORT
	 * 
	 * @param ip
	 * @param port
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Server getServer(String ip, int port) {
		try {
			Server server = (Server) entityManager
					.createQuery(
							"SELECT s FROM Server s WHERE s.ip = :ip AND s.port = :port")
					.setParameter("ip", ip).setParameter("port", port)
					.getSingleResult();

			// Add server to cache
			putServerInCache(server);

			return server;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets server from cache and if its not there, then refreshes it.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = cacheName, key = "#id")
	private Server getServerFromCache(long id) throws Exception {
		return getServer(id);
	}

	/**
	 * Adds a server to the cache or if its already there, it will overwrite it
	 * 
	 * @param server
	 */
	@SuppressWarnings("unchecked")
	private void putServerInCache(Server server) {
		Cache cache = cacheManager.getCache(cacheName);

		Set<Long> cachedServers = null;
		ValueWrapper object = cache.get("cachedServers");
		if (object != null)
			cachedServers = (Set<Long>) object.get();

		cache.put(server.getKey().getId(), server);

		// If its in the list, then don't upload the list again
		if (cachedServers != null
				&& !cachedServers.contains(server.getKey().getId())) {
			cachedServers.add(server.getKey().getId());
			cache.put("cachedServers", cachedServers);
		}

		cache.evict("getOnlineServers");
	}

	@Transactional
	public Server refresh(RefreshServerData data) throws Exception {
		Server server = null;
		try {
			// Get the server
			server = getServer(data.getId());
		} catch (Exception e) {
			throw e;
		}

		server.setMap(data.getMaps());
		server.setGamemode(data.getGamemode());

		// If the current players are more then the slot, it will be max
		if (data.getPlayers() > server.getSlot())
			server.setPlayers(server.getSlot());
		else
			server.setPlayers(data.getPlayers());

		server.setOnline(true);
		server.setLastupdate(Calendar.getInstance().getTimeInMillis());

		putServerInCache(server);

		return server;
	}

	// public void test() {
	// EntityManager em = entityManagerFactory.createEntityManager();
	// em.getTransaction().begin();
	//
	// Server server = new Server();
	// server.setCountry("de");
	// server.setGamemode(ServerGamemodes.AOCKOTH);
	// server.setMap(ServerMaps.DARKFOREST);
	// server.setIp("124.123.235.165");
	// server.setPort(7777);
	// server.setOnline(true);
	// server.setSlot(64);
	// server.setPlayers(21);
	// server.setName("[AS] Asdblah lorem ipsum BA/HG/W");
	// server.setTunngle(false);
	// server.setLastonline(new Date());
	// server.setLastupdate(new Date());
	// em.persist(server);
	// em.getTransaction().commit();
	// em.close();
	//
	// putServerInCache(server);
	// }

	/**
	 * Refreshes the global statics
	 * 
	 * @param servers
	 */
	private void refreshStatics(List<Server> servers) {
		context.getServletContext().setAttribute("global_serverCount",
				servers.size());

		int players = 0;
		for (Server server : servers)
			players += server.getPlayers();

		context.getServletContext().setAttribute("global_playerCount", players);
	}

	@Transactional
	public Server register(RegisterServerData data, String country) {

		// If its already registered, then return the existing
		// try {
		// Server temp = getServer(data.getIp(), data.getPort());
		// removeServerFromCache(temp);
		// entityManager.remove(temp);
		// } catch (Exception e) {
		// throw e;
		// }
		log.info("Server registering: " + data);

		Server server = data.createServer();
		server.setLastupdate(Calendar.getInstance().getTimeInMillis());
		server.setOnline(false);
		server.setCountry(country);

		// TODO test IP if not tunngle
		server.setIp(data.getIp());

		// Persist the new server
		entityManager.persist(server);

		return server;
	}

	@Transactional
	public void remove(RemoveServerData data) throws Exception {
		// Get the server
		Server server = getServer(data.getId());

		// Remove from cache
		removeServerFromCache(server);

		entityManager.remove(server);
	}

	@SuppressWarnings("unchecked")
	private void removeServerFromCache(Server server) {
		Cache cache = cacheManager.getCache(cacheName);

		Set<Long> cachedServers = null;
		ValueWrapper object = cache.get("cachedServers");
		if (object != null)
			cachedServers = (Set<Long>) object.get();

		cache.evict(server.getKey().getId());

		// If it isn't in the list, then don't upload the list again
		if (cachedServers != null
				&& cachedServers.contains(server.getKey().getId())) {
			cachedServers.remove(server.getKey().getId());
			cache.put("cachedServers", cachedServers);
		}

		cache.evict("getOnlineServers");
	}
}
