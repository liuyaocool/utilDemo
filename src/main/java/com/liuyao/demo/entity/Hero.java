package com.liuyao.demo.entity;

import com.liuyao.demo.anno.MyAnno;

public class Hero {

    private String id;
    private String name;
    public String name1;
    private int age;

    public Hero(String name) {
        this.name = name;
    }
    public Hero() {
        System.out.println("公开无参构造方法");
    }

    private Hero(String id, String name) {
        System.out.println("私有无参构造方法");
    }

    public String getId() {
        return id;
    }

    @MyAnno(true)
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @MyAnno(false)
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void show1(String s){
        System.out.println("调用了：公有的，String参数的show1(): 参数 = " + s);
    }
    protected void show2(){
        System.out.println("调用了：受保护的，无参的show2()");
    }
    void show3(){
        System.out.println("调用了：默认的，无参的show3()");
    }
    private String show4(int age){
        System.out.println("调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }

}
