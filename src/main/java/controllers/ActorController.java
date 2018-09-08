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

import java.util.Collection;

import javax.validation.Valid;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import forms.EditOwnPasswordForm;
import security.Authority;
import services.ActorService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/actors")
public class ActorController extends AbstractController {

    @Autowired
	private ActorService actorService;


    public ActorController()
    {
        super();
    }

    @RequestMapping("/index")
    public ModelAndView index()
    {
        final ModelAndView result = new ModelAndView("actors/index");
        final Collection<Actor> actors = this.actorService.findAll();
        actors.remove(this.actorService.findPrincipal());
        result.addObject("requestURI", "actors/index.do");
        result.addObject("actors", actors);

        return result;
    }

    @RequestMapping("/edit")
    public ModelAndView edit()
    {
        CheckUtils.checkAuthenticated();

        final Actor actor = this.actorService.getPrincipal();
        return this.createEditModelAndView(actor, null, null);
    }

    public ModelAndView createEditModelAndView(final Actor actor, final BindingResult binding, final String globalErrorMessage)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding("actors/edit", "actor", actor, binding, globalErrorMessage);

        result.addObject("formAction", "actors/update.do");

        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("actor") Actor actor, final BindingResult binding, final RedirectAttributes redir)
    {
        CheckUtils.checkAuthenticated();

        String globalErrorMessage = null;

        actor = actorService.bindForUpdate(actor, binding);
		if (!binding.hasErrors()) {
			try {
				actor = this.actorService.updateOwnProfile(actor);
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirectToReturnAction();
            } catch (final Throwable oops) {
                if (ApplicationConfig.DEBUG) {
                    oops.printStackTrace();
                }
                globalErrorMessage = "misc.commit.error";
			}
		}

        return this.createEditModelAndView(actor, binding, globalErrorMessage);
    }

    @RequestMapping("/edit_own_password")
    public ModelAndView editOwnPassword()
    {
        CheckUtils.checkAuthenticated();

        return this.createEditOwnPasswordModelAndView(new EditOwnPasswordForm(), null, null);
    }

    public ModelAndView createEditOwnPasswordModelAndView(final EditOwnPasswordForm form, final BindingResult binding, final String globalErrorMessage)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding("actors/edit_own_password", "form", form, binding, globalErrorMessage);

        return result;
    }

    @RequestMapping(value = "/update_own_password", method = RequestMethod.POST)
    public ModelAndView updateOwnPassword(@ModelAttribute("form") @Valid final EditOwnPasswordForm form, final BindingResult binding, final RedirectAttributes redir)
    {
        CheckUtils.checkAuthenticated();

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                this.actorService.updateOwnPassword(form.getOldPassword(), form.getNewPassword());
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirectToReturnAction();
            } catch (final OldPasswordDoesntMatchException oops) {
                binding.rejectValue("oldPassword", "actors.error.oldPasswordDoesntMatch");
            } catch (final Throwable oops) {
                if (ApplicationConfig.DEBUG) {
                    oops.printStackTrace();
                }
                globalErrorMessage = "misc.commit.error";
            }
        }

        return this.createEditOwnPasswordModelAndView(form, binding, globalErrorMessage);
    }

    @RequestMapping(value = "/set_banned", method = RequestMethod.POST)
    public ModelAndView setBanned(@RequestParam("id") int id, @RequestParam("banned") int banned, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        try {
            actorService.setBanned(id, banned != 0);
            redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            return ControllerUtils.redirectToReturnAction();
        } catch (final Throwable oops) {
            if (ApplicationConfig.DEBUG) {
                oops.printStackTrace();
            }
            redir.addFlashAttribute("globalErrorMessage", "misc.commit.error");
            return ControllerUtils.redirectToReturnAction();
        }
    }
}
