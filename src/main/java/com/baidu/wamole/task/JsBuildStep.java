package com.baidu.wamole.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.wamole.browser.Browser;
import com.baidu.wamole.browser.BrowserManager;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.template.ConfigurationFactory;
import com.baidu.wamole.xml.CopyOnWriteList;

public class JsBuildStep extends BuildStep {
	private CopyOnWriteList<String> browsers;
	private List<String> actives;
	private ResultTable resultTable;
	private BrowserManager bm;
	private List<String> browserList;

	@Override
	public boolean preBuild(AbstractBuild<?, ?> build) {
		bm = (BrowserManager) Wamole.getInstance().getModule(
				BrowserManager.class);
		browserList = new ArrayList<String>();
		for (String string : browsers.getView()) {
			browserList.add(string.toLowerCase());
		}
		actives = config(bm.getBrowsers());
		resultTable = new ResultTableImpl(browserList, actives, build
				.getProject().getKisses(), bm.getStep());
		return actives.size() > 0;
	}

	public ResultTable getResultTable() {
		return this.resultTable;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build) {
		bm.setBuildStep(this);
		while (!resultTable.isDead()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			this.resultTable.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bm.setBuildStep(null);
		System.out.println("result table :" + this.resultTable.toString());
		return true;
	}

	/**
	 * 根据配置信息配置，检测当前active的浏览器，将浏览器遍历出来
	 * 
	 * @param list
	 * @return
	 */
	private List<String> config(List<Browser> list) {
		List<String> result = new ArrayList<String>();
		for (Browser browser : list) {
			if (browserList.contains(browser.getName().toLowerCase())) {
				if (browser.getName().toLowerCase().equals("msie")) {
					result.add(browser.getName() + browser.getVersion());
				} else {
					result.add(browser.getName());
				}
			}
		}
		return result;
	}
}
