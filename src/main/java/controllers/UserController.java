package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import domain.User;
import exceptions.ResourceNotUniqueException;
import forms.NewUserForm;
import services.UserService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;
import utilities.UserAccountUtils;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractController {
    @Autowired private UserService userService;

    public UserController()
    {
        super();
    }

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkUnauthenticated();

        return createNewModelAndView(null, null, new NewUserForm());
    }

    public ModelAndView createNewModelAndView(String globalErrorMessage, BindingResult binding, NewUserForm form)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "users/new",
                "form",
                form,
                binding,
                globalErrorMessage
        );

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("form") @Valid NewUserForm form,
            BindingResult binding, RedirectAttributes redir
    )
    {
        CheckUtils.checkUnauthenticated();

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                User user = userService.createAsNewUser(form.getUser());
                UserAccountUtils.setSessionAccount(user.getUserAccount());

                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
                return ControllerUtils.redirect("/welcome/index.do");
            } catch (ResourceNotUniqueException ex) {
                binding.rejectValue("username", "misc.error.usernameNotUnique");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createNewModelAndView(globalErrorMessage, binding, form);
    }

}