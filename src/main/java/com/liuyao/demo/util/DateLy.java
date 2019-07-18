package com.liuyao.demo.util;

public class DateLy {

    public static final int NEWDATE = 0;//当前时间
    public static final int DATE = 2;//年月日
    public static final int DATETIME = 3;//年月日时分秒
    public static final int TIME = 4;//时分秒
    public static final int DAY = 86400000;//天
    public static final int HOUR24 = 3600000;//小时
    public static final int HOUR12 = 1800000;//小时
    public static final int MINUTE = 60000;//分钟
    public static final int SECOND = 1000;//秒

    private long times;

    public DateLy() {
        this.times = new java.util.Date().getTime();
    }

    public DateLy(java.util.Date date){
        this.times = date.getTime();
    }

    public DateLy(String time, String format) throws java.text.ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        this.times = sdf.parse(time).getTime();
    }

    public java.util.Date getDate(){
        return new java.util.Date(times);
    }

    public String getDate(String format){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(getDate());
    }

    public DateLy add(int times, int amount){
        this.times += times * amount;
        return this;
    }
}
