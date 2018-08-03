
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;
import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import forms.EditOwnPasswordForm;

@Controller
@RequestMapping("/actors")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	public ActorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView("actors/list");
		final Collection<Actor> actors = this.actorService.findAll();
		actors.remove(this.actorService.findPrincipal());
		result.addObject("requestURI", "actors/list.do");
		result.addObject("actors", actors);

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit() {
		CheckUtils.checkAuthenticated();

		final Actor actor = this.actorService.getPrincipal();
		return this.createEditModelAndView(actor, null, null);
	}

	public ModelAndView createEditModelAndView(final Actor actor, final BindingResult binding, final String globalErrorMessage) {
		final ModelAndView result = ControllerUtils.createViewWithBinding("actors/edit", binding, globalErrorMessage);

		result.addObject("formAction", "actors/update.do");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("actor") @Valid Actor actor, final BindingResult binding, final RedirectAttributes redir) {
		CheckUtils.checkAuthenticated();

		String globalErrorMessage = null;

		if (!binding.hasErrors())
			try {
				actor = this.actorService.updateOwnProfile(actor);
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

				return ControllerUtils.redirect("/welcome/index.do");
			} catch (final Throwable oops) {
				if (ApplicationConfig.DEBUG)
					oops.printStackTrace();
				globalErrorMessage = "misc.commit.error";

			}

		return this.createEditModelAndView(actor, binding, globalErrorMessage);
	}

	@RequestMapping("/edit_own_password")
	public ModelAndView editOwnPassword() {
		CheckUtils.checkAuthenticated();

		return this.createEditOwnPasswordModelAndView(new EditOwnPasswordForm(), null, null);
	}

	public ModelAndView createEditOwnPasswordModelAndView(final EditOwnPasswordForm form, final BindingResult binding, final String globalErrorMessage) {
		final ModelAndView result = ControllerUtils.createViewWithBinding("actors/edit_own_password", binding, globalErrorMessage);

		result.addObject("form", form);

		return result;
	}

	@RequestMapping(value = "/update_own_password", method = RequestMethod.POST)
	public ModelAndView updateOwnPassword(@ModelAttribute("form") @Valid final EditOwnPasswordForm form, final BindingResult binding, final RedirectAttributes redir) {
		CheckUtils.checkAuthenticated();

		String globalErrorMessage = null;

		if (!binding.hasErrors())
			try {
				this.actorService.updateOwnPassword(this.getPrincipal(), form.getOldPassword(), form.getNewPassword());
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

				return ControllerUtils.redirect("/welcome/index.do");
			} catch (final OldPasswordDoesntMatchException oops) {
				binding.addError(new FieldError("form", "oldPassword", form.getOldPassword(), false, new String[] {
					"actors.error.oldPasswordDoesntMatch"
				}, null, null));
			} catch (final Throwable oops) {
				if (ApplicationConfig.DEBUG)
					oops.printStackTrace();
				globalErrorMessage = "misc.commit.error";
			}

		return this.createEditOwnPasswordModelAndView(form, binding, globalErrorMessage);
	}
}
