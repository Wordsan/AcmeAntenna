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

import domain.Antenna;
import domain.User;
import security.Authority;
import services.AntennaService;
import services.SatelliteService;
import utilities.ApplicationConfig;
import utilities.CheckUtils;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/antennas")
public class AntennaController extends AbstractController {
    @Autowired private AntennaService antennaService;
    @Autowired private SatelliteService satelliteService;

    @RequestMapping("/index")
    public ModelAndView index()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        List<Antenna> antennas = antennaService.findAllForPrincipal();
        ModelAndView result = new ModelAndView("antennas/index");
        result.addObject("antennas", antennas);
        return result;
    }

    @RequestMapping("/show")
    public ModelAndView show(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        Antenna antenna = antennaService.getByIdForShow(id);
        ModelAndView result = new ModelAndView("antennas/show");
        result.addObject("antenna", antenna);
        return result;
    }

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        Antenna antenna = new Antenna();
        antenna.setUser((User) getPrincipal());
        return createEditModelAndView("antennas/new", "antennas/create.do", null, null, antenna);
    }

    public ModelAndView createEditModelAndView(
            String viewName, String formAction, BindingResult binding, String globalErrorMessage, Antenna antenna
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                viewName,
                binding,
                globalErrorMessage
        );

        result.addObject("formAction", formAction);
        result.addObject("antenna", antenna);
        result.addObject("satellites", satelliteService.findAllForIndex());

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("antenna") @Valid Antenna antenna,
            BindingResult binding,
            RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        String globalErrorMessage = null;

        if (!binding.hasErrors()) {
            try {
                antenna = antennaService.create(antenna);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
                return ControllerUtils.redirect("/antennas/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditModelAndView("antennas/new", "antennas/create.do", binding, globalErrorMessage, antenna);
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam("id") int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        Antenna antenna = antennaService.getByIdForEdit(id);
        return createEditModelAndView("antennas/edit", "antennas/update.do", null, null, antenna);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(
            @ModelAttribute("antenna") @Valid Antenna antenna,
            BindingResult binding, RedirectAttributes redir
    )
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        String globalErrorMessage = null;
        if (!binding.hasErrors()) {
            try {
                antenna = antennaService.update(antenna);
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");

                return ControllerUtils.redirect("/antennas/index.do");
            } catch (Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();
                globalErrorMessage = "misc.commit.error";
            }
        }

        return createEditModelAndView("antennas/edit", "antennas/update.do", binding, globalErrorMessage, antenna);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute("id") int id, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        try {
            antennaService.delete(id);
            redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
        } catch (Throwable oops) {
            if (ApplicationConfig.DEBUG) oops.printStackTrace();
            redir.addFlashAttribute("globalErrorMessage", "misc.commit.error");
        }
        return ControllerUtils.redirect("/antennas/index.do");
    }

}