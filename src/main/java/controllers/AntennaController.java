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
import domain.User;
import security.Authority;
import services.AntennaService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/antennas")
public class AntennaController extends AbstractController {
	@Autowired private AntennaService antennaService;
	@Autowired private SatelliteService satelliteService;

	@RequestMapping("/index")
	public ModelAndView index()
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		List<Antenna> antennas = antennaService.findAllForPrincipal();
		ModelAndView result = new ModelAndView("antennas/index");
		result.addObject("antennas", antennas);
		return result;
	}

	@RequestMapping("/new")
	public ModelAndView new_()
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		Antenna antenna = new Antenna();
		antenna.setUser((User) getPrincipal());
		return newForm(antenna, null, null);
	}

	public ModelAndView newForm(
			Antenna antenna,
			BindingResult binding,
			String globalErrorMessage
	)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"antennas/new",
				binding,
				globalErrorMessage
				);

		result.addObject("antenna", antenna);
		result.addObject("satellites", satelliteService.findAllForIndex());

		return result;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("antenna") @Valid Antenna antenna,
			BindingResult binding,
			RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		ModelAndView result;
		if (binding.hasErrors()) {
			result = newForm(antenna, binding, null);
		} else {
			try {
				antenna = antennaService.create(antenna);
				result = ControllerUtils.redirect("/antennas/index.do");
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(antenna,
								 binding,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);


		Antenna antenna = antennaService.getByIdForEdit(id);
		return editForm(antenna, null, null);
	}

	public ModelAndView editForm(
			Antenna antenna,
			BindingResult binding,
			String globalErrorMessage
	)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"antennas/edit",
				binding,
				globalErrorMessage
		);

		result.addObject("antenna", antenna);
		result.addObject("satellites", satelliteService.findAllForIndex());

		return result;
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("antenna") @Valid Antenna antenna,
			BindingResult binding, RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		ModelAndView result;
		if (binding.hasErrors()) {
			result = editForm(antenna, binding, null);
		} else {
			try {
				antenna = antennaService.update(antenna);
				result = ControllerUtils.redirect("/antennas/index.do");
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(antenna,
								 binding,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute("id") int id, RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		antennaService.delete(id);
		ModelAndView result = ControllerUtils.redirect("/antennas/index.do");
		redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
		return result;
	}

}