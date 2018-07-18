package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;

@Controller
@RequestMapping("/administrators")
public class AdministratorController extends AbstractController {
    private @Autowired AdministratorService service;

    @RequestMapping("/dashboard")
    public ModelAndView dashboard()
    {
        ModelAndView result = new ModelAndView("administrators/dashboard");
        result.addObject("avgAntennaCountPerUser", service.findAvgAntennaCountPerUser());
        result.addObject("stdDevAntennaCountPerUser", service.findStdDevAntennaCountPerUser());
        result.addObject("avgAntennaSignalQuality", service.findAvgAntennaSignalQuality());
        result.addObject("stdDevAntennaSignalQuality", service.findStdDevAntennaSignalQuality());
        result.addObject("antennaCountPerModel", service.findAntennaCountPerModel());
        result.addObject("mostPopularAntennas", service.findMostPopularAntennas());
        return result;
    }
}