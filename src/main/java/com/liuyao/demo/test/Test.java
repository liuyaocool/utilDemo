package com.liuyao.demo.test;

public class Test {


    public static void main(String[] args) {

//        System.out.println(new Double("12.1").intValue());
//        System.out.println(new Double("12.1").toString());
//        System.out.println(new Double("12.0").intValue());
//        System.out.println(12.0%1 == 0);
//        System.out.println(12.1%1 == 0);

        int x = 1310 - 1540;int y = 167 - 136;ss(x,y);
        x = 1540 - 1800;y = 136 - 78;ss(x, y);

        x = 2470 - 2890;y = 194 - 148;ss(x, y);
        x = 2890 - 3380;y = 148 - 22;ss(x, y);

    }

    private static void ss(int x, int y){
        System.out.println(Math.sqrt(x*x+y*y));
    }
}
