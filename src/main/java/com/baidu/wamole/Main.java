package com.baidu.wamole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.jetty.server.handler.HandlerCollection;

import com.baidu.wamole.exception.ConfigException;
import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.server.CommonResouceHandlerWrapper;
import com.baidu.wamole.server.JettyServer;
import com.baidu.wamole.server.ProjectHandlerWrapper;
import com.baidu.wamole.server.RestfulHandlerWrapper;

public class Main {
	private List<Project> projects;
	private final String root;

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.loadConfig();
		main.startServer();
	}

	public void loadConfig() {
		System.out.println("root" + root);
		File file = new File(root);
		if (!file.exists() || !new File(root + "/config.xml").exists()) {
			initWamole();
		}
		
		Wamole wamole = new Wamole(file);
		projects = wamole.getProjectList().getView();
		try {
			wamole.getConfig().config();
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

	private void initWamole() {
		File file = new File(root);
		file.mkdirs();
		File configFile = new File(root + "/config.xml");
		try {
			FileOutputStream w = new FileOutputStream(configFile);
			// 当第一次建立时，建立一个config.xml
			w.write("<?xml version='1.0' encoding='UTF-8'?>".getBytes());
			w.write("<wamole></wamole>".getBytes());
			w.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() throws Exception {
		JettyServer.setPort(8080);
		HandlerCollection collection = new HandlerCollection();
		collection.addHandler(new CommonResouceHandlerWrapper().getHandler());
		if(null != projects)
			collection.addHandler(new ProjectHandlerWrapper(projects).getHandler());
		collection.addHandler(new RestfulHandlerWrapper().getHandler());
		JettyServer.setHandler(collection);
		JettyServer.start();
	}

	{
		root = System.getProperty("user.home") + "/.wamole";
	}
}
