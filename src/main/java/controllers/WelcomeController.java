/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BannerService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	ActorService	actorService;
	@Autowired
	BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name, final HttpServletRequest request, final HttpServletResponse response) throws LoginException {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		result = new ModelAndView("welcome/index");
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		if (LoginService.isAuthenticated() && this.actorService.getPrincipal().isBanned()) {

			final Actor u = this.actorService.getPrincipal();
			final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null)
				new SecurityContextLogoutHandler().logout(request, response, auth);
			result = new ModelAndView("redirect:welcome/index.do?blocked=" + u.isBanned());
			result.addObject("name", name);
			result.addObject("moment", moment);

		}

		return result;
	}

}
