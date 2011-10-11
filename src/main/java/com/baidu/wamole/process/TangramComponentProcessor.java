package com.baidu.wamole.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.wamole.model.Project;
import com.baidu.wamole.model.JsKiss;
import com.baidu.wamole.model.Wamole;
import com.baidu.wamole.util.FileUtil;
import com.caucho.config.ConfigException;

/**
 * 针对Tangram-component特定处理
 * 
 * @author dailiqi
 */
public class TangramComponentProcessor extends TangramProcessor {

	private String depend;
	private Project dependProject;

	@Override
	public void processResource(JsKiss kiss) throws ConfigException {
		dependProject = Wamole.getInstance().getProject(depend);
		this.project = kiss.getProject();
		// Component类型特有加入 UserAction及Tools
//		this.list.add("/project/" + project.getName()
//				+ "/view/test/tools/br/js/UserAction.js");
		list.add("/resource/UserAction.js");
		this.list.add("/project/" + project.getName()
				+ "/view/test/tools/br/js/tools.js");
		list.add("/resource/UserAction.js");
		this.getDepends(project.getRootDir()
				+ kiss.getName().replace("test", "src"));
		this.list.add(getSrcPath(kiss.getName()));

		// 添加对特定case的tools.js资源加入
		String path = this.project.getRootDir() + kiss.getName();
		path = path.replace(".js", "/tools.js");
		if (FileUtil.existsFile(path)) {
			this.list.add(getCasePath(kiss.getName()).replace(".js",
					"/tools.js"));
		}
		this.list.add(getCasePath(kiss.getName()));
	}

	protected void getDepends(String path) throws ConfigException {
		matched.add(path);
		if (null != depend)
			this.dependProject = Wamole.getInstance().getProject(depend);
		Matcher matcher = Pattern.compile(REGEX).matcher(
				FileUtil.readFile(path));
		String relativePath = null;
		while (matcher.find()) {
			String name = matcher.group(1);
			path = project.getRootDir() + "/src/" + name.replace(".", "/")
					+ ".js";
			// 有项目依赖的解决方式
			if (!FileUtil.existsFile(path)) {
				path = path.replace(project.getRootDir(),
						dependProject.getRootDir());
				if (!FileUtil.existsFile(path)) {
					throw new ConfigException(
							"Tangram project parse error: can't find depend resource : "
									+ name);
				}
				relativePath = "/project/" + dependProject.getName()
						+ "/view"
						+ path.substring(dependProject.getRootDir().length());
			} else {
				relativePath = "/project/" + project.getName() + "/view"
						+ path.substring(project.getRootDir().length());
			}

			if (!list.contains(relativePath)) {
				if (!matched.contains(path)) {
					getDepends(path);
					list.add(relativePath);
				}
			}
		}
	}
}
