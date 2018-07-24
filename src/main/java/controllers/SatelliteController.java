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
import domain.Satellite;
import domain.User;
import forms.SearchForm;
import security.Authority;
import services.PlatformService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/satellites")
public class SatelliteController extends AbstractController {
	@Autowired private SatelliteService satelliteService;
	@Autowired private PlatformService platformService;

	@RequestMapping("/index")
	public ModelAndView index(@ModelAttribute("searchForm") @Valid SearchForm searchForm)
	{
		List<Satellite> satellites = satelliteService.search(searchForm.getTerms());

		ModelAndView result = new ModelAndView("satellites/index");
		result.addObject("satellites", satellites);
		result.addObject("searchForm", searchForm);
		return result;
	}

	@RequestMapping("/show")
	public ModelAndView show(@RequestParam("id") int id)
	{
		Satellite satellite = satelliteService.getById(id);
		ModelAndView result = new ModelAndView("satellites/show");
		result.addObject("satellite", satellite);
		result.addObject("platforms", satellite.getPlatforms());
		return result;
	}

	@RequestMapping("/new")
	public ModelAndView new_()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		Satellite satellite = new Satellite();
		return newForm(satellite, null, null);
	}

	private ModelAndView newForm(
			Satellite satellite,
			BindingResult binding,
			String globalErrorMessage
	)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"satellites/new",
				binding,
				globalErrorMessage
		);

		result.addObject("satellite", satellite);
		result.addObject("platforms", platformService.findAllForIndex());

		return result;
	}

	@RequestMapping(value="/create", method= RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("satellite") @Valid Satellite satellite,
			BindingResult binding,
			RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		ModelAndView result;
		if (binding.hasErrors()) {
			result = newForm(satellite, binding, null);
		} else {
			try {
				satellite = satelliteService.create(satellite);
				result = ControllerUtils.redirect("/satellites/index.do");
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(satellite,
								 binding,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		Satellite satellite = satelliteService.getByIdForEdit(id);
		return editForm(satellite, null, null);
	}

	private ModelAndView editForm(
			Satellite satellite,
			BindingResult binding,
			String globalErrorMessage
	)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"satellites/edit",
				binding,
				globalErrorMessage
		);

		result.addObject("satellite", satellite);
		result.addObject("platforms", platformService.findAllForIndex());

		return result;
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("satellite") @Valid Satellite satellite,
			BindingResult binding, RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		ModelAndView result;
		if (binding.hasErrors()) {
			result = editForm(satellite, binding, null);
		} else {
			try {
				satellite = satelliteService.update(satellite);
				result = ControllerUtils.redirect("/satellites/index.do");
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(satellite,
								 binding,
								 "misc.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView delete(@ModelAttribute("id") int id, RedirectAttributes redir)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		satelliteService.delete(id);
		ModelAndView result = ControllerUtils.redirect("/satellites/index.do");
		redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
		return result;
	}
}