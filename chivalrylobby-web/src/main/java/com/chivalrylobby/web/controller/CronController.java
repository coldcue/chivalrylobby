package com.chivalrylobby.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chivalrylobby.web.service.ServersService;

@Controller
@RequestMapping(value = "/cron", headers = { "X-AppEngine-Cron: true" })
// @RequestMapping(value = "/cron")
public class CronController {

	@Autowired
	ServersService serversService;

	@RequestMapping("deleteStaleServers")
	public @ResponseBody
	String deleteStaleServers() {
		serversService.deleteStaleServers();
		return "OK";
	}

}
