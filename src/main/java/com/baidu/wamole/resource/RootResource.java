package com.baidu.wamole.resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.template.ConfigurationFactory;
import com.sun.jersey.api.core.ResourceContext;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Path("/")
@Produces("text/html;charset=UTF-8")
public class RootResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	ResourceContext context;

	@GET
	public Response getView() {
		StringWriter writer = new StringWriter();
		List<Project<?, ?>> list = Wamole.getInstance().getProjectList()
				.getView();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"index.ftl");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projects", list);
			template.process(map, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return Response.ok(writer.getBuffer().toString()).build();
	}

	@Path("/project/{name}")
	public ProjectResource getProjectByName(@PathParam("name") String name) {
		ProjectResource resource = context.getResource(ProjectResource.class);
		resource.setName(name);
		return resource;
	}

	@Path("/task")
	public TaskResource getResourceByName() {
		return context.getResource(TaskResource.class);
	}

	@Path("/browser")
	public BrowserResource browser() {
		return context.getResource(BrowserResource.class);
	}

	@Path("/build")
	public BuildResource build() {
		return context.getResource(BuildResource.class);
	}
}
