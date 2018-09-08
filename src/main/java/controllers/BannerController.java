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

import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.Banner;
import domain.Satellite;
import security.Authority;
import services.BannerService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/banners")
public class BannerController extends AbstractController {

    @Autowired
    private BannerService bannerService;


    public BannerController()
    {
        super();
    }

    @RequestMapping("/index")
    public ModelAndView index()
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT, Authority.ADMINISTRATOR);

        List<Banner> banners;
        Actor principal = findPrincipal();
        if (principal instanceof Administrator) {
            banners = bannerService.findAllForIndex();
        } else {
            banners = bannerService.findMyBanners();
        }

        ModelAndView result = new ModelAndView("banners/index");
        result.addObject("banners", banners);

        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT);

        Banner banner = new Banner();
        return createEditModelAndView("banners/new", "banners/create.do", null, null, banner);
    }

    private ModelAndView createEditModelAndView(String viewName, String formAction, String globalErrorMessage, BindingResult binding, Banner banner)
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                viewName,
                "banner",
                banner,
                binding,
                globalErrorMessage
        );

        result.addObject("formAction", formAction);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("banner") @Valid Banner banner,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {
            try {
                banner = bannerService.create(banner);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/banners/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditModelAndView("banners/new", "banners/create.do", globalErrorMessage, binding, banner);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("id") int id, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        try {
            bannerService.delete(id);
            redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            return ControllerUtils.redirectToReturnAction();
        } catch (final Throwable oops) {
            if (ApplicationConfig.DEBUG) {
                oops.printStackTrace();
            }
            redir.addFlashAttribute("globalErrorMessage", "misc.commit.error");
            return ControllerUtils.redirectToReturnAction();
        }
    }
}
