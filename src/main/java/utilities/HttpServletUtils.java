package utilities;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletUtils {
    public static HttpServletRequest getCurrentHttpRequest()
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

    public static String currentRequestUri()
    {
        HttpServletRequest request = getCurrentHttpRequest();
        String relativePath = request.getServletPath();
        if (relativePath.startsWith("/")) relativePath = relativePath.substring(1);
        return relativePath;
    }

    public static String currentRequestUriAndParams()
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
