package com.baidu.wamole.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Planet {
//	private int id;
//	private String name;
//	private double radius;
//	private Address address;

	@XmlElement
	public int getId();


	@XmlElement
	public String getName();
	@XmlElement
	public double getRadius();

//	public void setRadius(double radius) {
//		this.radius = radius;
//	}
	/*@XmlElement
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
*/
}
