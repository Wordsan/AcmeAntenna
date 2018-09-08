package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import javax.validation.Valid;

import domain.Handyworker;
import domain.MaintenanceRequest;
import security.Authority;
import services.HandyworkerService;
import services.MaintenanceRequestService;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/maintenanceRequests/handyworker")
public class MaintenanceRequestHandyworkerController extends AbstractController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;
    @Autowired
    private HandyworkerService handyworkerService;


    public MaintenanceRequestHandyworkerController()
    {
        super();
    }

    @RequestMapping("/listNotServiced")
    public ModelAndView listNotServiced()
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);

        ModelAndView result;
        final Handyworker handyworker = this.handyworkerService.findPrincipal();

        final Collection<MaintenanceRequest> maintenanceRequests = this.handyworkerService.findNotServedMainteinanceRequest(handyworker);
        final boolean check = true;

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/handyworker/listNotServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);
        result.addObject("check", check);

        return result;
    }

    @RequestMapping("/listServiced")
    public ModelAndView listServiced()
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);

        ModelAndView result;
        final Handyworker handyworker = this.handyworkerService.findPrincipal();

        final Collection<MaintenanceRequest> maintenanceRequests = this.handyworkerService.findServedMainteinanceRequest(handyworker);
        final boolean done = true;

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/handyworker/listServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);
        result.addObject("done", done);

        return result;
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ModelAndView service(final int maintenanceRequestId)
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);

        ModelAndView result;
        final MaintenanceRequest m = this.maintenanceRequestService.findOne(maintenanceRequestId);
        result = this.createEditModelAndView(m);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest m)
    {
        ModelAndView result;

        result = this.createEditModelAndView(m, null, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest, BindingResult binding, final String message)
    {
        return ControllerUtils.createViewWithBinding("maintenanceRequests/service", "maintenanceRequest", maintenanceRequest, binding, message);
    }

    @RequestMapping(value = "/service", method = RequestMethod.POST, params = "save")
    public ModelAndView save(MaintenanceRequest maintenanceRequest, final BindingResult binding)
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);

        ModelAndView result;

        maintenanceRequest = maintenanceRequestService.bindForService(maintenanceRequest, binding);
        if (binding.hasErrors()) {
            result = this.createEditModelAndView(maintenanceRequest, binding, null);
        } else {
            try {
                this.maintenanceRequestService.service(maintenanceRequest);
                result = new ModelAndView("redirect:listNotServiced.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(maintenanceRequest, binding, "maintenanceRequest.commit.error");
            }
        }

        return result;
    }
}
