package com.baidu.wamole.model;


public class TangramKiss implements Kiss {

	private String name;

	private Project project;

	public TangramKiss(Project project, String name) {
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