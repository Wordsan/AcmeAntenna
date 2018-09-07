package utilities;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ControllerUtils {
    public static ModelAndView createViewWithBinding(
            String viewName,
            BindingResult binding,
            String globalErrorMessage
    )
    {
        ModelAndView result = new ModelAndView(viewName);

        if (binding != null) {
            result.addObject("result", binding);
            if (globalErrorMessage == null && binding.getGlobalError() != null) {
                globalErrorMessage = binding
                        .getGlobalError()
                        .getDefaultMessage();
            }
        }

        if (globalErrorMessage != null) {
            result.addObject("globalErrorMessage", globalErrorMessage);
        }

        return result;
    }

    public static ModelAndView redirect(String url)
    {
        RedirectView view = new RedirectView(url);

        // Fix root-relative url handling.
        view.setContextRelative(true);

        // Do not expose model attributes, use RedirectAttributes instead.
        view.setExposeModelAttributes(false);

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

    public static ModelAndView redirectToReturnAction()
    {
        String returnAction = null;

        returnAction = HttpServletUtils.getCurrentHttpRequest().getParameter("returnAction");

        if (returnAction == null || returnAction.isEmpty()) {
            returnAction = "/welcome/index.do";
        }

        return ControllerUtils.redirect(returnAction);
    }
}
