package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.chivalrylobby.web.entity.Server;

@Component("serversCacheService")
public class ServersCacheService {
	@Autowired
	private CacheManager cacheManager;

	@PersistenceContext
	private EntityManager entityManager;

	private final String cacheName = "servers";

	private final String listCacheName = "serverList";

	private final Logger log = Logger.getLogger(ServersCacheService.class
			.getSimpleName());

	/**
	 * Adds a server id to the cached servers list
	 * 
	 * @param id
	 */
	private void addToList(long id) throws Exception {
		Cache cache = cacheManager.getCache(cacheName);

		@SuppressWarnings("unchecked")
		Set<Long> ids = (Set<Long>) cache.get(listCacheName).get();

		ids.add(id);

		cache.put(listCacheName, ids);
	}

	/**
	 * Deletes a server from the memcache
	 * 
	 * @param server
	 * @throws Exception
	 */
	public void evict(Server server) throws Exception {
		Cache cache = cacheManager.getCache(cacheName);
		cache.evict(server.getKey().getId());
		removeFromList(server.getKey().getId());
	}

	public Server get(long id) {
		Cache cache = cacheManager.getCache(cacheName);
		return (Server) cache.get(id).get();
	}

	/**
	 * Gets all the servers from the memcache
	 * 
	 * @return
	 */
	public List<Server> getAll() throws Exception {
		Set<Long> ids = getList();
		List<Server> servers = new ArrayList<>();

		for (long id : ids) {
			servers.add(get(id));
		}

		return servers;
	}

	/**
	 * Gets the list of server ID's
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<Long> getList() throws Exception {
		Cache cache = cacheManager.getCache(cacheName);
		ValueWrapper vw = cache.get(listCacheName);
		if (vw == null)
			return new TreeSet<Long>();
		return (Set<Long>) vw.get();
	}

	/**
	 * Puts the server in the memcache
	 * 
	 * @param server
	 * @throws Exception
	 */
	public void put(Server server) throws Exception {
		cacheManager.getCache(cacheName).put(server.getKey().getId(), server);
		addToList(server.getKey().getId());
	}

	/**
	 * Removes a server id to the cached servers list
	 * 
	 * @param id
	 */
	private void removeFromList(long id) throws Exception {
		Cache cache = cacheManager.getCache(cacheName);

		@SuppressWarnings("unchecked")
		Set<Long> ids = (Set<Long>) cache.get(listCacheName).get();

		ids.remove(id);

		cache.put(listCacheName, ids);
	}

	/**
	 * Synchronize the servers with the database
	 */
	public void synchronize() {
		Cache cache = cacheManager.getCache(cacheName);

		Set<Long> inDbIds = new TreeSet<>();
		Set<Long> cachedIds;
		try {
			cachedIds = getList();
		} catch (Exception e) {
			cachedIds = new TreeSet<>();
			log.info("Cache was lost");
		}

		Query query = entityManager.createQuery("SELECT s FROM Server s");
		@SuppressWarnings("unchecked")
		List<Server> servers = (List<Server>) query.getResultList();

		for (Server server : servers) {
			long id = server.getKey().getId();

			inDbIds.add(id);

			if (!cachedIds.contains(id)) {
				cachedIds.add(id);
				cache.put(id, server);
			}
		}

		// Remove servers that are not in the database
		Set<Long> toRemove = cachedIds;
		toRemove.removeAll(inDbIds);

		for (Long id : toRemove) {
			cache.evict(id);
		}

		// Put server id list into the cache
		cache.put(listCacheName, cachedIds);
	}
}
