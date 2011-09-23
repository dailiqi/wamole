package com.baidu.wamole.browser;

import com.baidu.wamole.exception.TestException;

/**
 * 描述browser生命周期
 * 
 * @author dailiqi
 */
public interface LifeCycle {

	/**
	 * 启动
	 */
	public void doStart() throws TestException;

	/**
	 * 停止
	 */
	public void doStop() throws TestException;

	/**
	 * 是否启动
	 * 
	 * @return
	 */
	public boolean isStarted();
}
