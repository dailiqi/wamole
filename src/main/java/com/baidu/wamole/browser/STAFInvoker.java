package com.baidu.wamole.browser;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.exception.TestException;
import com.ibm.staf.STAFHandle;
import com.ibm.staf.STAFResult;

/**
 * 浏览器invoker的staf启动形式
 * 
 * @author dailiqi
 */
public class STAFInvoker implements BrowserInvoker {
	Logger logger = LoggerFactory.getLogger(STAFInvoker.class);
	private STAFHandle handle = new STAFHandle(new Random().nextInt());

	// public STAFInvoker() throws TestException {
	// try {
	// handle = new STAFHandle("STAF_Handler_"
	// + System.currentTimeMillis());
	// } catch (STAFException e) {
	// throw new TestException("STAF start error , please check your env.");
	// }
	// }

	/**
	 * 默认对本机capture访问
	 * 
	 * @param browser
	 * @throws TestException
	 */
	public void invoke(StaticBrowser browser) throws TestException {
		invoke(browser, TargetURL.getURL(browser));
	}

	public void invoke(StaticBrowser browser, TargetURL url)
			throws TestException {
		logger.info("cmd:" + "start shell command \"" + browser.getPath()
				+ "\" " + ampersandConverse(url.toString()));
		STAFResult result = handle.submit2(browser.getHost(), "process",
				"start shell command \"\\\"" + browser.getPath() + "\\\" "
						+ ampersandConverse(url.toString()) + "\"");
		getException(result);
	}

	public void stop(StaticBrowser browser) throws TestException {
		String handleNo = getHandle(browser);
		logger.info("stop handle No:" + handleNo);
		if (null != handleNo) {
			STAFResult result = handle.submit2(browser.getHost(), "process",
					"stop handle " + handleNo);

			if (STAFResult.Ok == result.rc
					|| STAFResult.ProcessAlreadyComplete == result.rc) {
				free(browser);
			}
			getException(result);
		}
	}

	public void free(StaticBrowser browser) throws TestException {
		String handleNo = getHandle(browser);
		STAFResult result = handle.submit2(browser.getHost(), "process",
				"free handle " + handleNo);
		getException(result);
	}

	public void stopAll(String host) throws TestException {
		STAFResult result = handle
				.submit2(host, "process", "stop all confirm ");
		if (STAFResult.Ok == result.rc) {
			result = handle.submit2(host, "process", " free all");
		} else {
			getException(result);
		}
	}

	@SuppressWarnings("unchecked")
	public String getHandle(StaticBrowser browser) throws TestException {
		STAFResult result = handle
				.submit2(browser.getHost(), "process", "list");
		getException(result);
		LinkedList<Map<String, String>> list = (LinkedList<Map<String, String>>) result.resultObj;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);

			if (map.get("command").contains(browser.getPath())) {
				logger.debug("getHandle handle no = " + map.get("handle"));
				return map.get("handle");
			}
		}
		return null;

	}

	public void restart(StaticBrowser browser, TargetURL url)
			throws TestException {
		try {
			stop(browser);
		} catch (TestException e) {

		} finally {
			invoke(browser, url);
		}
	}

	public void restart(StaticBrowser browser) throws TestException {
		this.restart(browser, TargetURL.getURL(browser));
	}

	private void getException(STAFResult result) throws TestException {
		if (STAFResult.Ok != result.rc) {
			logger.error("Error execute staf command, RC: " + result.rc + "\t"
					+ result.resultContext);
			throw new TestException("Error execute staf command, RC: "
					+ result.rc + "\t" + result.resultContext);
		}
	}

	private String ampersandConverse(String s) {
		return s.replace("&", "^&");
	}
}
