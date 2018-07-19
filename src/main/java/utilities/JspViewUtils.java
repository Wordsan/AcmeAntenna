package utilities;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.regex.Pattern;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class JspViewUtils {
    private static HttpServletRequest getCurrentHttpRequest()
    {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            // Step through the maze that is this framework.
            while (request instanceof HttpServletRequestWrapper) {
                request = (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
            }

            if (request != null) return request;
        }

        throw new RuntimeException("Not called in the context of an HTTP request");
    }


    public static String urlForPage(String paramName, int page)
    {
        HttpServletRequest request = getCurrentHttpRequest();
        StringBuffer sb = request.getRequestURL();

        String queryString = request.getQueryString().replaceFirst("(^|&)" + Pattern.quote(paramName) + "($|=[^&]*)", "");
        sb.append("?");
        if (!queryString.isEmpty()) {
            sb.append(queryString);
            sb.append("&");
        }
        sb.append(paramName);
        sb.append("=");
        sb.append(Integer.toString(page));
        return sb.toString();
    }

    public static String escapeJs(String textToEscape)
    {
        return StringEscapeUtils.escapeEcmaScript(textToEscape);
    }

}
