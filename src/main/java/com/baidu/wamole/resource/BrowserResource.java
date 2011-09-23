package com.baidu.wamole.resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.baidu.wamole.browser.Browser;
import com.baidu.wamole.browser.BrowserManager;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.template.ConfigurationFactory;
import com.sun.jersey.api.core.ResourceContext;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 浏览器资源
 * 
 * @author dailiqi
 */
@Produces("text/html;charset=UTF-8")
public class BrowserResource {
	@Context
	UriInfo uriInfo;
	@Context
	ResourceContext context;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/register")
	public Response getPage() {
		StringWriter writer = new StringWriter();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"browser/register.ftl");
			Map<String, Object> map = new HashMap<String, Object>();
			template.process(map, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return Response.ok(writer.toString()).build();
	}

	/**
	 * 当返回 false 则表示已注过，否则注册
	 * 
	 * @param param
	 * @return
	 */
	@POST
	@Path("/register")
	public Response register(MultivaluedMap<String, String> param) {
		String ip = request.getRemoteHost();
		String userAgent = param.getFirst("userAgent");
		// 新建一个instance
		Browser instance = Browser.build(ip, userAgent).build();

		// 获取已存在的 list 进行对比
		BrowserManager bm = (BrowserManager) Wamole.getInstance().getModule(
				BrowserManager.class);
		for (Browser browser : bm.getBrowsers()) {
			if (browser.isEqual(instance)) {
				return Response.ok("false").build();
			}
		}
		bm.addBrowser(instance);
		return Response.ok(instance.getId()).build();
	}

	@GET
	public Response list() {
		BrowserManager bm = (BrowserManager) Wamole.getInstance().getModule(
				BrowserManager.class);
		List<Browser> list = bm.getBrowsers();
		StringWriter writer = new StringWriter();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"browser/list.ftl");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("browsers", list);
			template.process(map, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return Response.ok(writer.toString()).build();
	}

	@GET
	@Path("/capture/{ids}")
	public Response capture() {
		StringWriter writer = new StringWriter();
		try {
			Template template = ConfigurationFactory.getInstance().getTemplate(
					"browser/capture.ftl");
			try {
				template.process(null, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(writer.toString()).build();
	}
}
