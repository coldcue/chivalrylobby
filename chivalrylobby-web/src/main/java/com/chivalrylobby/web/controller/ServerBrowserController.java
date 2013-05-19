package com.chivalrylobby.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
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

	@Autowired
	WebApplicationContext context;

	@RequestMapping("/")
	public ModelAndView index() throws Exception {
		ModelAndView mav = new ModelAndView("serverbrowser/serverbrowser");

		List<Server> servers = serversService.getOnlineServers();

		// Sort by players
		Collections.sort(servers, Server.compareByPlayers);
		Collections.reverse(servers);

		mav.addObject(servers);
		mav.addObject("serversCount", servers.size());

		return mav;
	}

	/**
	 * There are some external links which directs to this page, now its
	 * redirecting to the main page
	 * 
	 * @return
	 */
	@RequestMapping("/all")
	public String all() {
		return "redirect:/";
	}

	// @RequestMapping("/test")
	// public @ResponseBody
	// String test(HttpServletRequest request) {
	// String ret = new String();
	//
	// ret += request.toString();
	//
	// log.info("Test called...");
	// return ret;
	// }
}
