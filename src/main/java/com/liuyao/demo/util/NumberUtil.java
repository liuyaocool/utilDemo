package com.liuyao.demo.util;

/**
 * @author liuyao
 * @date 2019/9/24 5:59 PM
 * @Description
 **/
public class NumberUtil {

    public static double format(String number,int points){
        return Double.valueOf(String.format("%."+points+"f", Double.parseDouble(number)));
    }
}
