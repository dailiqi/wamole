package com.baidu.wamole.model;

public abstract class AbstractProject implements Project {

	// 项目名称
	private String name;
	// 项目基础路径
	private String rootDir;

	@Override
	public String getName() {
		return this.name;
	}

	public String getRootDir() {
		return this.rootDir;
	}
}
