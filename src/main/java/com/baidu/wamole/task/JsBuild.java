package com.baidu.wamole.task;

import java.io.IOException;

import com.baidu.wamole.model.JsProject;

public class JsBuild extends
		AbstractBuild<JsBuild, JsProject> {
	public JsBuild(JsProject project) throws IOException {
		super(project);
	}
}
