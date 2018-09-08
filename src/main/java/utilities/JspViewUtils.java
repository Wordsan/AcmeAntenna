package utilities;

import org.displaytag.util.ParamEncoder;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("deprecation")
public class JspViewUtils {
    public static String escapeJs(String textToEscape)
    {
        return org.apache.commons.lang3.StringEscapeUtils.escapeEcmaScript(textToEscape);
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

    public static String withoutDisplayTagParams(String url, String displayTagId)
    {
        ParamEncoder encoder = new ParamEncoder(displayTagId);
        url = withUrlParam(url, encoder.encodeParameterName("p"), null);
        url = withUrlParam(url, encoder.encodeParameterName("s"), null);
        url = withUrlParam(url, encoder.encodeParameterName("d"), null);
        url = withUrlParam(url, encoder.encodeParameterName("o"), null);
        return url;
    }

    public static String withUrlParam(String url, String paramName, String paramValue)
    {
        String[] parts = url.split("\\?", 2);
        String base = parts[0];
        String queryString = "";
        if (parts.length > 1) queryString = parts[1];
        String[] params = queryString.split("&");

        StringBuilder result = new StringBuilder();
        result.append(base);

        boolean first = true;
        for (String param : params) {
            if (!param.startsWith(paramName + "=")) {
                if (first) {
                    first = false;
                    result.append("?");
                } else {
                    result.append("&");
                }
                result.append(param);
            }
        }

        if (paramValue != null) {
            if (first) {
                first = false;
                result.append("?");
            } else {
                result.append("&");
            }

            result.append(paramName).append("=").append(escapeUrlParam(paramValue));
        }

        return result.toString();
    }

}
