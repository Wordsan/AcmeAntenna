package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        Actor actor = actorService.getPrincipal();
        return createEditModelAndView(actor, null, null);
    }

    public ModelAndView createEditModelAndView(
            Actor actor,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "actors/edit",
                binding,
                globalErrorMessage
        );

        result.addObject("formAction", "actors/update.do");
        result.addObject("actor", actor);

        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(
            @ModelAttribute("actor") @Valid Actor actor,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkAuthenticated();

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                actor = actorService.updateOwnProfile(actor);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/welcome/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";

            }
        }

        return createEditModelAndView(actor, binding, globalErrorMessage);
    }


    @RequestMapping("/edit_own_password")
    public ModelAndView editOwnPassword()
    {
        CheckUtils.checkAuthenticated();

        return createEditOwnPasswordModelAndView(new EditOwnPasswordForm(), null, null);
    }

    public ModelAndView createEditOwnPasswordModelAndView(
            EditOwnPasswordForm form,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "actors/edit_own_password",
                binding,
                globalErrorMessage
        );

        result.addObject("form", form);

        return result;
    }

    @RequestMapping(value = "/update_own_password", method = RequestMethod.POST)
    public ModelAndView updateOwnPassword(
            @ModelAttribute("form") @Valid EditOwnPasswordForm form,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkAuthenticated();

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                actorService.updateOwnPassword(getPrincipal(), form.getOldPassword(), form.getNewPassword());
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/welcome/index.do");
            } catch (OldPasswordDoesntMatchException oops) {
                binding.addError(new FieldError("form", "oldPassword", form.getOldPassword(), false, new String[]{"actors.error.oldPasswordDoesntMatch"}, null, null));
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditOwnPasswordModelAndView(form, binding, globalErrorMessage);
    }
}