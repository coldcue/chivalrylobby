package com.chivalrylobby.web.test.service;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;

import com.chivalrylobby.web.cache.MemcacheCache;
import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.chivalrylobby.web.service.ServersCacheService;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ServersCacheServiceTest {
	Logger log = Logger
			.getLogger(ServersCacheServiceTest.class.getSimpleName());

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalMemcacheServiceTestConfig());

	private ServersCacheService serversCacheService;

	@Before
	public void init() throws Exception {
		helper.setUp();

		SimpleCacheManager cacheManager = new SimpleCacheManager();

		List<Cache> cacheList = new ArrayList<>();
		MemcacheCache cache = new MemcacheCache();
		cache.setName("servers");
		cache.setTimeout(3600);
		cache.afterPropertiesSet();
		cacheList.add(cache);

		cacheManager.setCaches(cacheList);
		cacheManager.afterPropertiesSet();

		serversCacheService = new ServersCacheService();
		Field cacheManagerField = ServersCacheService.class
				.getDeclaredField("cacheManager");
		cacheManagerField.setAccessible(true);
		cacheManagerField.set(serversCacheService, cacheManager);
	}

	@After
	public void destroy() {
		helper.tearDown();
	}

	private Server getTestServer(long id) {
		Server server = new Server();
		server.setCountry("HU");
		server.setIp("12.145.124.31");
		server.setKey(KeyFactory.createKey("Server", id));
		server.setLastupdate(Calendar.getInstance().getTimeInMillis());
		server.setMap(ServerMaps.ARENA3);
		server.setGamemode(ServerGamemodes.AOCKOTH);
		server.setTunngle(false);
		server.setName("asdasdasd");
		server.setPlayers(32);
		server.setSlot(64);
		return server;
	}

	public void test1() throws Exception {
		log.info("Testing Put-Get-Evict in Memcache");

		// Put
		Server server = getTestServer(100);
		serversCacheService.put(server);

		// Get
		assertNotNull(serversCacheService.get(100));

		// Evict
		serversCacheService.evict(server);
		Server asd = serversCacheService.get(100);
		assertNull(asd);
	}
}
