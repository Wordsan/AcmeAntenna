package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.validation.Valid;

import domain.Satellite;
import forms.SearchForm;
import services.SatelliteService;

@Controller
@RequestMapping("/satellites")
public class SatelliteController extends AbstractController {
	@Autowired private SatelliteService satelliteService;

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
}