package com.baidu.wamole.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.model.Kiss;
import com.baidu.wamole.template.ConfigurationFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ResultTableImpl implements ResultTable {
	Logger logger = LoggerFactory.getLogger(ResultTableImpl.class);
	protected Result[][] results; // 结果
	protected List<String> browserList; // 浏览器列表
	protected List<String> activeList;// 活动的浏览器列表
	protected List<Kiss> kissList; // 用例列表
	protected List<String> kissNameList; //
	protected DeadLine deadLine; // 超时时间
	protected int[] currentIndex; // 当前执行index
	protected int[] successCount;

	public ResultTableImpl(List<String> browserList, List<String> activeList,
			List<Kiss> kissList, int interval) {
		logger.info(browserList.toString());
		logger.info(activeList.toString());
		this.browserList = browserList;
		this.kissList = kissList;
		converseKissName();
		results = new Result[browserList.size()][kissList.size()];
		currentIndex = new int[browserList.size()];
		successCount = new int[browserList.size()];
		for (String browser : browserList) {
			// 如果注册浏览器当前并无可用浏览器，则将该浏览器count直接置为最高。
			if (!activeList.contains(browser)) {
				successCount[browserList.indexOf(browser)] = kissList.size() + 1;
			}
		}
		deadLine = new DeadLine(interval, kissList.size());
		decrease();
	}

	private void converseKissName() {
		kissNameList = new ArrayList<String>();
		for (int i = 0; i < kissList.size(); i++) {
			kissNameList.add(kissList.get(i).getName());
		}
	}

	@Override
	public synchronized Kiss store(Result result) {
		int browserIndex = getBrowserIndex(result.getBrowser());
		int kissIndex = getKissIndex(result.getName());
		results[browserIndex][kissIndex] = result;
		++successCount[browserIndex];
		decrease();
		return getNextExcutableKiss(result.getBrowser());
	}

	/**
	 * 对browser当前result添加一个空的结果
	 * 
	 * @param browser
	 */
	private synchronized void storeEmpty(String browser, int index) {
		Result result = new Result();
		result.setBrowser(browser);
		result.setFail(0);
		result.setTotal(0);
		result.setTimeStamp(0);
		result.setName(kissList.get(index).getName());
		logger.debug("store empty case : " + result.getName());
		int browserIndex = getBrowserIndex(result.getBrowser());
		int kissIndex = getKissIndex(result.getName());
		results[browserIndex][kissIndex] = result;
		++successCount[browserIndex];
		decrease();
	}

	/**
	 * 获取各个浏览器成功执行了多少Case
	 * 
	 * @return
	 */
	private int getLeastCount() {
		int least = successCount[0];
		for (int i = 0; i < successCount.length; i++) {
			if (successCount[i] < least) {
				least = successCount[i];
			}
		}
		System.out.println(least);
		return least;
	}

	private void decrease() {
		System.out.println("decrease count : " + getLeastCount());
		deadLine.decrease(getLeastCount());
	}

	@Override
	public int getBrowserIndex(String browser) {
		if (browserList.contains(browser)) {
			return browserList.lastIndexOf(browser);
		} else
			return -1;
	}

	@Override
	public int getKissIndex(String kissName) {
		if (kissNameList.contains(kissName)) {
			return kissNameList.lastIndexOf(kissName);
		} else
			return -1;
	}

	@Override
	public Result getResult(int browserIndex, int kissIndex) {
		return results[browserIndex][kissIndex];
	}

	// @Override
	public synchronized Kiss getNextExcutableKiss(String browser) {
		int index = browserList.lastIndexOf(browser);
		int current = currentIndex[index];
		Kiss kiss = null;
		// 如果kiss列表中还有可
		if (current < kissList.size()) {
			kiss = kissList.get(current);
		}
		currentIndex[index] = ++current;
		logger.debug("browser : " + browser + ",next case:" + kiss);
		return kiss;
	}

	@Override
	public boolean isDead() {
		return deadLine.isDead();
	}

	public synchronized void save() throws IOException {
		Template template = ConfigurationFactory.getInstance().getTemplate(
				"report_xml.ftl");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("browsers", browserList);
		map.put("kisses", kissList);
		map.put("results", results);
		String root = System.getProperty("user.home") + "/.wamole";
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String filePath = root + "/result/Report_" + dateformat1.format(new Date()) + ".xml";
		File file = new File(filePath);
		// 建立文件
		if (!file.exists()) {
			// 建立文件夹
			if (filePath.lastIndexOf("/") > 0) {
				filePath = filePath.substring(0, filePath.lastIndexOf("/"));
				File dir = new File(filePath);
				dir.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			template.process(map, writer);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
