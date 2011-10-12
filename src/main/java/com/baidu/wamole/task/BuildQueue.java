package com.baidu.wamole.task;

import java.util.Queue;

import com.baidu.wamole.model.Wamole;

public class BuildQueue {
	private Queue<Build<?, ?>> queue;
	private Build<?, ?> current;

	public void addBuild(Build<?, ?> build) {
		this.queue.add(build);
		if (current == null) {
			this.current = build;
		}
	}

	public Build<?, ?> getCurrent() {
		return this.current;
	}

}
