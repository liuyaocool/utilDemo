package com.liuyao.demo.utils;

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

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


    public static void logError(String message, Exception... ex){
        StringBuilder msg = new StringBuilder();

        //获得方法的调用者信息
        try {
            StackTraceElement ste = new Exception().getStackTrace()[1];
            Class claz = Class.forName(ste.getClassName());
            msg.append(ste.getMethodName())
                    .append("(")
                    .append(ste.getLineNumber()).append("): ")
                    .append(message);
            LoggerFactory.getLogger(claz).error(msg.toString(), ex[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        logError("aaa", new Exception("test"));
    }

}
