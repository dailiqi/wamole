package com.baidu.wamole.server;

import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import com.baidu.wamole.model.Project;
import com.caucho.quercus.servlet.QuercusServlet;

public class ProjectHandlerWrapper implements HandlerWrapper {
	HandlerCollection handlers = new HandlerCollection();
	private List<Project> list;

	
	public ProjectHandlerWrapper(List<Project> list) {
		this.list = list;
	}

	@Override
	public Handler getHandler() {
		build();
		return handlers;
	}

	private void build() {
		for (Project project : list) {
			WebAppContext context = new WebAppContext(project.getRootDir(), "/project/"
					+ project.getName() + "/view");
			// 设定resource可在server启动后改变
			context.setDefaultsDescriptor(Thread.currentThread().getClass()
					.getResource("/resource/jetty/webdefault.xml").toString());
			// 增加对PHP的支持
			ServletHolder holder = new ServletHolder(QuercusServlet.class);
//			holder.setInitOrder(0);
			context.addServlet(holder, "*.php");
			handlers.addHandler(context);
		}
	}

	@Override
	public int getPriority() {
		return 4;
	}
}
