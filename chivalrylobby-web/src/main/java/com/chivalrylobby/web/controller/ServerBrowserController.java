package com.chivalrylobby.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.service.ServersService;

@Controller
public class ServerBrowserController {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	ServersService serversService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("serverbrowser/serverbrowser");

		List<Server> servers = serversService.getOnlineServers();
		mav.addObject(servers);

		return mav;
	}

	@RequestMapping("/test")
	public void test() {
		Cache cache = cacheManager.getCache("default");
		
		cache.put("asdasdad", "hellobello");
		String asd = (String) cache.get("asdasdad").get();
		System.out.println(asd);
	}
}
