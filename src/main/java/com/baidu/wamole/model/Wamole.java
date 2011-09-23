package com.baidu.wamole.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.baidu.wamole.browser.BrowserManager;
import com.baidu.wamole.config.Config;
import com.baidu.wamole.config.Config.NOOBConfig;
import com.baidu.wamole.xml.CopyOnWriteList;
import com.baidu.wamole.xml.DefaultXStream;
import com.baidu.wamole.xml.XmlFile;
import com.thoughtworks.xstream.XStream;

public class Wamole {
	private Config config = new NOOBConfig();
	private CopyOnWriteList<Project> projects = new CopyOnWriteList<Project>();
	private transient final File root;
	private static Wamole instance;
	private CopyOnWriteList<Module> modules = new CopyOnWriteList<Module>();

	public CopyOnWriteList<Project> getProjectList() {
		return projects;
	}

	public Wamole(File root) {
		this.root = root;
		this.load();
		instance = this;
	}

	public List<Module> getModules() {
		return modules.getView();
	}

	public Module getModule(Class<? extends Module> clazz) {
		List<Module> tmp = modules.getView();
		for (int i = 0; i < tmp.size(); i++) {
			if(clazz.isInstance(tmp.get(i))) {
				return clazz.cast(tmp.get(i));
			}
		}
		return null;
	}

	public Config getConfig() {
		return this.config;
	}

	public void load() {
		XmlFile file = this.getConfigFile();
		try {
			file.unmarshal(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private XmlFile getConfigFile() {
		return new XmlFile(XSTREAM, new File(root, "config.xml"));
	}

	private static final XStream XSTREAM = new DefaultXStream();
	{
		// 定义XML中别名
		XSTREAM.alias("wamole", Wamole.class);
		XSTREAM.alias("bm", BrowserManager.class);
	}

	public static Wamole getInstance() {
		return instance;
	}

	public Project getProject(String name) {
		List<Project> list = projects.getView();
		for (Project project : list) {
			if (project.getName().equals(name)) {
				return project;
			}
		}
		return null;
	}
}
