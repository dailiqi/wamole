package com.baidu.wamole.model;


public class DefaultKiss implements Kiss {

	protected String name;

	protected Project project;

	public DefaultKiss(Project project, String name) {
		this.name = name;
		this.project = project;
	}

	@Override
	public String getName() {
		return name;
	}

	public Project getProject() {
		return this.project;
	}
}