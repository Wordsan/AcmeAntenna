/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.User;
import services.ActorService;
import services.UserService;

@Controller
public class AbstractController {
	private @Autowired ActorService actorService;
	private @Autowired UserService userService;

	protected Actor getPrincipal()
	{
		return actorService.getPrincipal();
	}

	protected User getPrincipalUser()
	{
		return userService.getPrincipal();
	}

	@ModelAttribute("locale")
	public String getLocale()
	{
		return LocaleContextHolder.getLocale().toLanguageTag();
	}

	@ModelAttribute("displayTagPageSize")
	public int getDisplayTagPageSize()
	{
		return 5;
	}

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(Throwable oops)
	{
		if (oops instanceof AccessDeniedException) {
			// This clutters the console but is the only way
			// to pass the exception back to spring security
			// so it can do the proper action (send to login
			// screen if not logged in, show 403 otherwise)
			//
			throw (AccessDeniedException) oops;
		}

		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

}
