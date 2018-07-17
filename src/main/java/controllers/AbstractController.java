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
import org.springframework.http.HttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import domain.User;
import security.LoginService;
import services.ActorService;
import services.UserService;
import utilities.ApplicationConfig;
import utilities.ControllerUtils;

@Controller
public class AbstractController {
	private @Autowired ActorService actorService;
	private @Autowired UserService userService;

	private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

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
		return ApplicationConfig.DISPLAYTAG_PAGE_SIZE;
	}

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(Throwable oops, HttpServletRequest request)
	{
		// We throw this exception ourselves and expect it to be handled gracefully, so do so.
		if (oops instanceof AccessDeniedException) {
			return handleAccessDeniedException(request);
		}

		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	private ModelAndView handleAccessDeniedException(HttpServletRequest request)
	{
		if (!LoginService.isAuthenticated()) {
			// If we are not authenticated, redirect to login page, and save the current request
			// so that Spring will replay it on authentication success.
			requestCache.saveRequest(request, null);
			return ControllerUtils.redirect("/security/login.do");
		} else {
			// Else the user just doesn't have permissions to do this.
			return new ModelAndView("misc/403");
		}
	}
}
