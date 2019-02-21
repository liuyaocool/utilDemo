package com.liuyao.demo.test.study;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class TestDemo{
	
	public static void main(String[] args) {
		Test_T_fanXing<Integer> inte = new Test_T_fanXing<Integer>(77);
		inte.showType();
		System.out.println("=============================");
		
		Test_T_fanXing<String> stringT = new Test_T_fanXing<String>("stri");
		stringT.showType();
		System.out.println("=========================");
		
		Test_T_Collection<ArrayList, Object, Object> collectionT = new Test_T_Collection<>(new ArrayList());
		collectionT.showType();
		System.out.println("---------------------------------");
		//错误写法<>中内容应该保持一致
//		Test_T_Collection<Collection> collectionColl = new Test_T_Collection<ArrayList>(new ArrayList());
		
	}
}

class Test_T_fanXing<T> {

	private T ob;//泛型成员变量
	
	public Test_T_fanXing(T ob){
		this.ob = ob;
	}
	
	public T getOb() {
		return ob;
	}

	public void setOb(T ob) {
		this.ob = ob;
	}

	void showType() {

		System.out.println("T的实际类型是:" + ob.getClass().getName());
	}
	
}

//泛型可以继承一个类多个接口,但类必须放在第一个
//泛型可以定义多个  ','分隔
class Test_T_Collection<T extends Collection & Serializable, T1 ,T2>{
	
	private T ob;//泛型成员变量
	private T2 oo;
	private T1 bb;
	
	public Test_T_Collection(T ob){
		this.ob = ob;
	}
	
	public T getOb() {
		return ob;
	}

	public void setOb(T ob) {
		this.ob = ob;
	}

	void showType() {

		System.out.println("T的实际类型是:" + ob.getClass().getName());
	}
	
	<T> T showVoid(T t) {
		
		System.out.println("这是void泛型方法,输出参数:" + t);
		return t;
	}
	
	T2 showFanXing(T2 t) {
		return t;
	}
	
}

//
//class Test_TongPei<? extends Collection>{
//
//}
//


