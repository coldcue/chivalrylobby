package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.chivalrylobby.web.clapi.RefreshServerData;
import com.chivalrylobby.web.clapi.RegisterServerData;
import com.chivalrylobby.web.clapi.RemoveServerData;
import com.chivalrylobby.web.entity.Server;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ServersService {
	private final String cacheName = "servers";

	private EntityManagerFactory entityManagerFactory;
	private CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * Gets a server by Key
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@CachePut(value = cacheName, key = "#id.id")
	public Server getServer(Key id) throws Exception {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Server ret = em.find(Server.class, id);

			// Evict online servers cache
			cacheManager.getCache(cacheName).evict("getOnlineServers");
			return ret;
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
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
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Server server = (Server) em
					.createQuery(
							"SELECT s FROM Server s WHERE s.ip = :ip AND s.port = :port")
					.setParameter("ip", ip).setParameter("port", port)
					.getSingleResult();

			// Add server to cache
			putServerInCache(server);

			return server;
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}
	}

	/**
	 * Gets server from cache and if its not there, then refreshes it.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = cacheName, key = "#id.id")
	private Server getServerFromCache(Key id) throws Exception {
		return getServer(id);
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
				servers.add(getServerFromCache(KeyFactory.createKey(
						Server.class.getName(), id)));
			}

			return servers;
		} catch (Exception e) {
			// Do nothing
		}

		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em
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

		em.close();
		return servers;
	}

	/**
	 * Adds a server to the cache or if its already there, it will overwrite it
	 * 
	 * @param server
	 */
	private void putServerInCache(Server server) {
		Cache cache = cacheManager.getCache(cacheName);

		@SuppressWarnings("unchecked")
		Set<Long> cachedServers = (Set<Long>) cache.get("cachedServers").get();

		cache.put(server.getKey().getId(), server);

		// If its in the list, then don't upload the list again
		if (!cachedServers.contains(server.getKey().getId())) {
			cachedServers.add(server.getKey().getId());
			cache.put("cachedServers", cachedServers);
		}

		cache.evict("getOnlineServers");
	}

	private void removeServerFromCache(Server server) {
		Cache cache = cacheManager.getCache(cacheName);

		@SuppressWarnings("unchecked")
		Set<Long> cachedServers = (Set<Long>) cache.get("cachedServers").get();

		cache.evict(server.getKey().getId());

		// If it isn't in the list, then don't upload the list again
		if (!cachedServers.contains(server.getKey().getId())) {
			cachedServers.remove(server.getKey().getId());
			cache.put("cachedServers", cachedServers);
		}

		cache.evict("getOnlineServers");
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

	public Server register(RegisterServerData data) {

		// If its already registered, then return the existing
		try {
			Server server = getServer(data.getIp(), data.getPort());
			return server;
		} catch (Exception e) {
			// Do nothing
		}

		Server server = data.createServer();
		server.setLastonline(new Date());
		server.setLastupdate(new Date());
		server.setOnline(false);

		// TODO test IP if not tunngle

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			// Persist the new server
			em.persist(server);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		return server;
	}

	public Server refresh(RefreshServerData data) throws Exception {

		// Get the server
		Server server = getServer(KeyFactory.createKey(Server.class.getName(),
				data.getId()));

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			server.setMap(data.getMaps());
			server.setGamemode(data.getGamemode());

			// If the current players are more then the slot, it will be max
			if (data.getPlayers() > server.getSlot())
				server.setPlayers(server.getSlot());
			else
				server.setPlayers(data.getPlayers());

			server.setOnline(true);
			server.setLastupdate(new Date());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		putServerInCache(server);

		return server;
	}

	public void remove(RemoveServerData data) throws Exception {
		// Get the server
		Server server = getServer(KeyFactory.createKey(Server.class.getName(),
				data.getId()));

		// Remove from cache
		removeServerFromCache(server);

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.remove(server);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

	}
}
