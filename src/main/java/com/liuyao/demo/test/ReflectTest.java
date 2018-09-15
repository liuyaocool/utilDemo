package com.liuyao.demo.test;

import com.liuyao.demo.entity.Hero;
import com.liuyao.demo.util.FileIOUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

    public static void main(String[] args){

        try {

            //反射机制获得类
            Class clazz = Class.forName("com.liuyao.demo.entity.Hero");//自动调用无参公有构造

            System.out.println("======================获得公有构造============================");
            //获得所有公有构造
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor c: constructors) {
//                Object obj = c.newInstance();//调用构造 报错
                System.out.println(c);
            }

            System.out.println("===================获得所有构造================================");
            //获得所有构造方法
            Constructor[] constructors1 = clazz.getDeclaredConstructors();
            for (Constructor c: constructors1) {
                c.setAccessible(true);//暴力访问 忽略访问限制
                System.out.println(c);
            }

            System.out.println("===================获得字段=====================================");
            //获得所有公有字段
            Field[] fieldArray = clazz.getFields();
            //获得所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field f :fields) {
                System.out.println(f);
            }

            System.out.println("===================获得公有字段并调用===========================");
            //获得公有字段并调用
            Field f = clazz.getField("name1");
            Object obj = clazz.getConstructor().newInstance();
            f.set(obj, "超人");
            Hero hero1 = (Hero)obj;
            System.out.println(hero1.name1);

            System.out.println("=================获得私有字段并调用=============================");
            //获得私有字段并调用
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            Object obj2 = clazz.getConstructor().newInstance();
            Hero hero2 = (Hero)obj2;
            field.set(obj2, "钢铁侠");
            System.out.println(hero2.getName());

            System.out.println("====================获得所有公有方法===========================");
            Method[] methods = clazz.getMethods();
            for (Method me : methods) {
                System.out.println(me);
            }
            System.out.println("====================获得所有方法===========================");
            Method[] methodArray = clazz.getDeclaredMethods();
            for(Method m : methodArray){
                System.out.println(m);
            }

            System.out.println("====================获得公有的show1()方法===========================");
            Method m = clazz.getMethod("show1", String.class);
            Object obj4 = clazz.getConstructor().newInstance();
            m.invoke(obj4, "刘德华");

            System.out.println("====================获得私有的show4()方法===========================");
            Method m2 = clazz.getDeclaredMethod("show4", int.class);
            m2.setAccessible(true);//解除私有限定
            Object result = m2.invoke(obj, 20);//需要两个参数，一个是要调用的对象（获取有反射），一个是实参
            System.out.println("返回值：" + result);

            System.out.println("============================结束+++++++++++====================================================");

            Hero hero = new Hero();
            hero.setAge(11);
            System.out.println(hero.getClass() == clazz);


        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public static void aaa(int i){

        System.out.println(i);
    }
}
