package com.baidu.wamole.task;

import com.baidu.wamole.model.Project;

public class Task {
	private int id;
	private Project<?, ?> project;

	public Task(Project<?, ?> project) {
		this.project = project;
	}

	public boolean perform() {
		project.getBuild().build();
		return false;
	}

	public int getId() {
		return id;
	}
}
