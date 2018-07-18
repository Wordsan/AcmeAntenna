package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.validation.Valid;

import domain.Antenna;
import services.AntennaService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/antennas")
public class AntennaController extends AbstractController {
	@Autowired AntennaService antennaService;
	@Autowired SatelliteService satelliteService;

	@RequestMapping("/index")
	public ModelAndView index()
	{
		List<Antenna> antennas = antennaService.findAllForUser();
		ModelAndView result = new ModelAndView("antennas/index");
		result.addObject("antennas", antennas);
		return result;
	}

	@RequestMapping("/new")
	public ModelAndView new_()
	{
		Antenna antenna = new Antenna();
		antenna.setUser(getPrincipalUser());
		return newForm(antenna, null, false, null);
	}

	public ModelAndView newForm(
			Antenna antenna,
			BindingResult binding,
			boolean error,
			String message)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"antennas/new",
				binding,
				error,
				message
				);

		result.addObject("antenna", antenna);
		result.addObject("satellites", satelliteService.findAllSortedByName());

		return result;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("antenna") @Valid Antenna antenna,
			BindingResult binding)
	{
		ModelAndView result;
		if (binding.hasErrors()) {
			result = newForm(antenna, binding, true, null);
		} else {
			try {
				antenna = antennaService.create(antenna);
				result = ControllerUtils.redirect("/antennas/index.do");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(antenna,
								 binding,
								 true,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") int id)
	{
		Antenna antenna = antennaService.getByIdForEdit(id);
		return editForm(antenna, null, false, null);
	}

	public ModelAndView editForm(
			Antenna antenna,
			BindingResult binding,
			boolean error,
			String message)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"antennas/edit",
				binding,
				error,
				message
		);

		result.addObject("antenna", antenna);
		result.addObject("satellites", satelliteService.findAllSortedByName());

		return result;
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("antenna") @Valid Antenna antenna,
			BindingResult binding, RedirectAttributes redir)
	{
		ModelAndView result;
		if (binding.hasErrors()) {
			result = editForm(antenna, binding, true, null);
		} else {
			try {
				antenna = antennaService.update(antenna);
				result = ControllerUtils.redirect("/antennas/index.do");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(antenna,
								 binding,
								 true,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute("id") int id)
	{
		antennaService.delete(id);
		return ControllerUtils.redirect("/antennas/index.do");
	}

}