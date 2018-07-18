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
import domain.Platform;
import forms.SearchForm;
import services.PlatformService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/platforms")
public class PlatformController extends AbstractController {
	@Autowired PlatformService platformService;

	@RequestMapping("/index")
	public ModelAndView index(@ModelAttribute("searchForm") @Valid SearchForm searchForm)
	{
		List<Platform> platforms = platformService.search(searchForm.getTerms());

		ModelAndView result = new ModelAndView("platforms/index");
		result.addObject("platforms", platforms);
		result.addObject("searchForm", searchForm);
		return result;
	}


}