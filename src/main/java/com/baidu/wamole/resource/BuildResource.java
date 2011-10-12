package com.baidu.wamole.resource;

import java.util.Queue;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.task.Build;

public class BuildResource {

	@GET
	public Response getBuildList() {
		Queue<Build<?, ?>> queue = Wamole.getInstance().getBuildQueue();
		
		return Response.ok(queue.toString()).build();
	}
	
}
