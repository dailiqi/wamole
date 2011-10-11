package com.baidu.wamole.model;

import java.util.HashMap;


public class ProjectFactory {
	static HashMap<String, Project<?, ?>> pool = new HashMap<String, Project<?, ?>>();
	public static Project<?, ?> getProject(String name){
		return pool.get(name);
	}
	
	public static void addProject(Project<?, ?> project){
		pool.put(project.getName(), project);
	}
}
