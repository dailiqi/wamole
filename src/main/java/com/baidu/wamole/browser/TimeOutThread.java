package com.baidu.wamole.browser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.exception.TestException;

/**
 * 超时校验
 * 
 * @author dailiqi
 */
public class TimeOutThread extends Thread {
	Logger logger = LoggerFactory.getLogger(TimeOutThread.class);
	private List<StaticBrowser> list;
	private int step = 10000;
	public TimeOutThread(List<StaticBrowser> list,int step) {
		this.list = list;
		this.step = step * 1000;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(step * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (list) {
				for (int i = 0; i < list.size(); i++) {
					StaticBrowser sb = list.get(i);
					// 当超过10次重启，则不再进行重启操作，直接返回
					if(sb.faultCount > 10) {
						return;
					}
					long now = System.currentTimeMillis();
					long last = sb.getBrowser().getLastNoticeTime();
					if(now - last > step ) {
						try {
							sb.doStop();
							sb.doStart();
						} catch (TestException e) {
							e.printStackTrace();
						}
						
						sb.faultCount ++ ;
					}
				}
			}
		}
	}
}
