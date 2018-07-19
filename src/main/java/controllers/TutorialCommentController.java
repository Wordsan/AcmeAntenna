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

import domain.Antenna;
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
public class TutorialCommentController extends AbstractController {
    @Autowired private TutorialCommentService service;


}