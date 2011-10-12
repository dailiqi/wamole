package com.baidu.wamole.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.baidu.wamole.util.AntPathMatcher;

public class AntPathParser implements Parser<DefaultKiss, Project<?, ?>> {
	private String casepattern;
	private String filepath;
	private AntPathMatcher matcher;
	private Map<String, DefaultKiss> kisses;

	@Override
	public Map<String, DefaultKiss> parse(Project<?, ?> project) {
		kisses = new HashMap<String, DefaultKiss>();
		String filepath = project.getRootDir();
		parse(project, new File(filepath));
		return null;
	}

	/**
	 * 递归解析项目资源
	 * 
	 * @param file
	 */
	private void parse(Project<?, ?> project, File file) {
		File[] files = file.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				this.parse(project, file2);
			} else {
				String absolutePath = file2.getAbsolutePath();
				String relativePath = absolutePath.substring(new File(filepath)
						.getAbsolutePath().length());
				relativePath = relativePath.replace("\\", "/");
				if (matcher.match(casepattern, relativePath)) {
					kisses.put(relativePath, new DefaultKiss(project,
							relativePath));
				}
			}

		}
	}
}
