package utilities;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class JspViewUtils {
    public static String urlForPage(String paramName, int page)
    {
        HttpServletRequest request = HttpServletUtils.getCurrentHttpRequest();
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

}
