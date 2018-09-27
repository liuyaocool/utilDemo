package com.liuyao.demo.test;

import com.liuyao.demo.anno.MyAnno;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import com.liuyao.demo.util.MyClassUtil;

public class TestAnnotation {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        System.out.println(new Date().getDate());


        MyClassUtil.getClasses("com.liuyao.demo.entity");

        System.out.println("++++++++++++++_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_");
        try {
            Class clazz = Class.forName("com.liuyao.demo.entity.Hero");

            //判断是否有这个注解
            boolean hasAnno = clazz.isAnnotationPresent(MyAnno.class);

            if (hasAnno){
                MyAnno myAnno = (MyAnno) clazz.getAnnotation(MyAnno.class);

                System.out.println(myAnno.value());
            }

            //获得方法上的注解
            Method[] methods = clazz.getMethods();
            for (Method method: methods) {
                boolean methodHasAnno = method.isAnnotationPresent(MyAnno.class);
                if (methodHasAnno){

                    MyAnno myAnno =  method.getAnnotation(MyAnno.class);
                    System.out.println(method + "/" + myAnno.value());
                }
            }

            //获得方法注解值
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                field.setAccessible(true);
                boolean fieldHasAnno = field.isAnnotationPresent(MyAnno.class);
                if (fieldHasAnno) {

                    MyAnno myAnno = field.getAnnotation(MyAnno.class);
                    System.out.println(field + "/" + myAnno.value());
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        Runnable
    }
}
