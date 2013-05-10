package com.chivalrylobby.web.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chivalrylobby.web.entity.Server;
import com.chivalrylobby.web.json.support.RegisterServer;
import com.chivalrylobby.web.json.support.ResponseMessage;
import com.chivalrylobby.web.service.ServersService;

@Controller
@RequestMapping("/clapi")
public class ClientApiController {
	private static final Logger log = Logger
			.getLogger(ClientApiController.class.getName());

	@Autowired
	ServersService serversService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public @ResponseBody
	ResponseMessage register(
			@RequestParam(required = true, value = "q") String query)
			throws Exception {

		try {
			ObjectMapper mapper = new ObjectMapper();
			RegisterServer registerServer = mapper.readValue(query,
					RegisterServer.class);
			Server server = serversService.registerServer(registerServer);
			log.info("New server registered! id:" + server.getKey().getId());
		} catch (IOException e) {
			log.log(Level.WARNING, "Wrongly formatted registering: " + query, e);
			return new ResponseMessage(false,
					"Are you kidding me? bad format... try harder!");
		} catch (Exception e) {
			return new ResponseMessage(false, "Server registration failed!");
		}
		return new ResponseMessage(true, "Server successfully registered!");
	}
}
