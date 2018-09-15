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

    /**
     * 转换时间类(动态)
     */
    public static String getTimeFormat(Date date,String format) {
        if(date == null ) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 判断是否为空(null或空字符串)如果是空返回true
     * @param arg 判断字符串
     */
    public static boolean isEmpty(String arg) {
        if(arg==null||"".equals(arg.trim())||arg.trim().length()==0) {
            return true;
        }
        return false;
    }

    /**
     * 转换为日期格式 string转date
     * 参数 : "2018-05-18 12:12:12", "yyyy-MM-dd hh:mm:ss"
     * @return
     */
    public static Date getDateFormat(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date dateTime = null;
        try {
            dateTime = df.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 日期相减 date2-date1
     * @return
     */
    public static int getDateDiffer(String date1, String date2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long time1 = df.parse(date1).getTime();
            long time2 = df.parse(date2).getTime();
            int reduceDay = (int) ((time2 - time1)/1000/60/60/24);
            return reduceDay;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 日期添加多少天 '年-月-日'
     * @return
     */
    public static String add_days(String date, int days) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long daysTime = days*1000*60*60*24;
        long newTime = 0;
        Date date2 = new Date();
        try {
            long time = df.parse(date).getTime();
            newTime = daysTime + time;
            date2.setTime(newTime);
            return df.format(date2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "0";
    }

}
