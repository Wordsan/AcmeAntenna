/*
 * LegalController.java
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legal")
public class LegalController extends AbstractController {
	@RequestMapping("/about_us")
	public ModelAndView aboutUs() {
		ModelAndView result;
		String companyName;
		String address;
		String vatNumber;
		String contactEmail;

		companyName = "Acme Antenna, Inc.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		vatNumber = "R2028110A";
		contactEmail = "infoacmeantenna@acme.com";

		result = new ModelAndView("legal/about_us");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("vatNumber", vatNumber);
		result.addObject("contactEmail", contactEmail);

		return result;
	}

	@RequestMapping("/terms")
	public ModelAndView termsOfService() {
		ModelAndView result;
		String companyName;
		String address;
		String vatNumber;
		String contactEmail;
		String tabooWords;

		companyName = "Acme Antenna, Inc.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		vatNumber = "R2028110A";
		contactEmail = "termsacmeantenna@acme.com";

		result = new ModelAndView("legal/terms");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("vatNumber", vatNumber);
		result.addObject("contactEmail", contactEmail);

		return result;
	}

	@RequestMapping("/cookies")
	public ModelAndView cookiesPolicy() {
		ModelAndView result;

		result = new ModelAndView("legal/cookies");

		return result;
	}

	// About us ---------------------------------------------------------------

	@RequestMapping("/privacy")
	public ModelAndView privacyPolicy() {
		ModelAndView result;
		String companyName;
		String address;
		String contactEmail;

		companyName = "Acme Antenna, Inc.";
		address = "Avenida Reina Mercedes s/n - 41012 - Sevilla";
		contactEmail = "privacyacmeantenna@acme.com";

		result = new ModelAndView("legal/privacy");
		result.addObject("companyName", companyName);
		result.addObject("address", address);
		result.addObject("contactEmail", contactEmail);

		return result;
	}

}
