package com.chivalrylobby.web.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chivalrylobby.web.service.ServersService;

/**
 * The Client API Controller which communicates with the announcer client
 * 
 * @author Andrew
 * 
 */
@Controller
@RequestMapping("/clapi")
public class ClientApiController {
	private static final Logger log = Logger
			.getLogger(ClientApiController.class.getName());

	@Autowired
	ServersService serversService;

}
