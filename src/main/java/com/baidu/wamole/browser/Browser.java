package com.baidu.wamole.browser;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.exception.TestException;

public class Browser {
	public static BrowserBuilder build(String ip, String userAgent) {
		Browser browser = new Browser();
		browser.setIp(ip);
		return new BrowserBuilder(browser, userAgent);
	}

	Logger logger = LoggerFactory.getLogger(Browser.class);
	private String id;
	// 浏览器名称
	private String name;
	// 浏览器版本
	private String version;
	// 操作系统
	private String os;
	// 浏览器所在IP
	private String ip;
	// 是否激活状态-- 表示有任务正在运行
	private boolean active;
	// 启动中浏览器最后通知时间
	protected long lastNoticeTime;

	public void notice() throws TestException {
		lastNoticeTime = System.currentTimeMillis();
		logger.debug(this.getName() + " " + this.getVersion()
				+ " is notice!!!notice time = "
				+ new Date(System.currentTimeMillis()));
	}

	public long getLastNoticeTime() {
		return lastNoticeTime;
	}

	public void setLastNoticeTime(long lastNoticeTime) {
		this.lastNoticeTime = lastNoticeTime;
	}

	public boolean isActive() {
		return active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEqual(Browser another) {
		if (another.getIp().equals(this.getIp())
				&& another.getName().equals(this.name)
				&& another.getVersion().equals(this.version)) {
			return true;
		}
		return false;
	}

	public static class BrowserBuilder {
		private Browser browser;
		private String ua;

		public BrowserBuilder(Browser browser, String userAgent) {
			this.ua = userAgent.toLowerCase();
			this.browser = browser;
		}

		public void parseOS() {
			if (ua.indexOf("windows nt 6.1") > -1) {
				browser.setOs("win7");
			} else if (ua.indexOf("windows nt 6.0") > -1) {
				browser.setOs("vista");
			} else if (ua.indexOf("windows nt 5.2") > -1) {
				browser.setOs("2003");
			} else if (ua.indexOf("windows nt 5.1") > -1) {
				browser.setOs("xp");
			} else if (ua.indexOf("windows nt 5.0") > -1) {
				browser.setOs("2000");
			} else if (ua.indexOf("blackberry") > -1) {
				browser.setOs("blackberry");
			} else if (ua.indexOf("iphone") > -1) {
				browser.setOs("iphone");
			} else if (ua.indexOf("ipod") > -1) {
				browser.setOs("ipod");
			} else if (ua.indexOf("ipad") > -1) {
				browser.setOs("ipad");
			} else if (ua.indexOf("symbian") > -1) {
				browser.setOs("symbian");
			} else if (ua.indexOf("webos") > -1) {
				browser.setOs("webos");
			} else if (ua.indexOf("android") > -1) {
				browser.setOs("android");
			} else if (ua.indexOf("windows phone") > -1) {
				browser.setOs("winmo");
			} else if (ua.indexOf("os x 10.4") > -1
					|| ua.indexOf("os x 10_4") > -1) {
				browser.setOs("osx10.4");
			} else if (ua.indexOf("os x 10.5") > -1
					|| ua.indexOf("os x 10_5") > -1) {
				browser.setOs("osx10.5");
			} else if (ua.indexOf("os x 10.6") > -1
					|| ua.indexOf("os x 10_6") > -1) {
				browser.setOs("osx10.6");
			} else if (ua.indexOf("os x") > -1) {
				browser.setOs("osx");
			} else if (ua.indexOf("linux") > -1) {
				browser.setOs("linux");
			}
		}

		public void parseBrowser() {
			if (ua.indexOf("msie") > -1 && ua.indexOf("windows phone") > -1) {
				browser.setName("winmo");
			} else if (ua.indexOf("msie") > -1) {
				browser.setName("msie");
			} else if (ua.indexOf("konqueror") > -1) {
				browser.setName("konqueror");
			} else if (ua.indexOf("chrome") > -1) {
				browser.setName("chrome");
			} else if (ua.indexOf("webos") > -1) {
				browser.setName("webos");
			} else if (ua.indexOf("android") > -1
					&& ua.indexOf("mobile safari") > -1) {
				browser.setName("android");
			} else if (ua.indexOf("series60") > -1) {
				browser.setName("s60");
			} else if (ua.indexOf("blackberry") > -1) {
				browser.setName("blackberry");
			} else if (ua.indexOf("opera mobi") > -1) {
				browser.setName("operamobile");
			} else if (ua.indexOf("fennec") > -1) {
				browser.setName("fennec");
			} else if (ua.indexOf("webkit") > -1 && ua.indexOf("mobile") > -1) {
				browser.setName("mobilewebkit");
			} else if (ua.indexOf("webkit") > -1) {
				browser.setName("webkit");
			} else if (ua.indexOf("presto") > -1) {
				browser.setName("presto");
			} else if (ua.indexOf("gecko") > -1) {
				browser.setName("gecko");
			}
		}

		public void parseVersion() {

			Matcher matcher = Pattern
					.compile(
							".+(rv|webos|applewebkit|presto|msie|konqueror)[\\/: ]([0-9a-z.]+)")
					.matcher(ua);
			if (matcher.find()) {
				browser.setVersion(matcher.group(2));
			}
			Matcher matcher2 = Pattern
					.compile(
							".*(webos|fennec|series60|blackberry[0-9]*[a-z]*)[\\/: ]([0-9a-z.]+)")
					.matcher(ua);
			if (matcher2.find()) {
				browser.setVersion(matcher2.group(2));
			}
			Matcher matcher3 = Pattern.compile("ms-rtc lm 8").matcher(ua);
			if (matcher3.find()) {
				browser.setVersion("8.0as7.0");
			}
		}

		public Browser build() {
			parseOS();
			parseBrowser();
			parseVersion();
			this.browser.lastNoticeTime = System.currentTimeMillis();
			this.browser.id = String.valueOf(new Random().nextInt(4));
			return browser;
		}
	}
}
