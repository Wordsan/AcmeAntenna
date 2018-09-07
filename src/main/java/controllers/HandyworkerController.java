package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

import javax.validation.Valid;

import domain.Handyworker;
import exceptions.ResourceNotUniqueException;
import forms.NewHandyworkerForm;
import security.UserAccountService;
import services.HandyworkerService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;
import utilities.UserAccountUtils;

@Controller
@RequestMapping("/handyworkers")
public class HandyworkerController extends AbstractController {

    @Autowired
    private HandyworkerService handyworkerService;
    @Autowired
    private UserAccountService userAccountService;


    public HandyworkerController()
    {
        super();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list()
    {
        final ModelAndView result = new ModelAndView("handyworkers/list");
        final Collection<Handyworker> handyworkers = this.handyworkerService.findAll();
        result.addObject("requestURI", "handyworkers/list.do");
        result.addObject("handyworkers", handyworkers);

        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkUnauthenticated();

        return this.createEditModelAndView(null, null, new NewHandyworkerForm());
    }

    public ModelAndView createEditModelAndView(final String globalErrorMessage, final BindingResult binding, final NewHandyworkerForm form)
    {
        final ModelAndView result = ControllerUtils.createViewWithBinding("handyworkers/new", binding, globalErrorMessage);

        result.addObject("form", form);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("form") @Valid final NewHandyworkerForm form, final BindingResult binding, final RedirectAttributes redir)
    {
        CheckUtils.checkUnauthenticated();

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                final Handyworker handyworker = this.handyworkerService.createAsNewHandyworker(form.getHandyworker());
                UserAccountUtils.setSessionAccount(handyworker.getUserAccount());

                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
                return ControllerUtils.redirect("/welcome/index.do");
            } catch (final ResourceNotUniqueException ex) {
                globalErrorMessage = "misc.error.usernameNotUnique";
            } catch (final Throwable oops) {
                if (ApplicationConfig.DEBUG) {
                    oops.printStackTrace();
                }
                globalErrorMessage = "misc.commit.error";
            }
        }

        return this.createEditModelAndView(globalErrorMessage, binding, form);
    }

	/*
     * @RequestMapping(value = "/create", method = RequestMethod.GET)
	 * public ModelAndView create() {
	 * final Handyworker handyworker = this.handyworkerService.create();
	 * final UserAccount ua = this.userAccountService.create();
	 * handyworker.setUserAccount(ua);
	 * final ModelAndView result = this.createModelAndView(handyworker, null);
	 * 
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/create", method = RequestMethod.POST)
	 * public ModelAndView saveHandyworker(@Valid final Handyworker handyworker, final BindingResult binding) {
	 * ModelAndView res;
	 * final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	 * if (binding.hasErrors()) {
	 * System.out.print(binding.getAllErrors());
	 * res = this.createModelAndView(handyworker, null);
	 * } else {
	 * 
	 * handyworker.getUserAccount().setPassword(encoder.encodePassword(handyworker.getUserAccount().getPassword(), null));
	 * 
	 * final Handyworker handyworkerSaved = this.handyworkerService.save(handyworker);
	 * res = new ModelAndView("security/login");
	 * res.addObject("credentials", handyworkerSaved.getUserAccount());
	 * }
	 * return res;
	 * }
	 * protected ModelAndView createModelAndView(final Handyworker handyworker, final String message) {
	 * ModelAndView result;
	 * 
	 * result = new ModelAndView("handyworkers/create");
	 * result.addObject("handyworker", handyworker);
	 * result.addObject("message", message);
	 * 
	 * return result;
	 * }
	 */
}
