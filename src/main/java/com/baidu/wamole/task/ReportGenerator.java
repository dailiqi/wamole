package com.baidu.wamole.task;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.wamole.model.Kiss;
import com.baidu.wamole.template.ConfigurationFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ReportGenerator {
	protected Configuration configuration;

	public ReportGenerator() {
	}

	public void generate(Writer writer, List<String> browserList,
			List<Kiss> kissList, Result[][] results) {
		Template template = null;
		try {
			template = ConfigurationFactory.getInstance()
			.getTemplate("report_xml.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("browsers", browserList);
		map.put("kisses", kissList);
		map.put("results", results);
		try {
			template.process(map, writer);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
