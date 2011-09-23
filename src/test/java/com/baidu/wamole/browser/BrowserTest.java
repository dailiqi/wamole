package com.baidu.wamole.browser;

import junit.framework.Assert;

import org.junit.Test;

public class BrowserTest {

	@Test
	public void build1Test() {
		String userAgent = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1";
		String ip = "127.0.0.1";

		Browser browser = Browser.build(ip, userAgent).build();
		Assert.assertEquals("127.0.0.1", browser.getIp());
		Assert.assertEquals("chrome", browser.getName());
		Assert.assertEquals("xp", browser.getOs());
		// Assert.assertEquals("13", browser.getVersion());
	}

	@Test
	public void build2Test() {
		String userAgent = "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2";
		String ip = "127.0.0.1";

		Browser browser = Browser.build(ip, userAgent).build();
		Assert.assertEquals("127.0.0.1", browser.getIp());
		Assert.assertEquals("gecko", browser.getName());
		Assert.assertEquals("xp", browser.getOs());
		Assert.assertEquals("6.0.2", browser.getVersion());
	}

	@Test
	public void build3Test() {
		String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; InfoPath.3; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022)";
		String ip = "127.0.0.1";

		Browser browser = Browser.build(ip, userAgent).build();
		Assert.assertEquals("127.0.0.1", browser.getIp());
		Assert.assertEquals("msie", browser.getName());
		Assert.assertEquals("xp", browser.getOs());
		Assert.assertEquals("7.0", browser.getVersion());
	}
	
	@Test
	public void build4Test() {
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1";
		String ip = "127.0.0.1";

		Browser browser = Browser.build(ip, userAgent).build();
		Assert.assertEquals("127.0.0.1", browser.getIp());
		Assert.assertEquals("webkit", browser.getName());
		Assert.assertEquals("xp", browser.getOs());
		Assert.assertEquals("533.21.1", browser.getVersion());
	}
}
