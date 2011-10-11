package com.baidu.wamole.process;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.wamole.model.JsProject;
import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.JsKiss;
import com.baidu.wamole.template.ConfigurationFactory;
import com.baidu.wamole.util.FileUtil;
import com.caucho.config.ConfigException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TangramProcessor implements Processor<JsKiss> {

	// protected String depend;
	protected static final String REGEX = "///import\\s([^;]+);";
	// protected Project dependProject;
	protected Project project;
	protected List<String> list;// 给出排序后的
	protected List<String> matched;// 已经匹配过的

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baidu.wamole.process.Processor#process(com.baidu.wamole.model.Kiss)
	 */
	@Override
	public String process(JsKiss kiss) throws ConfigException {
		init();
		processResource(kiss);
		return loadTemplate(kiss);
	}

	private void init() {
		list = new ArrayList<String>();
		matched = new ArrayList<String>();
	}

	private String loadTemplate(JsKiss kiss) {
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = null;
		try {
			template = ConfigurationFactory.getInstance()
					.getTemplate("run.ftl");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		map.put("kiss", kiss);
		map.put("resources", list);
		Writer writer = new StringWriter();
		try {
			template.process(map, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	protected void processResource(JsKiss kiss) throws ConfigException {
		this.project = kiss.getProject();
		list.add("/resource/UserAction.js");
		this.getDepends(project.getRootDir()
				+ kiss.getName().replace("test", "src"));
		this.list.add(getSrcPath(kiss.getName()));
		this.list.add(getCasePath(kiss.getName()));
	}

	/**
	 * 转化源码路径
	 * 
	 * @param kiss
	 * @return
	 */
	protected String getSrcPath(String kiss) {
		return "/project/" + project.getName() + "/view"
				+ kiss.replace("test", "src");
	}

	/**
	 * 转化用例路径
	 * 
	 * @param kiss
	 * @return
	 */
	protected String getCasePath(String kiss) {
		return "/project/" + project.getName() + "/view" + kiss;
	}

	/**
	 * 获取用例执行依赖资源
	 * 
	 * @param path
	 * @return
	 * @throws ConfigException 
	 */
	protected void getDepends(String path) throws ConfigException {
		matched.add(path);
		Matcher matcher = Pattern.compile(REGEX).matcher(
				FileUtil.readFile(path));
		String relativePath = null;
		while (matcher.find()) {
			String name = matcher.group(1);
			path = project.getRootDir() + "/src/" + name.replace(".", "/")
					+ ".js";
			relativePath = "/project/" + project.getName() + "/view"
					+ path.substring(project.getRootDir().length());
			if (!list.contains(relativePath)) {
				if (!matched.contains(path)) {
					getDepends(path);
					list.add(relativePath);
				}
			}
		}
	}
}
