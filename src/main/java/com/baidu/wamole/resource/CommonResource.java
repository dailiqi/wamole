package com.baidu.wamole.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/resource/{res: .*}")
public class CommonResource {
	@GET
	public String get(@PathParam("resource") String resourcePath) {
		System.out.println("resource path: " + resourcePath);
		return resourcePath;
	}
}
