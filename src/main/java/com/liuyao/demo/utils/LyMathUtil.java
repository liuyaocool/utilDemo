package com.liuyao.demo.utils;

public class LyMathUtil {


    private static String subtract(double a, double b){
        String zf = "";//结果正负
        if (a < b){
            double c = a;
            a = b;
            b = c;
            zf = "-";
        }
        String[] as = String.valueOf(a).split("\\.");
        String[] bs = String.valueOf(b).split("\\.");
        while (as[1].length() > bs[1].length()) bs[1] += "0";
        while (as[1].length() < bs[1].length()) as[1] += "0";
        int decimallen = as[1].length();
        long[] al = new long[]{Long.parseLong(as[0]), Long.parseLong(as[1])};
        long[] bl = new long[]{Long.parseLong(bs[0]), Long.parseLong(bs[1])};
        //如果小数需要借位
        if (al[1] < bl[1]){
            al[0] --;
            al[1] = Long.parseLong("1" + as[1]);
        }
        as[0] = String.valueOf(al[0] - bl[0]);
        as[1] = String.valueOf(al[1] - bl[1]);
        while (as[1].length() < decimallen){
            as[1] = "0" + as[1];
        }
        return zf + as[0] + "." + as[1];
    }

    public static void main(String[] args) {
        System.out.println(subtract(117.1533899009, 117.15347052216885));
        System.out.println(117.1533899009 - 117.15347052216885);

    }
}
