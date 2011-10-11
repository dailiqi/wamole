package com.baidu.wamole.resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
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

@Produces("text/html;charset=UTF-8")
public class ViewResource {
	@Context
	UriInfo uriInfo;
	@Context
	ResourceContext context;
	
	@GET
	public Response get() {
		StringWriter writer = new StringWriter();
		List<Project<?, ?>> list = Wamole.getInstance().getProjectList().getView();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"project/list.ftl");
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
}
