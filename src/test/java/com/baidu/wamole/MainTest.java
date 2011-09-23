package com.baidu.wamole;

import java.util.Properties;

public class MainTest {

	
	public static void main(String[] args) {
		Properties pro = System.getProperties();
		for (Object string : pro.keySet()) {
			System.out.println("key = " + string  + ", value = " + pro.get(string));
		}
	}
}
