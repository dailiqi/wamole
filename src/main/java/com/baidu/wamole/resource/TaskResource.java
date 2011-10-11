package com.baidu.wamole.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.task.Task;

@Produces("text/html;charset=UTF-8")
public class TaskResource {

	@GET
	public Response getTaskList() {
		return Response.ok("").build();
	}
	
	@GET
	@Path("/==new==/{project}")
	public Response addTask(@PathParam("project") String name) {
		Project<?, ?> project = Wamole.getInstance().getProject(name);
		Task task = new Task(project);
		System.out.println("add");
		return Response.ok("").build();
	}
	
	@GET
	@Path("{id}")
	public Response getTask(int id) {
		return Response.ok("").build();
	}
}
