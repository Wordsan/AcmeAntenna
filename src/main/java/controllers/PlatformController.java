package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.validation.Valid;

import domain.Platform;
import domain.User;
import forms.SearchForm;
import security.Authority;
import services.PlatformService;
import services.PlatformSubscriptionService;

@Controller
@RequestMapping("/platforms")
public class PlatformController extends AbstractController {
	@Autowired private PlatformService service;
	@Autowired private PlatformSubscriptionService platformSubscriptionService;

	@RequestMapping("/index")
	public ModelAndView index(@ModelAttribute("searchForm") @Valid SearchForm searchForm)
	{
		List<Platform> platforms = service.search(searchForm.getTerms());

		ModelAndView result = new ModelAndView("platforms/index");
		result.addObject("platforms", platforms);
		result.addObject("searchForm", searchForm);
		return result;
	}

	@RequestMapping("/show")
	public ModelAndView show(@RequestParam("id") int id)
	{
		Platform platform = service.getById(id);
		ModelAndView result = new ModelAndView("platforms/show");
		result.addObject("platform", platform);
		result.addObject("satellites", platform.getSatellites());
		if (hasAuthority(Authority.USER)) {
			result.addObject("platformSubscriptions", platformSubscriptionService.findAllForPrincipalAndPlatform(platform));
		}
		return result;
	}
}