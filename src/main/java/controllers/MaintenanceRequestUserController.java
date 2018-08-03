
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
import services.UserService;
import domain.Antenna;
import domain.Handyworker;
import domain.MaintenanceRequest;
import domain.User;

@Controller
@RequestMapping("/maintenanceRequests/user")
public class MaintenanceRequestUserController {

	@Autowired
	private MaintenanceRequestService	maintenanceRequestService;
	@Autowired
	private UserService					userService;
	@Autowired
	private HandyworkerService			handyworkerService;


	public MaintenanceRequestUserController() {
		super();
	}

	@RequestMapping(value = "/listNotServiced", method = RequestMethod.GET)
	public ModelAndView listNotServiced() {
		ModelAndView result;
		final User user = this.userService.findPrincipal();
		final Collection<MaintenanceRequest> all = this.maintenanceRequestService.findAll();
		final Collection<MaintenanceRequest> maintenanceRequests = this.getNotServiced(user, all);

		result = new ModelAndView("maintenanceRequests/list");
		result.addObject("requestURI", "maintenanceRequests/user/listNotServiced.do");
		result.addObject("maintenanceRequests", maintenanceRequests);

		return result;
	}

	private Collection<MaintenanceRequest> getNotServiced(final User user, final Collection<MaintenanceRequest> list) {

		final Collection<MaintenanceRequest> res = new ArrayList<MaintenanceRequest>();
		for (final MaintenanceRequest mr : list)
			if (mr.getDoneTime() == null && user.getRequests().contains(mr))
				res.add(mr);
		return res;

	}

	@RequestMapping(value = "/listServiced", method = RequestMethod.GET)
	public ModelAndView listServiced() {
		ModelAndView result;
		final User user = this.userService.findPrincipal();
		final Collection<MaintenanceRequest> all = this.maintenanceRequestService.findAll();
		final Collection<MaintenanceRequest> maintenanceRequests = this.getServiced(user, all);
		final boolean done = true;

		result = new ModelAndView("maintenanceRequests/list");
		result.addObject("requestURI", "maintenanceRequests/user/listServiced.do");
		result.addObject("maintenanceRequests", maintenanceRequests);
		result.addObject("done", done);

		return result;
	}

	private Collection<MaintenanceRequest> getServiced(final User user, final Collection<MaintenanceRequest> list) {

		final Collection<MaintenanceRequest> res = new ArrayList<MaintenanceRequest>();
		for (final MaintenanceRequest mr : list)
			if (mr.getDoneTime() != null && user.getRequests().contains(mr))
				res.add(mr);
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MaintenanceRequest maintenanceRequest;

		maintenanceRequest = this.maintenanceRequestService.create();
		result = this.createEditModelAndView(maintenanceRequest);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest) {
		ModelAndView result;

		result = this.createEditModelAndView(maintenanceRequest, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest, final String message) {
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
	public ModelAndView save(@Valid final MaintenanceRequest maintenanceRequest, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(maintenanceRequest);
		} else
			try {
				this.maintenanceRequestService.save(maintenanceRequest);
				result = new ModelAndView("redirect:listNotServiced.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(maintenanceRequest, "maintenanceRequest.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MaintenanceRequest maintenanceRequest, final BindingResult binding) {
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
