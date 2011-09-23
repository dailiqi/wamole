package com.baidu.wamole.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.baidu.wamole.exception.TestException;
import com.baidu.wamole.process.Processor;
import com.baidu.wamole.util.AntPathMatcher;

public class TangramProject extends AbstractProject {

	private ConcurrentHashMap<String, Kiss> kisses;
	private String casepattern;
	private boolean inited;
	private Processor<Kiss> processor;
	private AntPathMatcher matcher;

	public TangramProject() {
	}

	/**
	 * 项目初始化
	 * @throws TestException
	 */
	private void init() throws TestException {
		if (!inited) {
			matcher = new AntPathMatcher();
			kisses = new ConcurrentHashMap<String, Kiss>();
			File root = new File(this.getRootDir());
			if (root.isDirectory()) {
				parse(root);
			} else {
				throw new TestException("tangram project init fail!");
			}
		}

		inited = true;
	}

	/**
	 * 递归解析项目资源
	 * @param file
	 */
	private void parse(File file) {
		File[] files = file.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				this.parse(file2);
			} else {
				String absolutePath = file2.getAbsolutePath();
				String relativePath = absolutePath.substring(new File(this
						.getRootDir()).getAbsolutePath().length());
				relativePath = relativePath.replace("\\", "/");
				System.out.println("casePattern: " + casepattern + "; relativePath:" + relativePath + " ; result" + matcher.match(casepattern, relativePath));
				if (matcher.match(casepattern, relativePath)) {
					kisses.put(relativePath,
							new TangramKiss(this, relativePath));
				}
			}

		}
	}

	@Override
	public Kiss getKiss(String kissName) {
		if (!inited) {
			try {
				init();
			} catch (TestException e) {
				e.printStackTrace();
			}
		}
		return (Kiss) kisses.get(kissName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kiss> getKisses() {
		if (!inited) {
			try {
				init();
			} catch (TestException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<Kiss>(
				(Collection<? extends Kiss>) kisses.entrySet());
	}

	@Override
	public String getExecutePage(String searchString) throws TestException {
		Kiss kiss = this.getKiss(searchString);
		try{
			String s = processor.process(kiss);
			return s;
		} catch (Exception e) {
			throw new TestException("请检查配置信息是否正确，可能由于资源无法匹配导致.");
		}
	}
}
