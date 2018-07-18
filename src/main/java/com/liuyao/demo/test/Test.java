package com.liuyao.demo.test;

public class Test {

    public static void main(String[] args){

//        FileIOUtil.importExcel(Hero.class);

        int startRowNum = -3;
        startRowNum = startRowNum < 0 ? 0 : startRowNum - 1; //设置开始行
        System.out.println(startRowNum);
        aaa(10);

    }

    public static void aaa(int i){

        System.out.println(i);
    }
}
