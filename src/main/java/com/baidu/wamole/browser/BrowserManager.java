package com.baidu.wamole.browser;

import java.util.List;

import com.baidu.wamole.exception.TestException;
import com.baidu.wamole.model.Module;
import com.baidu.wamole.xml.CopyOnWriteList;

public class BrowserManager implements Module {
	private CopyOnWriteList<StaticBrowser> statics;
	private CopyOnWriteList<Browser> browsers;
	/*package */BrowserManager() {
	}
	private int step = 20;

	private boolean autorun;

	public List<StaticBrowser> getStaticBrowsers() {
		return statics.getView();
	}

	public List<Browser> getBrowsers() {
		return browsers.getView();
	}

	public boolean isStatic(Browser browser) {
		if (statics.size() > 0) {
			for (StaticBrowser sb : statics) {
				if (sb.getBrowser().isEqual(browser))
					return true;
			}
		}
		return false;
	}

	public void addBrowser(Browser browser) {
		browsers.add(browser);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isAutorun() {
		return autorun;
	}

	public void setAutorun(boolean autorun) {
		this.autorun = autorun;
	}

	public void invokeAll() {
		// 根据浏览器配置，启动浏览器，并
		if (autorun) {
			new Thread("Browser Mananger invoker static browser thread!") {
				@Override
				public void run() {
					List<StaticBrowser> statics = getStaticBrowsers();
					if (statics.size() > 0) {
						for (StaticBrowser staticBrowser : statics) {
							try {
								staticBrowser.doStart();
							} catch (TestException e) {
								e.printStackTrace();
							}
						}
					}
					try {
						Thread.sleep(step * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					new TimeOutThread(getStaticBrowsers(), step).start();
				}
			}.start();
		}
	}
}
