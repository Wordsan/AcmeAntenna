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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import domain.Actor;
import exceptions.ResourceNotFoundException;
import security.LoginService;
import services.ActorService;
import utilities.ApplicationConfig;
import utilities.ControllerUtils;
import utilities.HttpServletUtils;

@Controller
public class AbstractController {
    private @Autowired ActorService actorService;

    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

    @ModelAttribute("principal")
    public Actor findPrincipal()
    {
        Actor principal = actorService.findPrincipal();
        return principal;
    }

    protected Actor getPrincipal()
    {
        Actor principal = actorService.getPrincipal();
        return principal;
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

    // Returns the current request URI without the query string.
    @ModelAttribute("currentRequestUri")
    public String getCurrentRequestUri()
    {
        return HttpServletUtils.currentRequestUri();
    }

    // Returns the current request URI and query string.
    @ModelAttribute("currentRequestUriAndParams")
    public String getCurrentRequestUriAndParams()
    {
        return HttpServletUtils.currentRequestUriAndParams();
    }

    // Returns the URI that a form, called by the page in this request, should return to. For cancel buttons.
    // First returns the returnAction parameter, if not, if it's a GET request, the current URI and params, else the welcome page.
    @ModelAttribute("returnActionForHere")
    public String getReturnActionForHere()
    {
        String prevReturnAction = HttpServletUtils.getCurrentHttpRequest().getParameter("returnAction");
        if (prevReturnAction != null && !prevReturnAction.isEmpty()) return prevReturnAction;
        if (HttpServletUtils.getCurrentHttpRequest().getMethod().equalsIgnoreCase("GET")) {
            return HttpServletUtils.currentRequestUriAndParams();
        } else {
            return "welcome/index.do";
        }
    }

    @InitBinder
    public void configEmptyStringAsNull(WebDataBinder binder)
    {
        // Tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // Panic handler ----------------------------------------------------------

    @ExceptionHandler(Throwable.class)
    public ModelAndView panic(Throwable oops, HttpServletRequest request)
    {
        // We throw this exception ourselves and expect it to be handled gracefully, so do so.
        if (oops instanceof AccessDeniedException) {
            return handleAccessDeniedException(request);
        }
        if (oops instanceof ResourceNotFoundException) {
            return handle404(request);
        }

        ModelAndView result;

        result = new ModelAndView("misc/panic");
        result.addObject("name", ClassUtils.getShortName(oops.getClass()));
        result.addObject("exception", oops.getMessage());
        result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

        if (ApplicationConfig.DEBUG) oops.printStackTrace();

        return result;
    }

    private ModelAndView handle404(HttpServletRequest request)
    {
        return new ModelAndView("misc/404");
    }

    private ModelAndView handleAccessDeniedException(HttpServletRequest request)
    {
        if (!LoginService.isAuthenticated()) {
            // If we are not authenticated, redirect to login page, and save the current request
            // so that Spring will replay it on authentication success.
            // Only save the request if it's a GET request. Otherwise the user must do it again.
            if (request.getMethod().equalsIgnoreCase("GET")) {
                requestCache.saveRequest(request, null);
            }
            return ControllerUtils.redirect("/security/login.do");
        } else {
            // Else the user just doesn't have permissions to do this.
            return new ModelAndView("misc/403");
        }
    }
}
