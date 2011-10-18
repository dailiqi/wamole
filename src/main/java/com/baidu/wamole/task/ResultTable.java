package com.baidu.wamole.task;

import java.io.IOException;

import com.baidu.wamole.model.Kiss;

/**
 * 存储结果的2维表
 * 
 * @author dailiqi
 */
public interface ResultTable {
	/**
	 * 存储一个结果，并且对超时时间减少一个interval,并返回下个可执行的kiss
	 * 
	 * @param result
	 */
	public Kiss store(Result result);

	/**
	 * 获取浏览器Index
	 * 
	 * @param browser
	 * @return
	 */
	public int getBrowserIndex(String browser);

	/**
	 * 获取CaseIndex
	 * 
	 * @param kissName
	 * @return
	 */
	public int getKissIndex(String kissName);

	/**
	 * 根据index获取结果
	 * 
	 * @param browserIndex
	 * @param kissIndex
	 * @return
	 */
	public Result getResult(int browserIndex, int kissIndex);

	// /**
	// * 报告
	// */
	public void save() throws IOException;

	/**
	 * 获取下个需要执行的kiss
	 * 
	 * @param browser 浏览器种类
	 * @return
	 */
	public Kiss getNextExcutableKiss(String browser);

	public boolean isDead();
}
