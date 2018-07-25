package utilities;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
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

        String queryString = request.getQueryString();
        if (queryString == null) queryString = "";
        queryString = queryString.replaceFirst("(^|&)" + Pattern.quote(paramName) + "($|=[^&]*)", "");
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
    public static String escapeUrlParam(String textToEscape)
    {
        try {
            return UriUtils.encodeQueryParam(textToEscape, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 is guaranteed by Java. This will never happen.
            throw new RuntimeException(e);
        }
    }

    public static String currentRelativeUrl()
    {
        HttpServletRequest request = getCurrentHttpRequest();
        String relativePath = request.getServletPath();
        if (relativePath.startsWith("/")) relativePath = relativePath.substring(1);
        String queryString = request.getQueryString();
        if (queryString == null) queryString = "";
        if (!queryString.isEmpty()) {
            relativePath += "?" + queryString;
        }
        return relativePath;
    }

}
