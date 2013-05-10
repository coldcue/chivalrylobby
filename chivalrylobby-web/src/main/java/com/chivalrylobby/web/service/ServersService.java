package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.chivalrylobby.web.entity.Server;
import com.google.appengine.api.datastore.Key;

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

			// Evict online servers cache
			Cache cache = cacheManager.getCache(cacheName);
			cache.put(server.getKey().getId(), server);
			cache.evict("getOnlineServers");
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
	public Server getServerFromCache(Key id) throws Exception {
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
		EntityManager em = entityManagerFactory.createEntityManager();
		List<Server> servers = new ArrayList<>();

		Query query = em
				.createQuery("SELECT s FROM Server s WHERE s.online = true");

		for (Server temp : (List<Server>) query.getResultList()) {
			servers.add(temp);
		}

		em.close();
		return servers;
	}
}
