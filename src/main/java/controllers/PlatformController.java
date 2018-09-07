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

import java.util.List;

import javax.validation.Valid;

import domain.Platform;
import forms.SearchForm;
import security.Authority;
import security.LoginService;
import services.PlatformService;
import services.PlatformSubscriptionService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/platforms")
public class PlatformController extends AbstractController {
    @Autowired private PlatformService platformService;
    @Autowired private SatelliteService satelliteService;
    @Autowired private PlatformSubscriptionService platformSubscriptionService;

    @RequestMapping("/index")
    public ModelAndView index(@ModelAttribute("searchForm") @Valid SearchForm searchForm)
    {
        List<Platform> platforms = platformService.search(searchForm.getTerms());

        ModelAndView result = new ModelAndView("platforms/index");
        result.addObject("platforms", platforms);
        result.addObject("searchForm", searchForm);
        return result;
    }

    @RequestMapping("/show")
    public ModelAndView show(@RequestParam("id") int id)
    {
        Platform platform = platformService.getById(id);
        ModelAndView result = new ModelAndView("platforms/show");
        result.addObject("platform", platform);
        if (LoginService.hasAuthority(Authority.USER)) {
            result.addObject("platformSubscriptions", platformSubscriptionService.findAllForPrincipalAndPlatform(platform));
        }
        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        Platform platform = new Platform();
        return createEditModelAndView("platforms/new", "platforms/create.do", null, null, platform);
    }

    private ModelAndView createEditModelAndView(String viewName, String formAction, String globalErrorMessage, BindingResult binding, Platform platform)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                viewName,
                binding,
                globalErrorMessage
        );

        result.addObject("formAction", formAction);
        result.addObject("platform", platform);
        result.addObject("satellites", satelliteService.findAllForIndex());

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("platform") @Valid Platform platform,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {
            try {
                platform = platformService.create(platform);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/platforms/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditModelAndView("platforms/new", "platforms/create.do", globalErrorMessage, binding, platform);
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam("id") int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        Platform platform = platformService.getByIdForEdit(id);
        return createEditModelAndView("platforms/edit", "platforms/update.do", null, null, platform);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(
            @ModelAttribute("platform") @Valid Platform platform,
            BindingResult binding, RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {
            try {
                platform = platformService.update(platform);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/platforms/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditModelAndView("platforms/edit", "platforms/update.do", globalErrorMessage, binding, platform);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute("id") int id, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        ModelAndView result = ControllerUtils.redirect("/platforms/index.do");
        try {
            platformService.delete(id);
            redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
        } catch (Throwable oops) {
            if (ApplicationConfig.DEBUG) oops.printStackTrace();
            redir.addFlashAttribute("globalErrorMessage", "misc.commit.error");
        }
        return result;
    }
}