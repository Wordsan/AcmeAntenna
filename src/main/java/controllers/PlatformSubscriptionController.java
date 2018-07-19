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

import java.nio.channels.OverlappingFileLockException;
import java.util.List;

import javax.validation.Valid;

import domain.PlatformSubscription;
import domain.User;
import exceptions.OverlappingPlatformSubscriptionException;
import services.PlatformService;
import services.PlatformSubscriptionService;
import utilities.ApplicationConfig;
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
        PlatformSubscription platformSubscription = new PlatformSubscription();
        platformSubscription.setUser((User) getPrincipal());
        if (platformId != null) {
            platformSubscription.setPlatform(platformService.getById(platformId));
        }
        // Set a dummy key-code. Lame, but easier than disabling validation just for that field.
        platformSubscription.setKeyCode(StringUtils.repeat(PlatformSubscription.KEYCODE_ALPHABET.charAt(0), PlatformSubscription.KEYCODE_LENGTH));
        return newForm(platformSubscription, null, false, null);
    }

    public ModelAndView newForm(
            PlatformSubscription platformSubscription,
            BindingResult binding,
            boolean error,
            String message)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "platform_subscriptions/new",
                binding,
                error,
                message
        );

        result.addObject("platformSubscription", platformSubscription);
        result.addObject("platforms", platformService.findAll());

        return result;
    }

    @RequestMapping(value="/create", method= RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("platformSubscription") @Valid PlatformSubscription platformSubscription,
            BindingResult binding)
    {
        ModelAndView result;
        if (binding.hasErrors()) {
            result = newForm(platformSubscription, binding, true, null);
        } else {
            try {
                platformSubscription = service.create(platformSubscription);
                result = ControllerUtils.redirect("/platform_subscriptions/index.do");
            } catch(OverlappingPlatformSubscriptionException ex) {
                result = newForm(platformSubscription,
                                 binding,
                                 true,
                                 "platform_subscription.error.overlapping");
            } catch(Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();

                result = newForm(platformSubscription,
                                 binding,
                                 true,
                                 "misc.commit.error");
            }
        }

        return result;
    }
}