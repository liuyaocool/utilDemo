package com.liuyao.demo.utils;

public class DateLy {

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";//
    public enum FMT {
        DAY(86400000),//天
        HOUR24(3600000),//24小时
        HOUR12(1800000),//12小时
        MINUTE(60000),//分钟
        SECOND(1000),//秒
        ;
        private int num;
        FMT(int num) { this.num = num; }
        public int getNum() { return num; }
    }
    public static final int NEWDATE = 0;//当前时间
    public static final int DATE = 2;//年月日
    public static final int DATETIME = 3;//年月日时分秒
    public static final int TIME = 4;//时分秒
    private transient long times;

    public DateLy() {
        this.times = System.currentTimeMillis();
    }

    public DateLy(java.util.Date date){
        this.times = date.getTime();
    }

    public DateLy(String time, String format) throws java.text.ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        this.times = sdf.parse(time).getTime();
    }

    public java.util.Date getDate(){
        return new java.util.Date(this.times);
    }

    public String getDate(String format){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(getDate());
    }

    public DateLy add(FMT fmt, int amount){
        this.times += fmt.getNum() * amount;
        return this;
    }




}
