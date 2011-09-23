package com.baidu.wamole.server;

import org.eclipse.jetty.server.Handler;


public interface HandlerWrapper{

	// 获取Handler
	public Handler getHandler();
	// 获取优先级 0最高 9最小
	public int getPriority();
}
