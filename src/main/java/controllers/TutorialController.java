package controllers;

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
import utilities.CheckUtils;
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

    @RequestMapping("/new")
    public ModelAndView new_()
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        Tutorial tutorial = new Tutorial();
        tutorial.setUser((User) getPrincipal());
        tutorial.setLastUpdateTime(new Date());
        return newForm(tutorial, null, null);
    }

    public ModelAndView newForm(
            Tutorial tutorial,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "tutorials/new",
                binding,
                globalErrorMessage
        );

        result.addObject("tutorial", tutorial);

        return result;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView create(
            @ModelAttribute("tutorial") @Valid Tutorial tutorial,
            BindingResult binding,
            RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        ModelAndView result;
        if (binding.hasErrors()) {
            result = newForm(tutorial, binding, null);
        } else {
            try {
                tutorial = service.create(tutorial);
                result = ControllerUtils.redirect("/tutorials/show.do");
                redir.addAttribute("id", tutorial.getId());
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            } catch(Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();

                result = newForm(tutorial,
                                 binding,
                                 "misc.commit.error");
            }
        }

        return result;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam("id") int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        Tutorial tutorial = service.getByIdForEdit(id);
        return editForm(tutorial, null, null);
    }

    public ModelAndView editForm(
            Tutorial tutorial,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = ControllerUtils.createViewWithBinding(
                "tutorials/edit",
                binding,
                globalErrorMessage
        );

        result.addObject("tutorial", tutorial);

        return result;
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public ModelAndView update(
            @ModelAttribute("tutorial") @Valid Tutorial tutorial,
            BindingResult binding, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        ModelAndView result;
        if (binding.hasErrors()) {
            result = editForm(tutorial, binding, null);
        } else {
            try {
                tutorial = service.update(tutorial);
                result = ControllerUtils.redirect("/tutorials/show.do");
                redir.addAttribute("id", tutorial.getId());
                redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
            } catch(Throwable oops) {
                if (ApplicationConfig.DEBUG) oops.printStackTrace();

                result = newForm(tutorial,
                                 binding,
                                 "misc.commit.error");
            }
        }

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
        CheckUtils.checkPrincipalAuthority(Authority.USER);

        ModelAndView result;
        if (binding.hasErrors()) {
            result = show(tutorialComment.getTutorial().getId(), null, tutorialComment);
            result.addObject("result", binding);
        } else {
            tutorialCommentService.create(tutorialComment);
            result = ControllerUtils.redirect("/tutorials/show.do");
            redir.addAttribute("id", tutorialComment.getTutorial().getId());
            redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
        }

        return result;
    }

    @RequestMapping(value="/deleteComment", method=RequestMethod.POST)
    public ModelAndView deleteComment(@RequestParam("id") int id, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        TutorialComment comment = tutorialCommentService.getByIdForDelete(id);

        Tutorial tutorial = comment.getTutorial();
        tutorialCommentService.delete(id);

        ModelAndView result = ControllerUtils.redirect("/tutorials/show.do");
        redir.addAttribute("id", tutorial.getId());
        redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
        return result;
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public ModelAndView delete(@RequestParam("id") int id, RedirectAttributes redir)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        service.delete(id);

        ModelAndView result = ControllerUtils.redirect("/tutorials/index.do");
        redir.addFlashAttribute("globalSuccessMessage", "misc.operationCompletedSuccessfully");
        return result;
    }
}