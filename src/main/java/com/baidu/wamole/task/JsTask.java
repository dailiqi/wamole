package com.baidu.wamole.task;

import java.util.List;

import com.baidu.wamole.model.JsProject;

public class JsTask extends Task{
	private JsProject project;
	public JsTask(JsProject project) {
		super(project);
		this.project = project;
	}
	public ResultTable getResult() {
		List<BuildStep> list = project.getBuildSteps();
		for (BuildStep buildStep : list) {
			if (buildStep instanceof JsBuildStep) {
				return ((JsBuildStep) buildStep).getResultTable();
			}
		}
		return null;
	}
}
