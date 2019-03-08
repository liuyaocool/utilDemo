package com.liuyao.demo.util;

public class RegexpUtil {

	public static void main(String[] args) {
		//大小写 数字 8位正则
		String aaaa = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
		System.out.println("aa".matches(aaaa));
		System.out.println("aaaaaaaaa".matches(aaaa));
		System.out.println("AA".matches(aaaa));
		System.out.println("AAAAAAAA".matches(aaaa));
		System.out.println("11".matches(aaaa));
		System.out.println("11111111".matches(aaaa));
		System.out.println("aaAA".matches(aaaa));
		System.out.println("aaAAaaaaa".matches(aaaa));
		System.out.println("aa11".matches(aaaa));
		System.out.println("aaasdadsfdsf11".matches(aaaa));
		System.out.println("AA11".matches(aaaa));
		System.out.println("AA11342343".matches(aaaa));
		System.out.println("AAaa11".matches(aaaa));
		System.out.println("AAaa112233445".matches(aaaa));

	}
}
