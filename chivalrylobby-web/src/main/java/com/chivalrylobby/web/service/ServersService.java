package com.chivalrylobby.web.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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

	@Transactional
	public List<Server> getPublicServers() {
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<Server> servers = em.createNamedQuery("getPublicServers")
				.getResultList();

		em.close();
		return servers;
	}

	@Transactional
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
