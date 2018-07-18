package com.liuyao.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil{

    /**
     * 转换时间格式
     */
    public static String getDateFormat(Date date, String format){

        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    public static void main(String[] args){

        String dateStr = getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(dateStr);

    }

}
