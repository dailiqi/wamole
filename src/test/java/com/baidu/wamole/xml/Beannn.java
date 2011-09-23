package com.baidu.wamole.xml;

import java.util.ArrayList;
import java.util.List;

public class Beannn {

	private String name;
	private int age;
	private Location location;
	private List friends = new ArrayList();

	public Beannn(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List getFriends() {
		return friends;
	}

	public void setFriends(List friends) {
		this.friends = friends;
	}
}
