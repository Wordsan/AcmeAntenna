package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import domain.Antenna;
import security.Authority;
import services.AntennaService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/antennas")
public class AntennaController extends AbstractController {
	@Autowired AntennaService antennaService;

	public AntennaController()
	{
		super();
	}

	@RequestMapping("/new")
	public ModelAndView new_()
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);

		Antenna antenna = new Antenna();
		return newForm(antenna, null, false, null);
	}

	public ModelAndView newForm(
			Antenna antenna,
			BindingResult binding,
			boolean error,
			String message)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"antenna/new",
				binding,
				error,
				message
				);

		result.addObject("antenna", antenna);

		return result;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("form") @Valid Antenna antenna,
			BindingResult binding)
	{
		ModelAndView result;
		if (binding.hasErrors()) {
			// Go back to new form.
			result = newForm(antenna, binding, true, null);
		} else {
			try {
				// Commit to DB.
				antenna = antennaService.create(antenna);

				result = ControllerUtils.redirect("/welcome/index.do");
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

}