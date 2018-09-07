
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AgentService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;
import utilities.UserAccountUtils;
import domain.Agent;
import exceptions.ResourceNotUniqueException;
import forms.NewAgentForm;

@Controller
@RequestMapping("/agents")
public class AgentController extends AbstractController {

	@Autowired
	private AgentService	agentService;


	public AgentController() {
		super();
	}

	@RequestMapping("/new")
	public ModelAndView new_() {
		CheckUtils.checkUnauthenticated();

		return this.createEditModelAndView(null, null, new NewAgentForm());
	}

	public ModelAndView createEditModelAndView(final String globalErrorMessage, final BindingResult binding, final NewAgentForm form) {
		final ModelAndView result = ControllerUtils.createViewWithBinding("agents/new", binding, globalErrorMessage);

		result.addObject("form", form);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute("form") @Valid final NewAgentForm form, final BindingResult binding, final RedirectAttributes redir) {
		CheckUtils.checkUnauthenticated();

		String globalErrorMessage = null;

		if (!binding.hasErrors())
			try {
				final Agent agent = this.agentService.createAsNewAgent(form.getAgent());
				UserAccountUtils.setSessionAccount(agent.getUserAccount());

				redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
				return ControllerUtils.redirect("/welcome/index.do");
			} catch (final ResourceNotUniqueException ex) {
				globalErrorMessage = "misc.error.usernameNotUnique";
			} catch (final Throwable oops) {
				if (ApplicationConfig.DEBUG)
					oops.printStackTrace();
				globalErrorMessage = "misc.commit.error";
			}

		return this.createEditModelAndView(globalErrorMessage, binding, form);
	}

}
