package com.chivalrylobby.web.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.google.appengine.api.datastore.Key;

public class ServersService implements ApplicationContextAware {

	ApplicationContext context;

	public ServersService() {
		System.out.println("!!!!!!!!!!!!!!!!!!! constructor called");
	}

	/**
	 * Gets a server by Key
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Server getServer(Key id) {
		EntityManager em = (EntityManager) context.getBean("entityManager");
		return em.find(Server.class, id);
	}

	@Transactional
	public void test() {
		EntityManager em = (EntityManager) context.getBean("entityManager");

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Server jdo = new Server();
		jdo.setCountry("HU");
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
		em.persist(jdo);

		tx.commit();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
}
