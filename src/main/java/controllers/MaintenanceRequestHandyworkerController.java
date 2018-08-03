
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HandyworkerService;
import services.MaintenanceRequestService;
import domain.Handyworker;
import domain.MaintenanceRequest;

@Controller
@RequestMapping("/maintenanceRequests/handyworker")
public class MaintenanceRequestHandyworkerController {

	@Autowired
	private MaintenanceRequestService	maintenanceRequestService;
	@Autowired
	private HandyworkerService			handyworkerService;


	public MaintenanceRequestHandyworkerController() {
		super();
	}

	@RequestMapping(value = "/listNotServiced", method = RequestMethod.GET)
	public ModelAndView listNotServiced() {
		ModelAndView result;
		final Handyworker handyworker = this.handyworkerService.findPrincipal();
		final Collection<MaintenanceRequest> all = this.maintenanceRequestService.findAll();
		final Collection<MaintenanceRequest> maintenanceRequests = this.getNotServiced(handyworker, all);
		final boolean check = true;

		result = new ModelAndView("maintenanceRequests/list");
		result.addObject("requestURI", "maintenanceRequests/handyworker/listNotServiced.do");
		result.addObject("maintenanceRequests", maintenanceRequests);
		result.addObject("check", check);

		return result;
	}

	private Collection<MaintenanceRequest> getNotServiced(final Handyworker handyworker, final Collection<MaintenanceRequest> list) {

		final Collection<MaintenanceRequest> res = new ArrayList<MaintenanceRequest>();
		for (final MaintenanceRequest mr : list)
			if (mr.getDoneTime() == null && handyworker.getRequests().contains(mr))
				res.add(mr);
		return res;

	}

	@RequestMapping(value = "/listServiced", method = RequestMethod.GET)
	public ModelAndView listServiced() {
		ModelAndView result;
		final Handyworker handyworker = this.handyworkerService.findPrincipal();
		final Collection<MaintenanceRequest> all = this.maintenanceRequestService.findAll();
		final Collection<MaintenanceRequest> maintenanceRequests = this.getServiced(handyworker, all);
		final boolean done = true;

		result = new ModelAndView("maintenanceRequests/list");
		result.addObject("requestURI", "maintenanceRequests/handyworker/listServiced.do");
		result.addObject("maintenanceRequests", maintenanceRequests);
		result.addObject("done", done);

		return result;
	}

	private Collection<MaintenanceRequest> getServiced(final Handyworker handyworker, final Collection<MaintenanceRequest> list) {

		final Collection<MaintenanceRequest> res = new ArrayList<MaintenanceRequest>();
		for (final MaintenanceRequest mr : list)
			if (mr.getDoneTime() != null && handyworker.getRequests().contains(mr))
				res.add(mr);
		return res;

	}

	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public ModelAndView service(final int maintenanceRequestId) {
		ModelAndView result;
		final MaintenanceRequest m = this.maintenanceRequestService.findOne(maintenanceRequestId);
		result = this.createEditModelAndView(m);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MaintenanceRequest m) {
		ModelAndView result;

		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest, final String message) {
		ModelAndView result;

		result = new ModelAndView("maintenanceRequests/service");
		result.addObject("maintenanceRequest", maintenanceRequest);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/service", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MaintenanceRequest maintenanceRequest, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(maintenanceRequest);
		} else
			try {
				this.maintenanceRequestService.service(maintenanceRequest);
				result = new ModelAndView("redirect:listNotServiced.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(maintenanceRequest, "maintenanceRequest.commit.error");
			}

		return result;
	}
}
