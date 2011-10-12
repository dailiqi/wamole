package com.baidu.wamole.task;

import com.baidu.wamole.model.Project;

/**
 * 项目构建
 * @author dailiqi
 *
 * @param <B>
 * @param <P>
 */
public interface Build<B extends Build<B ,P>, P extends Project<P ,B>> {
	
	public P getProject();
	public void build();
	public boolean started();
	public boolean finished();
}
