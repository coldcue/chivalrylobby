package com.chivalrylobby.web.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chivalrylobby.web.clapi.RefreshServerData;
import com.chivalrylobby.web.clapi.RefreshServerDataValidator;
import com.chivalrylobby.web.clapi.RegisterServerData;
import com.chivalrylobby.web.clapi.RegisterServerDataValidator;
import com.chivalrylobby.web.clapi.RemoveServerData;
import com.chivalrylobby.web.clapi.ResponseMessage;
import com.chivalrylobby.web.entity.Server;
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

	@RequestMapping(value = "register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	ResponseMessage register(@RequestBody RegisterServerData data,
			Errors errors, HttpServletRequest request) {
		try {

			// Validate it
			ValidationUtils.invokeValidator(new RegisterServerDataValidator(),
					data, errors);
			if (errors.hasErrors())
				throw new Exception();

			String country = "";
			if (request.getHeader("X-AppEngine-Country") != null)
				country = request.getHeader("X-AppEngine-Country")
						.toLowerCase();

			Server server = serversService.register(data, country);
			log.info("Server registered: id:" + server.getKey().getId());
			return new ResponseMessage(true, "Server successfully registered!",
					server.getKey().getId());
		} catch (Exception e) {
			log.log(Level.WARNING, "Server registration error", e);
			return new ResponseMessage(false,
					"Server registration was unsuccessful");
		}
	}

	@RequestMapping(value = "refresh", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	ResponseMessage refresh(@RequestBody RefreshServerData data, Errors errors) {
		try {
			// Validate it
			ValidationUtils.invokeValidator(new RefreshServerDataValidator(),
					data, errors);
			if (errors.hasErrors())
				throw new Exception();

			serversService.refresh(data);
			return new ResponseMessage(true, "Server successfully refreshed!");
		} catch (Exception e) {
			log.log(Level.WARNING, "Server refresh error", e);
			return new ResponseMessage(false, "Server refresh was unsuccessful");
		}
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	ResponseMessage remove(@RequestBody RemoveServerData data, Errors errors) {
		try {
			// Validate it
			ValidationUtils.invokeValidator(new RegisterServerDataValidator(),
					data, errors);
			if (errors.hasErrors())
				throw new Exception();

			serversService.remove(data);
			return new ResponseMessage(true, "Server successfully removed!");
		} catch (Exception e) {
			log.log(Level.WARNING, "Server remove error", e);
			return new ResponseMessage(false, "Server removal was unsuccessful");
		}
	}

}
