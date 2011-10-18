package com.baidu.wamole.browser;

import java.util.List;

import com.baidu.wamole.exception.TestException;
import com.baidu.wamole.model.Module;
import com.baidu.wamole.model.JsKiss;
import com.baidu.wamole.task.BuildStep;
import com.baidu.wamole.task.JsBuildStep;
import com.baidu.wamole.task.Result;
import com.baidu.wamole.xml.CopyOnWriteList;

public class BrowserManager implements Module {
	private CopyOnWriteList<StaticBrowser> statics;
	private CopyOnWriteList<Browser> browsers;
	private JsBuildStep buildStep;

	/* package */BrowserManager() {
	}

	private int step = 20;

	private boolean autorun;

	public List<StaticBrowser> getStaticBrowsers() {
		return statics.getView();
	}

	public JsKiss notice(String id, Result result) throws TestException {
		getBrowser(id).notice();
		// 当务任务时
		if (null == buildStep) {
			return null;
			//当有任务时
		} else {
			// 当result有结果
			if (null != result && null != result.getName()) {
				return (JsKiss) buildStep.getResultTable().store(result);
				// result无结果，当前任务中需要该浏览器进行测试
			} else if (buildStep.getResultTable().getBrowserIndex(
					result.getBrowser()) > 0) {
				return (JsKiss) buildStep.getResultTable()
						.getNextExcutableKiss(result.getBrowser());
			} else {
				return null;
			}
		}
		// else {
		// buildStep.getResultTable().store(null);
		// }
		// if (null != result.getName()) {
		// return (TangramKiss) buildStep.getResultTable().store(result);
		// } else {
		// return null;
		// }
	}

	public Browser getBrowser(String id) {
		List<Browser> brs = getBrowsers();
		for (Browser br : brs) {
			if (br.getId().equals(id))
				return br;
		}
		return null;
	}

	public void removeBrowser(String id) {
		List<Browser> brs = getBrowsers();
		for (Browser br : brs) {
			if (br.getId().equals(id))
				browsers.remove(br);
		}
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

	public BuildStep getBuildStep() {
		return buildStep;
	}

	public void setBuildStep(JsBuildStep buildStep) {
		this.buildStep = buildStep;
	}
}
