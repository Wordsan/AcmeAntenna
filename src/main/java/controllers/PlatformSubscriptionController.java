package controllers;

import org.apache.commons.lang3.StringUtils;
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

import domain.PlatformSubscription;
import domain.User;
import exceptions.OverlappingPlatformSubscriptionException;
import security.Authority;
import services.PlatformService;
import services.PlatformSubscriptionService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/platform_subscriptions")
public class PlatformSubscriptionController extends AbstractController {
    @Autowired private PlatformSubscriptionService service;
    @Autowired private PlatformService platformService;

    @RequestMapping("/index")
    public ModelAndView index()
    {
        List<PlatformSubscription> platformSubscriptions = service.findAllForPrincipal();
        ModelAndView result = new ModelAndView("platform_subscriptions/index");
        result.addObject("platformSubscriptions", platformSubscriptions);
        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_(@RequestParam(value = "platformId", required = false) Integer platformId)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        PlatformSubscription platformSubscription = new PlatformSubscription();
        platformSubscription.setUser((User) getPrincipal());
        if (platformId != null) {
            platformSubscription.setPlatform(platformService.getById(platformId));
        }
        // Set a dummy key-code. Lame, but easier than finding out how to disable validation just for that field.
        platformSubscription.setKeyCode(StringUtils.repeat(PlatformSubscription.KEYCODE_ALPHABET.charAt(0), PlatformSubscription.KEYCODE_LENGTH));

        return createEditModelAndView("platform_subscriptions/new", "platform_subscriptions/create.do", null, null, platformSubscription);
    }

    public ModelAndView createEditModelAndView(String viewName, String formAction, String globalErrorMessage, BindingResult binding, PlatformSubscription platformSubscription)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                viewName,
                "platformSubscription",
                platformSubscription,
                binding,
                globalErrorMessage
        );

        result.addObject("formAction", formAction);
        result.addObject("platforms", platformService.findAllForIndex());

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("platformSubscription") @Valid PlatformSubscription platformSubscription,
            BindingResult binding, RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {
            try {
                platformSubscription = service.create(platformSubscription);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
                return ControllerUtils.redirectToReturnAction();
            } catch (OverlappingPlatformSubscriptionException ex) {
                globalErrorMessage = "platform_subscription.error.overlapping";
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }


        return createEditModelAndView("platform_subscriptions/new", "platform_subscriptions/create.do", globalErrorMessage, binding, platformSubscription);
    }
}