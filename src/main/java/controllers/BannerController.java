package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import javax.validation.Valid;

import domain.Banner;
import services.BannerService;

@Controller
@RequestMapping("/banners")
public class BannerController extends AbstractController {

    @Autowired
    private BannerService bannerService;


    public BannerController()
    {
        super();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list()
    {
        ModelAndView result;
        final Collection<Banner> banners = this.bannerService.findAll();
        result = new ModelAndView("banners/list");
        result.addObject("requestURI", "banners/list.do");
        result.addObject("banners", banners);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create()
    {
        ModelAndView result;
        Banner b;

        b = this.bannerService.create();
        result = this.createEditModelAndView(b);

        return result;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam final int bannerId)
    {
        ModelAndView result;
        final Banner banner = this.bannerService.findOne(bannerId);

        result = new ModelAndView("banners/delete");
        result.addObject("banner", banner);

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Banner b, final BindingResult binding)
    {
        ModelAndView result;

        if (binding.hasErrors()) {
            System.out.println(binding.getAllErrors());
            result = this.createEditModelAndView(b);
        } else {
            try {
                this.bannerService.save(b);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(b, "banner.commit.error");
            }
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(final Banner b, final BindingResult binding)
    {
        ModelAndView result;
        if (binding.hasErrors()) {
            System.out.println(binding.getAllErrors());
            result = this.createEditModelAndView(b);
        } else {
            try {
                this.bannerService.delete(b);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(b, "banner.commit.error");
            }
        }

        return result;
    }

    protected ModelAndView createEditModelAndView(final Banner b)
    {
        ModelAndView result;

        result = this.createEditModelAndView(b, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final Banner banner, final String message)
    {
        ModelAndView result;

        result = new ModelAndView("banners/create");
        result.addObject("banner", banner);
        result.addObject("message", message);

        return result;
    }

	/*
	 * @RequestMapping(value = "/randomBanner", method = RequestMethod.GET)
	 * public ModelAndView randomBanner() {
	 * ModelAndView result;
	 * final Collection<Banner> banners = this.bannerService.findAll();
	 * final String banner = this.randomPicture(banners);
	 * result = new ModelAndView("master.page");
	 * result.addObject("banner", banner);
	 * 
	 * return result;
	 * }
	 * 
	 * private String randomPicture(final Collection<Banner> list) {
	 * String res = null;
	 * final Integer aux = (int) Math.random() * (this.maxPage(list) + 1);
	 * for (final Banner b : list)
	 * if (b.getTargetPage() == aux) {
	 * res = b.getPictureUrl();
	 * break;
	 * }
	 * return res;
	 * 
	 * }
	 * 
	 * private Integer maxPage(final Collection<Banner> list) {
	 * Integer res = 0;
	 * for (final Banner b : list) {
	 * final Integer aux = b.getTargetPage();
	 * if (aux >= res)
	 * res = aux;
	 * }
	 * return res;
	 * }
	 */

}
