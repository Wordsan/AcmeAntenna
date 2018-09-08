package controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private PlatformSubscriptionService service;
    @Autowired
    private PlatformService platformService;


    @RequestMapping("/index")
    public ModelAndView index()
    {
        final List<PlatformSubscription> platformSubscriptions = this.service.findAllForPrincipal();
        final ModelAndView result = new ModelAndView("platform_subscriptions/index");
        result.addObject("platformSubscriptions", platformSubscriptions);
        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_(@RequestParam(value = "platformId", required = false) final Integer platformId, @CookieValue(value = "creditCard", required = false) final String creditCard)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        final PlatformSubscription platformSubscription = new PlatformSubscription();
        platformSubscription.setUser((User) this.getPrincipal());
        if (platformId != null) {
            platformSubscription.setPlatform(this.platformService.getById(platformId));
        }

        platformSubscription.setKeyCode(StringUtils.repeat(PlatformSubscription.KEYCODE_ALPHABET.charAt(0), PlatformSubscription.KEYCODE_LENGTH));
        if (creditCard != null) {
            platformSubscription.setCreditCard(creditCard);
        }

        return this.createEditModelAndView("platform_subscriptions/new", "platform_subscriptions/create.do", null, null, platformSubscription);
    }

    public ModelAndView createEditModelAndView(final String viewName, final String formAction, final String globalErrorMessage, final BindingResult binding, final PlatformSubscription platformSubscription)
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
    public ModelAndView create(@ModelAttribute("platformSubscription") @Valid PlatformSubscription platformSubscription, final BindingResult binding, final RedirectAttributes redir, final HttpServletResponse response)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {

            try {
                platformSubscription = this.service.create(platformSubscription);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
                response.addCookie(new Cookie("creditCard", platformSubscription.getCreditCard()));
                return ControllerUtils.redirectToReturnAction();
            } catch (OverlappingPlatformSubscriptionException ex) {
                globalErrorMessage = "platform_subscription.error.overlapping";
            } catch (final Throwable oops) {
                if (ApplicationConfig.DEBUG) {
                    oops.printStackTrace();
                }
                globalErrorMessage = "misc.commit.error";
            }

        }

        return this.createEditModelAndView("platform_subscriptions/new", "platform_subscriptions/create.do", globalErrorMessage, binding, platformSubscription);
    }
}
