package com.liuyao.demo.util;

import org.slf4j.LoggerFactory;

public class LyLogUtil {

    //日志打印方法
    public static void logInfo(Object msg){

        //获得方法的调用者信息
        try {
            StackTraceElement ste = new Exception().getStackTrace()[1];
            Class claz = Class.forName(ste.getClassName());
            StringBuilder sb = new StringBuilder();
            sb.append(ste.getMethodName())
                    .append("(").append(ste.getLineNumber()).append("): ")
                    .append(String.valueOf(msg));
            LoggerFactory.getLogger(claz).info(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}