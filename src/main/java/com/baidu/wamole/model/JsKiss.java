package com.baidu.wamole.model;


public class JsKiss extends DefaultKiss{
	
	public JsKiss(JsProject project, String name) {
		super(project, name);
	}

	public String getExecUrl() {
		return "/project/" + project.getName() + "/exec" + name;
	}
}