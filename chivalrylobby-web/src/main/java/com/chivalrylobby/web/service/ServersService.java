package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
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
	private final String cacheName = "servers";

	@Autowired
	private WebApplicationContext context;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private final Logger log = Logger.getLogger(ServersService.class
			.getSimpleName());

	@Autowired
	ServersCacheManager serversCacheManager;

	/**
	 * Delete servers that has exceeded the maximum refresh time
	 */
	@Transactional
	public void deleteStaleServers() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -6);

			List<Server> servers = serversCacheManager.getAll();

			for (Server server : servers) {
				if (server.getLastupdate() < cal.getTimeInMillis())
					remove(server);
			}
		} catch (Exception e) {
			serversCacheManager.synchronize();
		}
	}

	/**
	 * Get Online servers (it has a protective cache)
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Server> getOnlineServers() throws Exception {
		List<Server> servers = new ArrayList<>();

		// First, try the cache
		try {
			servers = serversCacheManager.getAll();
		} catch (Exception e) {
			serversCacheManager.synchronize();
			servers = serversCacheManager.getAll();
		}

		// TODO do some sorting

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
	@CachePut(value = cacheName, key = "#id")
	public Server getServer(long id) throws Exception {
		try {
			Server ret = entityManager.find(Server.class,
					KeyFactory.createKey(Server.class.getSimpleName(), id));

			serversCacheManager.put(ret);
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
			return server;
		} catch (Exception e) {
			throw e;
		}
	}

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

		server.setLastupdate(Calendar.getInstance().getTimeInMillis());

		serversCacheManager.put(server);

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
		server.setCountry(country);

		// TODO test IP if not tunngle
		server.setIp(data.getIp());

		// Persist the new server
		entityManager.persist(server);

		return server;
	}

	/**
	 * Remove server from everywhere
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Transactional
	public void remove(RemoveServerData data) throws Exception {
		// Get the server
		Server server = getServer(data.getId());

		// Remove from cache
		serversCacheManager.evict(server);

		// Remove from database
		entityManager.remove(server);
	}

	/**
	 * Remove server from everywhere
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Transactional
	public void remove(Server server) throws Exception {

		// Remove from cache
		serversCacheManager.evict(server);

		// Remove from database
		entityManager.remove(server);
	}

}
