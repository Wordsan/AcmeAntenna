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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import domain.User;
import security.LoginService;
import services.ActorService;
import services.BannerService;
import utilities.ControllerUtils;
import utilities.UserAccountUtils;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
    @RequestMapping(value = "/index")
    public ModelAndView index() throws LoginException
    {
        return new ModelAndView("welcome/index");
    }

    @RequestMapping(value = "/blocked")
    public ModelAndView blocked()
    {
        Actor principal = findPrincipal();
        if (principal.isBanned()) {
            ModelAndView result = new ModelAndView("welcome/blocked");

            // Log user out.
            UserAccountUtils.setSessionAccount(null);
            return result;
        }

        // If not actually blocked, redirect to index.
        return ControllerUtils.redirect("/welcome/index.do");
    }
}
