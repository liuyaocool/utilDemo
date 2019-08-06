package com.liuyao.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtils {

    private static final String[] IP_HEADERS = {"x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP"};

    public static String getRemoteAddr() {
        HttpServletRequest request = ServletUtils.getRequest();
        String ip = null;
        for (int i = 0; i < IP_HEADERS.length; i++) {
            if(isNotIp(ip)) {
                ip = request.getHeader(IP_HEADERS[i]);
            }
        }
        if(isNotIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前网络ip
     * @return
     */
    public static String getAddr(){
        String ip = getRemoteAddr();
        if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
            //根据网卡取本机配置的IP
            try {
                InetAddress inet = InetAddress.getLocalHost();
                ip= inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip!=null && ip.length()>15 && ip.indexOf(",")>0){ //"***.***.***.***".length() = 15
            ip = ip.substring(0,ip.indexOf(","));
        }
        return ip;
    }

    private static boolean isNotIp(String ip){
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

}
