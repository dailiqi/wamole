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
public class FrameResource {
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
	public Response importCase(@PathParam("path") String path) {
		path = "/" + path;
		String uri = uriInfo.getPath();
		String project = uri.substring("project/".length(),
				uri.indexOf("/frame/"));
		Project<?, ?> instance = Wamole.getInstance().getProject(project);
		if (path.endsWith(".js"))
			try {
				String s = instance.getExecutePage(path);
				String testimport = s.substring(s.lastIndexOf("<script"), s.lastIndexOf("</script>") +"</script>".length());
				s = s.replace(testimport, "");
//				System.out.println(s);
				return Response.ok(s).build();
			} catch (TestException e) {
				e.printStackTrace();
				return Response.ok(e.getMessage() + "path：" + path).build();
			}
		else
			return Response.status(404).build();
	}
}
