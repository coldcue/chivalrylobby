package com.chivalrylobby.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/howto")
public class HowtoController {

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("howto/index");
		return mav;
	}

	@RequestMapping("/install")
	public ModelAndView install() {
		ModelAndView mav = new ModelAndView("howto/install");
		return mav;
	}

	@RequestMapping("/connect")
	public ModelAndView connect() {
		ModelAndView mav = new ModelAndView("howto/connect");
		return mav;
	}

	@RequestMapping("/createserver")
	public ModelAndView createserver() {
		ModelAndView mav = new ModelAndView("howto/createserver");
		return mav;
	}
}
