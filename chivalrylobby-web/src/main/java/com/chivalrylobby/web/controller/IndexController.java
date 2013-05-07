package com.chivalrylobby.web.controller;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chivalrylobby.web.jpa.ServerJDO;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index/index");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("datastore");
		EntityManager em = emf.createEntityManager();
		
		ServerJDO jdo = new ServerJDO();
		jdo.setCountry("HU");
		jdo.setIp("127.14.124.64");
		jdo.setLastonline(new Date());
		jdo.setLastupdate(new Date());
		jdo.setOnline(true);
		jdo.setPlayers((byte)32);
		jdo.setPort(6547);
		jdo.setSlot((byte)45);
		jdo.setTunngle(false);
		
		em.persist(jdo);
		
		return mav;
	}
}
