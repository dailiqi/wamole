package com.baidu.wamole.config;

import com.baidu.wamole.exception.ConfigException;

public class TangramConfig<Item> extends AbstractConfig {


//	private int step; // 浏览器通知步长
//
//	private boolean autorun;// 浏览器监控是否开启

	public TangramConfig() {
	}

	@Override
	public void config() throws ConfigException {
//		// 根据浏览器配置，启动浏览器，并
//		if (autorun) {
//			new Thread("Tangram config thread") {
//				@Override
//				public void run() {
//					// 挨个启动浏览器
//					for (StaticBrowser browser : browsers) {
//						try {
//							browser.doStart();
//						} catch (TestException e) {
//							e.printStackTrace();
//						}
//					}
//					// 休息
//					try {
//						Thread.sleep(step * 1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					new TimeOutThread(browsers.getView(), step).start();
//				}
//			}.start();
//		}
	}

//	public int getStep() {
//		return step;
//	}
//
//	public CopyOnWriteList<StaticBrowser> getBrowsers() {
//		return browsers;
//	}
}
