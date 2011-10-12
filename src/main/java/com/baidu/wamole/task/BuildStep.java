package com.baidu.wamole.task;

/**
 * 项目构建器
 * 
 * @author dailiqi
 */
public abstract class BuildStep {//implements Runnable {

	public boolean preBuild(AbstractBuild<?, ?> build) {
		return true;
	}

	public boolean perform(AbstractBuild<?, ?> build) {
		return false;
	}
}
