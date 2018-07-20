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
import exceptions.UsernameNotUniqueException;
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

		NewUserForm form = new NewUserForm();
		return newForm(form, null, null);
	}

	public ModelAndView newForm(
			NewUserForm form,
			BindingResult binding,
			String globalErrorMessage
	)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"users/new",
				binding,
				globalErrorMessage
				);

		result.addObject("form", form);

		return result;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("form") @Valid NewUserForm form,
			BindingResult binding, RedirectAttributes redir)
	{
		CheckUtils.checkUnauthenticated();

		ModelAndView result;
		if (binding.hasErrors()) {
			// Go back to edit form.
			result = newForm(form, binding, null);
		} else {
			try {
				// Commit to DB.
				User user = userService.createAsNewUser(form.getUser());
				UserAccountUtils.setSessionAccount(user.getUserAccount());

				result = ControllerUtils.redirect("/welcome/index.do");
				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
			} catch(UsernameNotUniqueException ex) {
				result = newForm(form,
								 binding,
								 "misc.error.usernameNotUnique");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(form,
								 binding,
								 "misc.commit.error");
			}
		}

		return result;
	}

}