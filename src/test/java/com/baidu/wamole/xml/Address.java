package com.baidu.wamole.xml;

import javax.xml.bind.annotation.XmlElement;

public class Address implements Location{
	public String add;
	public String number;
	@XmlElement
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	@XmlElement
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String getLocation() {
		return this.add + this.number;
	}

}
