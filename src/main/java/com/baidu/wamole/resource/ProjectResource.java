package com.baidu.wamole.resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.template.ConfigurationFactory;
import com.sun.jersey.api.core.ResourceContext;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 项目信息资源
 * 
 * @author dailiqi
 */
@Produces("text/html;charset=UTF-8")
public class ProjectResource {
	private Project<?, ?> project;
	@Context
	UriInfo uriInfo;
	@Context
	ResourceContext context;

	public void setName(String name) {
		List<Project<?, ?>> list = Wamole.getInstance().getProjectList().getView();
		for (Project<?, ?> project : list) {
			if (project.getName().equals(name)) {
				this.project = project;
			}
		}
	}

	@GET
	public Response get() {
		if (null == project) {
			return Response.noContent().build();
		}
		StringWriter writer = new StringWriter();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"project/project.ftl");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("project", project);
			template.process(map, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return Response.ok(writer.getBuffer().toString()).build();
	}

	@Path("/exec")
	public ExecuteResource executeCase() {
		return context.getResource(ExecuteResource.class);
	}
	@Path("/frame")
	public FrameResource importCase() {
		return context.getResource(FrameResource.class);
	}
	@GET
	@Path("/detail")
	public Response getDetail() {
		return Response.ok("detail").build();
	}
	@GET
	@Path("/build")
	public Response build() {
		Wamole.getInstance().addBuild(project.getBuild());
		return Response.ok("").build();
	}
}
