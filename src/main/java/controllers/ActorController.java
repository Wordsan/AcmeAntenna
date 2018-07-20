package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
        return editForm(actor, null, null);
    }

    public ModelAndView editForm(
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

        ModelAndView result;
        if (binding.hasErrors()) {
            // Go back to edit form.
            result = editForm(actor, binding, null);
        } else {
            try {
                // Commit to DB.
                actor = actorService.updateOwnProfile(actor);
                result = ControllerUtils.redirect("/welcome/index.do");
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();

                result = editForm(actor,
                                  binding,
                                  "misc.commit.error"
                );
            }
        }

        return result;
    }


    @RequestMapping("/editOwnPassword")
    public ModelAndView editOwnPassword()
    {
        CheckUtils.checkAuthenticated();

        return editOwnPasswordForm(new EditOwnPasswordForm(), null, null);
    }

    public ModelAndView editOwnPasswordForm(
            EditOwnPasswordForm form,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "actors/editOwnPassword",
                binding,
                globalErrorMessage
        );

        result.addObject("form", form);

        return result;
    }

    @RequestMapping(value = "/updateOwnPassword", method = RequestMethod.POST)
    public ModelAndView updateOwnPassword(
            @ModelAttribute("form") @Valid EditOwnPasswordForm form,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkAuthenticated();

        ModelAndView result;
        if (binding.hasErrors()) {
            // Go back to edit form.
            result = editOwnPasswordForm(form, binding, null);
        } else {
            try {
                // Commit to DB.
                actorService.updateOwnPassword(getPrincipal(), form.getOldPassword(), form.getNewPassword());
                result = ControllerUtils.redirect("/welcome/index.do");
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            } catch (OldPasswordDoesntMatchException oops) {
                binding.addError(new FieldError("form", "oldPassword", form.getRepeatNewPassword(), false, new String[]{"actors.error.oldPasswordDoesntMatch"}, null, null));
                result = editOwnPasswordForm(form,
                                             binding,
                                             null
                );
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();

                result = editOwnPasswordForm(form,
                                             binding,
                                             "misc.commit.error"
                );
            }
        }

        return result;
    }
}