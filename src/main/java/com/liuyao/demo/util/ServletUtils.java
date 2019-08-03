package com.liuyao.demo.util;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.Validate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {

    public static final String DEFAULT_PARAMS_PARAM = "params";
    public static final String DEFAULT_PARAM_PREFIX_PARAM = "param_";
    private static String[] staticFiles;
    private static String[] staticFileExcludeUri;

    private ServletUtils() { }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;

        try {
            request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
            return request == null ? null : request;
        } catch (Exception var2) {
            return null;
        }
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;

        try {
            response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
            return response == null ? null : response;
        } catch (Exception var2) {
            return null;
        }
    }

    public static void redirectUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            if (isAjaxRequest(request)) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + url);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        } else {
            String xRequestedWith = request.getHeader("X-Requested-With");
            if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
                return true;
            } else {
                String uri = request.getRequestURI();
//                if (StringUtils.inStringIgnoreCase(uri, new String[]{".json", ".xml"})) {
//                    return true;
//                } else {
//                    String ajax = request.getParameter("__ajax");
//                    return StringUtils.inStringIgnoreCase(ajax, new String[]{"json", "xml"});
//                }
            }
        }
        return false;
    }

    public static boolean isStaticFile(String uri) {
//        if (staticFiles == null) {
//            PropertiesUtils pl = PropertiesUtils.getInstance();
//
//            try {
//                staticFiles = StringUtils.split(pl.getProperty("web.staticFile"), ",");
//                staticFileExcludeUri = StringUtils.split(pl.getProperty("web.staticFileExcludeUri"), ",");
//            } catch (NoSuchElementException var5) {
//                ;
//            }
//
//            if (staticFiles == null) {
//                try {
//                    throw new Exception("检测到“jeesite.yml”中没有配置“web.staticFile”属性。配置示例：\n#静态文件后缀\nweb.staticFile=.css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk");
//                } catch (Exception var6) {
//                    var6.printStackTrace();
//                }
//            }
//        }
//
//        if (staticFileExcludeUri != null) {
//            String[] var7 = staticFileExcludeUri;
//            int var2 = var7.length;
//
//            for(int var3 = 0; var3 < var2; ++var3) {
//                String s = var7[var3];
//                if (StringUtils.contains(uri, s)) {
//                    return false;
//                }
//            }
//        }
//
//        return StringUtils.endsWithAny(uri, staticFiles);
        return false;
    }

    public static String renderResult(String result, String message) {
        return renderResult((String)result, message, (Object)null);
    }

    public static String renderResult(String result, String message, Object data) {
//        Map<String, Object> resultMap = MapUtils.newHashMap();
//        resultMap.put("result", result);
//        resultMap.put("message", message);
//        if (data != null) {
//            if (data instanceof Map) {
//                resultMap.putAll((Map)data);
//            } else {
//                resultMap.put("data", data);
//            }
//        }
//
//        HttpServletRequest request = getRequest();
//        String uri = request.getRequestURI();
//        if (!StringUtils.endsWithIgnoreCase(uri, ".xml") && !StringUtils.equalsIgnoreCase(request.getParameter("__ajax"), "xml")) {
//            String functionName = request.getParameter("__callback");
//            return StringUtils.isNotBlank(functionName) ? JsonMapper.toJsonp(functionName, resultMap) : JsonMapper.toJson(resultMap);
//        } else {
//            return XmlMapper.toXml(resultMap);
//        }
        return "";
    }

    public static String renderResult(HttpServletResponse response, String result, String message) {
        return renderString(response, renderResult(result, message), (String)null);
    }

    public static String renderResult(HttpServletResponse response, String result, String message, Object data) {
        return renderString(response, renderResult(result, message, data), (String)null);
    }

    public static String renderObject(HttpServletResponse response, Object object) {
//        HttpServletRequest request = getRequest();
//        String uri = request.getRequestURI();
//        if (!StringUtils.endsWithIgnoreCase(uri, ".xml") && !StringUtils.equalsIgnoreCase(request.getParameter("__ajax"), "xml")) {
//            String functionName = request.getParameter("__callback");
//            return StringUtils.isNotBlank(functionName) ? renderString(response, JsonMapper.toJsonp(functionName, object)) : renderString(response, JsonMapper.toJson(object));
//        } else {
//            return renderString(response, XmlMapper.toXml(object));
//        }
        return "";
    }

    public static String renderString(HttpServletResponse response, String string) {
        return renderString(response, string, (String)null);
    }

    public static String renderString(HttpServletResponse response, String string, String type) {
        try {
            if (type == null) {
//                if ((!StringUtils.startsWith(string, "{") || !StringUtils.endsWith(string, "}")) && (!StringUtils.startsWith(string, "[") || !StringUtils.endsWith(string, "]"))) {
//                    if (StringUtils.startsWith(string, "<") && StringUtils.endsWith(string, ">")) {
//                        type = "application/xml";
//                    } else {
//                        type = "text/html";
//                    }
//                } else {
//                    type = "application/json";
//                }
            }

            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return null;
    }

    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getParameter(name);
    }

    public static Map<String, Object> getParameters() {
        return getParameters(getRequest());
    }

    public static Map<String, Object> getParameters(ServletRequest request) {
//        return (Map)(request == null ? MapUtils.newHashMap() : getParametersStartingWith(request, ""));
        return new HashMap<>();
    }

    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null", new Object[0]);
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap();
        String pre = prefix;
        if (prefix == null) {
            pre = "";
        }

        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            if ("".equals(pre) || paramName.startsWith(pre)) {
                String unprefixed = paramName.substring(pre.length());
                String[] values = request.getParameterValues(paramName);
                if (values != null && values.length != 0) {
                    if (values.length > 1) {
                        params.put(unprefixed, values);
                    } else {
                        params.put(unprefixed, values[0]);
                    }
                } else {
                    values = new String[0];
                }
            }
        }

        return params;
    }

    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        StringBuilder queryStringBuilder = new StringBuilder();
        String pre = prefix;
        if (prefix == null) {
            pre = "";
        }

        Iterator it = params.entrySet().iterator();

        while(it.hasNext()) {
            Entry<String, Object> entry = (Entry)it.next();
            queryStringBuilder.append(pre).append((String)entry.getKey()).append("=").append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append("&");
            }
        }

        return queryStringBuilder.toString();
    }

    public static Map<String, Object> getExtParams(ServletRequest request) {
        Map<String, Object> paramMap = null;
//        String params = StringUtils.trim(request.getParameter("params"));
//        if (StringUtils.isNotBlank(params) && StringUtils.startsWith(params, "{")) {
//            paramMap = (Map)JsonMapper.fromJson(params, Map.class);
//        } else {
//            paramMap = getParametersStartingWith(getRequest(), "param_");
//        }

        return paramMap;
    }

    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }

    public static void setNoCacheHeader(HttpServletResponse response) {
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if (ifModifiedSince != -1L && lastModified < ifModifiedSince + 1000L) {
            response.setStatus(304);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while(!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(304);
                response.setHeader("ETag", etag);
                return false;
            }
        }

        return true;
    }
}
