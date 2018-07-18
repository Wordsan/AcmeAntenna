package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		return newForm(form, null, false, null);
	}

	public ModelAndView newForm(
			NewUserForm form,
			BindingResult binding,
			boolean error,
			String message)
	{
		ModelAndView result = ControllerUtils.createViewWithBinding(
				"users/new",
				binding,
				error, message
				);

		result.addObject("form", form);

		return result;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("form") @Valid NewUserForm form,
			BindingResult binding)
	{
		ModelAndView result;
		if (binding.hasErrors()) {
			// Go back to edit form.
			result = newForm(form, binding, true, null);
		} else {
			try {
				// Commit to DB.
				User user = userService.createAsNewUser(form.getUser());
				UserAccountUtils.setSessionAccount(user.getUserAccount());

				result = ControllerUtils.redirect("/welcome/index.do");
			} catch(UsernameNotUniqueException ex) {
				result = newForm(form,
								 binding,
								 true,
								 "misc.error.usernameNotUnique");
			} catch(Throwable oops) {
				if (ApplicationConfig.DEBUG) oops.printStackTrace();

				result = newForm(form,
								 binding,
								 true,
								 "misc.commit.error");
			}
		}

		return result;
	}

}