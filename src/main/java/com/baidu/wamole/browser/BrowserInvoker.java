package com.baidu.wamole.browser;

import com.baidu.wamole.browser.StaticBrowser;
import com.baidu.wamole.exception.TestException;

/**
 * 浏览器唤醒
 * @author dailiqi
 */
public interface BrowserInvoker {
	public void invoke(StaticBrowser browser, TargetURL url) throws TestException;

	public void restart(StaticBrowser browser, TargetURL url) throws TestException;

	public void invoke(StaticBrowser browser) throws TestException;

	public void restart(StaticBrowser browser) throws TestException;

	public void stop(StaticBrowser browser) throws TestException;
}
