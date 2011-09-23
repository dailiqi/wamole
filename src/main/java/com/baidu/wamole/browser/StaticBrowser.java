package com.baidu.wamole.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.exception.TestException;

public class StaticBrowser implements LifeCycle {
	Logger logger = LoggerFactory.getLogger(StaticBrowser.class);
	private String invoker;
	private BrowserInvoker invokerinstance;
	// 浏览器监听步长
	private int step;
	private Browser browser;
	// 浏览器路径
	private String path;
	private String host;
	// 是否已启动
	private boolean started;

	public int faultCount;

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public void doStart() throws TestException {
		try {
			if (null == invokerinstance) {
				initInvoker();
			}
			invokerinstance.invoke(this);
		} catch (TestException e) {
			e.printStackTrace();
		}
	}

	private void initInvoker() {
		try {
			invokerinstance = (BrowserInvoker) Class.forName(invoker)
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doStop() throws TestException {
		try {
			if (null == invokerinstance) {
				initInvoker();
			}
			invokerinstance.stop(this);
		} catch (TestException e) {
			e.printStackTrace();
		}
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

}
