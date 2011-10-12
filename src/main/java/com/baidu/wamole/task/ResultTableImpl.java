package com.baidu.wamole.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.wamole.model.Kiss;

public class ResultTableImpl implements ResultTable {
	Logger logger = LoggerFactory.getLogger(ResultTableImpl.class);
	protected Result[][] results; // 结果
	protected List<String> browserList; // 浏览器列表
	protected List<Kiss> kissList; // 用例列表
	protected List<String> kissNameList; //
	protected DeadLine deadLine; // 超时时间
	protected int[] currentIndex; // 当前执行index
	protected int[] successCount;

	public ResultTableImpl(List<String> browserList,
			List<Kiss> kissList, int interval) {
		this.browserList = browserList;
		this.kissList = kissList;
		converseKissName();
		results = new Result[browserList.size()][kissList.size()];
		currentIndex = new int[browserList.size()];
		successCount = new int[browserList.size()];
		deadLine = new DeadLine(interval,kissList.size());
		
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
	 * @param browser
	 */
	private synchronized void storeEmpty(String browser,int index){
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
	 * @return
	 */
	private int getLeastCount() {
		int least = successCount[0];
		for (int i = 0; i < successCount.length; i++) {
			if (successCount[i] < least) {
				least = successCount[i];
			}
		}
		return least;
	}

	private void decrease() {
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

//	@Override
	public synchronized Kiss getNextExcutableKiss(String browser) {
		int index = browserList.lastIndexOf(browser);
		int current = currentIndex[index];
		Kiss kiss = null;
//		current = current + 1;
//		while(current < kissList.size()) {
//			// 当没有case 存默认result 
//			storeEmpty(browser ,current);
//			current++;
////			store(result);
//		}
//		kissList.get
		// 如果kiss列表中还有可
		if (current < kissList.size()) {
			kiss  = kissList.get(current);
		}
		currentIndex[index] = ++current;
		logger.debug("browser : " +browser+ ",next case:" + kiss);
		return kiss;
	}

	@Override
	public boolean isDead() {
		return deadLine.isDead();
	}
}
