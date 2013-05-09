package com.chivalrylobby.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.google.appengine.api.datastore.Key;

public class ServersService {
	EntityManagerFactory emf;

	public ServersService() {
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Gets a server by Key
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Server getServer(Key id) {
		EntityManager em = emf.createEntityManager();
		em.close();
		return em.find(Server.class, id);
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value = "short", key = "#root.methodName")
	@Transactional
	public List<Server> getOnlineServers() {
		EntityManager em = emf.createEntityManager();
		List<Server> servers = new ArrayList<>();

		Query query = em
				.createQuery("SELECT s FROM Server s WHERE s.online = true");

		for (Server temp : (List<Server>) query.getResultList()) {
			servers.add(temp);
		}

		em.close();
		return servers;
	}

	public void test() {
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Server jdo = new Server();
		jdo.setCountry("de");
		jdo.setIp("127.14.124.64");
		jdo.setLastonline(new Date());
		jdo.setLastupdate(new Date());
		jdo.setOnline(true);
		jdo.setPlayers(32);
		jdo.setPort(6547);
		jdo.setSlot(45);
		jdo.setTunngle(false);
		jdo.setGamemode(ServerGamemodes.AOCTD);
		jdo.setMap(ServerMaps.MOOR);
		jdo.setName("[ASD] Clan's Chivalry - NO BLA/AH/BL/AH");
		em.persist(jdo);

		tx.commit();
		em.close();
	}
}
