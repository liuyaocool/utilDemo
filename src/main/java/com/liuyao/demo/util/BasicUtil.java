package com.liuyao.demo.util;

public class BasicUtil {

    private static final String[] result = {"null","int", "double"};

    //判断是否是数字
    private static String isNumber(Object obj){
        if (null == obj){ return result[0]; }
        String num = "0123456789";
        char[] aa = (String.valueOf(obj)).toCharArray();
        if (aa.length == 0) { return result[0]; }
        int point = 0;
        for (int i = 0; i < aa.length; i++) {
            if ('.' == aa[i]){ point++; continue;}
            if (num.indexOf(aa[i]) < 0){ return result[0]; }
        }
        switch (point){
            case 0: return result[1];
            case 1:
                return result[2];
            default: return result[0];
        }
    }

    /**
     *
     * @param obj
     * @param fix 0：舍小数点 1：小数点进1
     * @return
     */
    public static Integer parseInt(Object obj, int fix){
        String a = isNumber(obj);
        switch (a){
            case "null": return null;
            case "int": return Integer.valueOf(String.valueOf(obj));
            case "double":
                if (Double.parseDouble(String.valueOf(obj)) % 1 > 0){
                    return removePoit(obj) + fix;
                }
                return removePoit(obj);
        }
        return null;
    }

    private static int removePoit(Object obj){
        String a = String.valueOf(obj);
        return Integer.parseInt(a.substring(0, a.indexOf(".")));
    }
}
