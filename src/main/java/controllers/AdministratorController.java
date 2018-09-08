package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.AdministratorService;
import utilities.CheckUtils;

@Controller
@RequestMapping("/administrators")
public class AdministratorController extends AbstractController {

    private @Autowired AdministratorService service;


    @RequestMapping("/dashboard")
    public ModelAndView dashboard()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        final ModelAndView result = new ModelAndView("administrators/dashboard");
        result.addObject("avgAntennaCountPerUser", this.service.findAvgAntennaCountPerUser());
        result.addObject("stdDevAntennaCountPerUser", this.service.findStdDevAntennaCountPerUser());
        result.addObject("avgAntennaSignalQuality", this.service.findAvgAntennaSignalQuality());
        result.addObject("stdDevAntennaSignalQuality", this.service.findStdDevAntennaSignalQuality());
        result.addObject("avgTutorialCountPerUser", this.service.findAvgTutorialCountPerUser());
        result.addObject("stdDevTutorialCountPerUser", this.service.findStdDevTutorialCountPerUser());
        result.addObject("avgCommentCountPerTutorial", this.service.findAvgCommentCountPerTutorial());
        result.addObject("stdDevCommentCountPerTutorial", this.service.findStdDevCommentCountPerTutorial());
        result.addObject("antennaCountPerModel", this.service.findAntennaCountPerModel());
        result.addObject("mostPopularAntennas", this.service.findMostPopularAntennas());
        result.addObject("topTutorialContributors", this.service.findTopTutorialContributors());
        result.addObject("avgRequestCountPerUser", this.service.findAvgRequestCountPerUser());
        result.addObject("stdDevRequestCountPerUser", this.service.findStdDevRequestCountPerUser());
        result.addObject("avgRatioServicedRequestsPerUser", this.service.findAvgRatioServicedRequestsPerUser());
        result.addObject("avgRatioServicedRequestsPerHandyworker", this.service.findAvgRatioServicedRequestsPerHandyworker());
        result.addObject("avgBannerCountPerAgent", this.service.findAvgBannerCountPerAgent());
        result.addObject("mostPopularAgents", this.service.findMostPopularAgentsByBanners());
        return result;
    }
}
