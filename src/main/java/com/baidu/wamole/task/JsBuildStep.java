package com.baidu.wamole.task;

import java.util.ArrayList;
import java.util.List;

import com.baidu.wamole.browser.Browser;
import com.baidu.wamole.browser.BrowserManager;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.xml.CopyOnWriteList;

public class JsBuildStep extends BuildStep {
	private CopyOnWriteList<String> browsers;
	private List<Browser> actives;
	private ResultTable resultTable;
	private BrowserManager bm;
	private List<String> browserList;

	@Override
	public boolean preBuild(AbstractBuild<?, ?> build) {
		bm = (BrowserManager) Wamole.getInstance().getModule(
				BrowserManager.class);
		actives = config(bm.getBrowsers());
		browserList = new ArrayList<String>();
		for (String string : browsers.getView()) {
			browserList.add(string.toLowerCase());
		}
		resultTable = new ResultTableImpl(browserList, build.getProject()
				.getKisses(), bm.getStep());
		return actives.size() > 0;
	}

	public ResultTable getResultTable() {
		return this.resultTable;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build) {
		bm.setBuildStep(this);
		while (!resultTable.isDead()) {
		}
		bm.setBuildStep(null);
		return true;
	}

	/**
	 * 根据配置信息配置，检测当前active的浏览器，将浏览器遍历出来
	 * 
	 * @param list
	 * @return
	 */
	private List<Browser> config(List<Browser> list) {
		List<Browser> result = new ArrayList<Browser>();
		for (Browser browser : list) {
			if (browserList.contains(browser.getName().toLowerCase())) {
				result.add(browser);
			}
		}
		return result;
	}
}
