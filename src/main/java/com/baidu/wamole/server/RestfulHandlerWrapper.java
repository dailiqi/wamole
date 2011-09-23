package com.baidu.wamole.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class RestfulHandlerWrapper implements HandlerWrapper {
	private ServletContextHandler handler = new ServletContextHandler();

	@Override
	public Handler getHandler() {
		ServletHolder holder = new ServletHolder(ServletContainer.class);
		holder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		holder.setInitParameter("com.sun.jersey.config.property.packages",
				"com.baidu.wamole.resource");
		handler.addServlet(holder, "/*");
		return handler;
	}

	@Override
	public int getPriority() {
		return 5;
	}
}
