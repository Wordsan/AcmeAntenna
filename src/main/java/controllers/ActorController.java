package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import forms.EditOwnPasswordForm;
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

        if (!binding.hasErrors()) {
            try {
                actor = this.actorService.updateOwnProfile(actor, binding);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirectToReturnAction();
            } catch (ConstraintViolationException ex) {
                // Error is in binding.
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
}
