package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import domain.Antenna;
import domain.Handyworker;
import domain.MaintenanceRequest;
import domain.User;
import services.HandyworkerService;
import services.MaintenanceRequestService;
import services.UserService;

@Controller
@RequestMapping("/maintenanceRequests/user")
public class MaintenanceRequestUserController extends AbstractController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private HandyworkerService handyworkerService;


    public MaintenanceRequestUserController()
    {
        super();
    }

    @RequestMapping(value = "/listNotServiced", method = RequestMethod.GET)
    public ModelAndView listNotServiced()
    {
        ModelAndView result;
        final User user = this.userService.findPrincipal();
        final Collection<MaintenanceRequest> maintenanceRequests = this.userService.findNotServedMainteinanceRequest(user);

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/user/listNotServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);

        return result;
    }

    @RequestMapping(value = "/listServiced", method = RequestMethod.GET)
    public ModelAndView listServiced()
    {
        ModelAndView result;
        final User user = this.userService.findPrincipal();

        final Collection<MaintenanceRequest> maintenanceRequests = this.userService.findServedMainteinanceRequest(user);
        final boolean done = true;

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/user/listServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);
        result.addObject("done", done);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@CookieValue(required = false) final String creditCard)
    {
        ModelAndView result;
        MaintenanceRequest maintenanceRequest;

        maintenanceRequest = this.maintenanceRequestService.create();
        maintenanceRequest.setCreditCard(creditCard);
        result = this.createEditModelAndView(maintenanceRequest);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest)
    {
        ModelAndView result;

        result = this.createEditModelAndView(maintenanceRequest, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest, final String message)
    {
        ModelAndView result;
        final User u = this.userService.findPrincipal();
        final Collection<Handyworker> handyworkers = this.handyworkerService.findAll();
        final Collection<Antenna> antennas = u.getAntennas();

        result = new ModelAndView("maintenanceRequests/create");
        result.addObject("maintenanceRequest", maintenanceRequest);
        result.addObject("handyworkers", handyworkers);
        result.addObject("antennas", antennas);

        result.addObject("message", message);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final MaintenanceRequest maintenanceRequest, final BindingResult binding, final HttpServletResponse response)
    {
        ModelAndView result;

        if (binding.hasErrors()) {
            System.out.println(binding.getAllErrors());
            result = this.createEditModelAndView(maintenanceRequest);
        } else {
            try {
                this.maintenanceRequestService.save(maintenanceRequest);
                result = new ModelAndView("redirect:listNotServiced.do");
                response.addCookie(new Cookie("creditCard", maintenanceRequest.getCreditCard()));
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(maintenanceRequest, "maintenanceRequest.commit.error");
            }
        }

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(final MaintenanceRequest maintenanceRequest, final BindingResult binding)
    {
        ModelAndView result;

        try {
            this.maintenanceRequestService.delete(maintenanceRequest);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(maintenanceRequest, "maintenanceRequest.commit.error");
        }

        return result;
    }
}
