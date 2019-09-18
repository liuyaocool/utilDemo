package com.liuyao.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 1, 12, 12, 12);
        System.out.println(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DATE, -7);
        String queryDate = sdf.format(calendar.getTime());
        System.out.println(queryDate);

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
    public static String addDay(String date, int days, String format) {
        format = null == format || "".equals(format.trim()) ? "yyyy-MM-dd" : format;

        DateFormat df = new SimpleDateFormat(format);
        long daysTime = days*1000*60*60*24;
        long newTime;
        try {
            long time = df.parse(date).getTime();
            newTime = daysTime + time;
            Date date2 = new Date();
            date2.setTime(newTime);
            return df.format(date2);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return "1970-01-01";
    }

    /**
     * 已知日期字符串，获得小时数
     */
    public static void getHour(String dateStr){

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            Date date = format.parse(dateStr);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            double hour24 = c1.get(Calendar.HOUR_OF_DAY);//24小时制
            double hour12 = c1.get(Calendar.HOUR);//12小时制
        } catch (Exception e){

        }
    }
}

