package com.baidu.wamole.model;

import java.util.List;

import com.baidu.wamole.task.AbstractBuild;
import com.baidu.wamole.task.BuildStep;
import com.baidu.wamole.xml.CopyOnWriteList;

public abstract class AbstractProject<P extends AbstractProject<P, B> ,B extends AbstractBuild<B, P>> implements Project<P ,B> {

	// 项目名称
	private String name;
	// 项目基础路径
	private String rootDir;
	
	//项目构建步骤
	private CopyOnWriteList<BuildStep> buildSteps;
	
	//项目
	@Override
	public String getName() {
		return this.name;
	}

	public String getRootDir() {
		return this.rootDir;
	}
	
	public List<BuildStep> getBuildSteps() {
		return buildSteps.getView();
	}
}
