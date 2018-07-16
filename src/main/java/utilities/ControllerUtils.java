package utilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ControllerUtils {
	public static ModelAndView createViewWithBinding(
			String viewName,
			BindingResult binding,
			boolean error, String message
	)
	{
		ModelAndView result = new ModelAndView(viewName);

		if (binding != null) {
			result.addObject("result", binding);
			if (message == null
			&& binding.getGlobalError() != null) {
				message = binding
					.getGlobalError()
					.getDefaultMessage();
			}
		}

		if (message != null) {
			result.addObject("message", message);
		}

		return result;
	}

	public static ModelAndView redirect(String url)
	{
		RedirectView view = new RedirectView(url);

		// Fix root-relative url handling.
		view.setContextRelative(true);

		// Allow adding query parameters to the ModelAndView.
		view.setExposeModelAttributes(true);

		ModelAndView result = new ModelAndView(view);
		return result;
	}

	public static <Type> Map<Type, String> convertToMapForSelect(
			Collection<Type> collection
			)
	{
		Map<Type, String> result = new HashMap<Type, String>();

		for (Type obj : collection) {
			result.put(obj, obj.toString());
		}

		return result;
	}

	public static String findGlobalErrorMessage(
			Set<ConstraintViolation<?>> violations
			)
	{
		String result = null;

		for (ConstraintViolation<?> violation : violations) {
			String path = violation.getPropertyPath().toString();

			if (path == null || path.equals("")) {
				result = violation.getMessage();
				break;
			}
		}

		return result;
	}
}
