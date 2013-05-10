package com.chivalrylobby.web.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.service.ServersService;

@Controller
public class ServerBrowserController {
	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(ServerBrowserController.class.getName());

	@Autowired
	ServersService serversService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("serverbrowser/serverbrowser");

		List<Server> servers = serversService.getOnlineServers();
		mav.addObject(servers);

		return mav;
	}

	// @RequestMapping("/test")
	// public void test() {
	// serversService.test();
	// log.info("Test called...");
	// }
}
