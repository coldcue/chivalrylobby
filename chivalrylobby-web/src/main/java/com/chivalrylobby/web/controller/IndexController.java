package com.chivalrylobby.web.controller;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.chivalrylobby.web.jpa.Server;

@Controller
public class IndexController {

	@Autowired
	WebApplicationContext wac;

	@RequestMapping("/index")
	@Transactional
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index/index");

		EntityManager em = (EntityManager) wac.getBean("entityManager");
		
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Server jdo = new Server();
		jdo.setCountry("HU");
		jdo.setIp("127.14.124.64");
		jdo.setLastonline(new Date());
		jdo.setLastupdate(new Date());
		jdo.setOnline(true);
		jdo.setPlayers((byte) 32);
		jdo.setPort(6547);
		jdo.setSlot((byte) 45);
		jdo.setTunngle(false);

		em.persist(jdo);

		etx.commit();
		em.close();

		return mav;
	}
}
