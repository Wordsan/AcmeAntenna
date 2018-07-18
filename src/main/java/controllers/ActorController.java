package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import domain.Actor;
import services.ActorService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/actors")
public class ActorController extends AbstractController {
	@Autowired private ActorService actorService;

	public ActorController()
	{
		super();
	}

	@RequestMapping("/edit")
	public ModelAndView edit()
	{
		CheckUtils.checkAuthenticated();

		Actor actor = actorService.getPrincipal();
		return editForm(actor, null, false, null);
	}

	public ModelAndView editForm(Actor explorer,
			BindingResult binding,
			boolean error,
			String message)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"actors/edit",
				binding,
				error,
				message
				);

		result.addObject("actor", explorer);

		return result;
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("actor") @Valid Actor actor,
			BindingResult binding)
	{
		ModelAndView result;
		if (binding.hasErrors()) {
			// Go back to edit form.
			result = editForm(actor, binding, true, null);
		} else {
			try {
				// Commit to DB.
				actor = actorService.updateOwnProfile(actor);
				result = ControllerUtils.redirect("/welcome/index.do");

			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = editForm(actor,
						binding,
						true,
						"misc.commit.error");
			}
		}

		return result;
	}

}