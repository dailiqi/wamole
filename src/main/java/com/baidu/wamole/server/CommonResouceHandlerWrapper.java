package com.baidu.wamole.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.webapp.WebAppContext;

public class CommonResouceHandlerWrapper implements HandlerWrapper {
	@Override
	public Handler getHandler() {
		WebAppContext handler = new WebAppContext(Thread.currentThread()
				.getClass().getResource("/resource/").toString(), "/resource");
		handler.setDefaultsDescriptor(Thread.currentThread().getClass()
				.getResource("/resource/jetty/webdefault.xml").toString());
		return handler;
	}

	@Override
	public int getPriority() {
		return 4;
	}
}
