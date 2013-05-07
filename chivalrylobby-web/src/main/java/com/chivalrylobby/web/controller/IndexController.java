package com.chivalrylobby.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.service.ServersService;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
public class IndexController {

	@Autowired
	ServersService serversService;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index/index");

		serversService.test();
		Server srv = serversService.getServer(KeyFactory
				.createKey("Server", 24));

		System.out.println(srv);

		return mav;
	}
}
