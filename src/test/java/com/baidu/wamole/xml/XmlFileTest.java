package com.baidu.wamole.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.baidu.wamole.model.Wamole;

public class XmlFileTest {

	private XmlFile file;

	@Before
	public void init() {
		file = new XmlFile(new File("F:\\Workplaces\\Workplace\\WaMoleImpl\\src\\test\\java\\com\\baidu\\wamole\\xml\\config.xml"));
	}

//	@Test
//	public void testLoad() throws IOException {
////		file.unmarshal(new Beannn("Jhon" , 18));
//		Beannn bean = (Beannn) file.read();
//		Assert.assertEquals("test", bean.geztName());
////		Assert.assertEquals("listtest",bean.getLocation().getLocation());
//		List<String> list = bean.getFriends();
//		for (String string : list) {
//			System.out.println(string);
//		}
//	}
	
	@Test
	public void testLoad() throws IOException {
		Wamole wamole = (Wamole)file.read();
		wamole.load();
//		
	}
}
