package com.baidu.wamole.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class WamoleTest {
	private Wamole wamole;

	@Before
	public void init() throws IOException, URISyntaxException {
		System.out.println(this.getClass().getResource("").getPath());
		File file = new File(this.getClass().getResource("").toURI());
		wamole = new Wamole(file);
	}

	@Test
	public void loadTest() {
//		wamole.getConfig()wamole;
		Assert.assertEquals(1, wamole.getProjectList().size());
		Assert.assertEquals("t", wamole.getProjectList().get(0).getName());
	}
}
