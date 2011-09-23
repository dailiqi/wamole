package com.baidu.wamole.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.baidu.wamole.exception.TestException;
import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.Wamole;

@Produces("text/html;charset=UTF-8")
public class ExecuteResource {
	@Context
	UriInfo uriInfo;

	/**
	 * <ul>
	 * <li>支持单用例执行
	 * <li>支持路径用例批量执行
	 * 
	 * @param hh
	 * @param path
	 * @param projectName
	 * @return
	 */
	@GET
	@Path("{path: .*}")
	public Response execCase(@PathParam("path") String path) {
		path = "/" + path;
		System.out.println("path:" + path);
		System.out.println(uriInfo.getPath());
		String uri = uriInfo.getPath();
		System.out.println(uriInfo.getRequestUri());
		String project = uri.substring("project/".length(),
				uri.indexOf("/exec/"));
		Project instance = Wamole.getInstance().getProject(project);
		if (path.endsWith(".js"))
			try {
				return Response.ok(instance.getExecutePage(path)).build();
			} catch (TestException e) {
				e.printStackTrace();
				return Response.ok(e.getMessage() + "path：" + path).build();
			}
		else
			return Response.status(404).build();
	}
}
