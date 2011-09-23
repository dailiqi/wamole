package com.baidu.wamole.browser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.browser.StaticBrowser;
import com.baidu.wamole.exception.TestException;

public class BatchInvoker implements BrowserInvoker {
	Logger logger = LoggerFactory.getLogger(STAFInvoker.class);

	@Override
	public void invoke(StaticBrowser browser, TargetURL url)
			throws TestException {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		sb.append(browser.getPath());
		sb.append("\"");
		sb.append(" ");
		sb.append("\"");
		sb.append(url.toString());
		sb.append("\"");
		logger.info("invoke command :" + sb.toString());
		try {
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			throw new TestException("batch invoke error ,cmd:" + sb.toString()
					+ ".", e);
		}
	}

	@Override
	public void restart(StaticBrowser browser, TargetURL url)
			throws TestException {
		try {
			stop(browser);
			Thread.sleep(1000);
		} catch (TestException e) {
			throw new TestException(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			invoke(browser, url);
		}
	}

	@Override
	public void invoke(StaticBrowser browser) throws TestException {
		this.invoke(browser, TargetURL.getURL(browser));
	}

	@Override
	public void restart(StaticBrowser browser) throws TestException {
		this.restart(browser, TargetURL.getURL(browser));
	}

	@Override
	public void stop(StaticBrowser browser) throws TestException {
		StringBuffer sb = new StringBuffer();
		sb.append("TASKKILL");
		sb.append(" ");
		sb.append("/F");
		sb.append(" ");
		sb.append("/IM");
		sb.append(" ");
		String path = browser.getPath();
		path = path.replace("\\", "/");

		String browserName = path.substring(path.lastIndexOf("/") + 1);
		sb.append("\"");
		sb.append(browserName);
		sb.append("\"");
		logger.info("invoke command :" + sb.toString());
		try {
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			throw new TestException("batch stop error, cmd:" + sb.toString()
					+ ".", e);
		}
	}

}
