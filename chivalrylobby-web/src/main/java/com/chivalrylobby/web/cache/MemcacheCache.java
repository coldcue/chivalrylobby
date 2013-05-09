package com.chivalrylobby.web.cache;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemcacheCache implements Cache {

	private AsyncMemcacheService ams;
	private Expiration exp;

	public MemcacheCache() {
		ams = MemcacheServiceFactory.getAsyncMemcacheService("default");
		ams.setErrorHandler(ErrorHandlers
				.getConsistentLogAndContinue(Level.INFO));
		exp = Expiration.byDeltaSeconds(3600);
	}

	@Override
	public String getName() {
		return ams.getNamespace();
	}

	@Override
	public Object getNativeCache() {
		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		try {
			return new SimpleValueWrapper(ams.get(key).get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleValueWrapper(null);
	}

	@Override
	public void put(Object key, Object value) {
		ams.put(key, value, exp);
	}

	@Override
	public void evict(Object key) {
		ams.delete(key);
	}

	@Override
	public void clear() {
		ams.clearAll();
	}

}
