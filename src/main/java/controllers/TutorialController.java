package controllers;

import com.sun.tracing.dtrace.ModuleAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import javax.validation.Valid;

import domain.Tutorial;
import domain.TutorialComment;
import domain.User;
import security.Authority;
import security.LoginService;
import services.TutorialCommentService;
import services.TutorialService;
import utilities.ApplicationConfig;
import utilities.ControllerUtils;

@Controller
@RequestMapping("/tutorials")
public class TutorialController extends AbstractController {
    @Autowired private TutorialService service;
    @Autowired private TutorialCommentService tutorialCommentService;

    @RequestMapping("/index")
    public ModelAndView index()
    {
        ModelAndView result = new ModelAndView("tutorials/index");
        result.addObject("tutorials", service.findAll());
        return result;
    }

    @RequestMapping("/show")
    public ModelAndView show(@RequestParam("id") int id, @RequestParam(value = "page", required = false) Integer page, @ModelAttribute("tutorialComment") TutorialComment tutorialComment)
    {
        if (page == null) page = 0;

        ModelAndView result = new ModelAndView("tutorials/show");
        Tutorial tutorial = service.getById(id);
        result.addObject("tutorial", tutorial);
        if (LoginService.hasAuthority(Authority.USER)) {
            tutorialComment.setUser((User) getPrincipal());
            tutorialComment.setTutorial(tutorial);
            tutorialComment.setCreationTime(new Date());
            result.addObject("tutorialComment", tutorialComment);
        }
        if (LoginService.hasAuthority(Authority.USER) || LoginService.hasAuthority(Authority.ADMINISTRATOR)) {
            result.addObject("comments", tutorialCommentService.findAllForTutorial(tutorial, new PageRequest(page, getDisplayTagPageSize())));
        }
        return result;
    }

    @RequestMapping(value="/createComment", method= RequestMethod.POST)
    public ModelAndView createComment(@ModelAttribute("tutorialComment") @Valid TutorialComment tutorialComment, BindingResult binding, RedirectAttributes redir)
    {
        ModelAndView result;
        if (binding.hasErrors()) {
            result = show(tutorialComment.getTutorial().getId(), null, tutorialComment);
            result.addObject("result", binding);
        } else {
            tutorialCommentService.create(tutorialComment);
            redir.addAttribute("id", tutorialComment.getTutorial().getId());
            result = ControllerUtils.redirect("/tutorials/show.do");
        }

        return result;
    }

    @RequestMapping(value="/deleteComment", method=RequestMethod.POST)
    public ModelAndView deleteComment(@RequestParam("id") int id, RedirectAttributes redir)
    {
        TutorialComment comment = tutorialCommentService.getById(id);

        Tutorial tutorial = comment.getTutorial();
        tutorialCommentService.delete(id);

        redir.addAttribute("id", tutorial.getId());
        return ControllerUtils.redirect("/tutorials/show.do");
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public ModelAndView delete(@RequestParam("id") int id, RedirectAttributes redir)
    {
        service.delete(id);
        return ControllerUtils.redirect("/tutorials/index.do");
    }
}