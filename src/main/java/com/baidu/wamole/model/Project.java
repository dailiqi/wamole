package com.baidu.wamole.model;

import java.util.List;

import com.baidu.wamole.exception.TestException;

public interface Project {
	/**
	 * project alias
	 * 
	 * @return
	 */
	String getName();

	/**
	 * project base dir
	 * 
	 * @return
	 */
	String getRootDir();

	/**
	 * 根据名称获取具体用例
	 * 
	 * @param kissName
	 * @return
	 */
	Kiss getKiss(String kissName);

	/**
	 * 获取用例列表
	 * 
	 * @return
	 */
	List<Kiss> getKisses();

	/**
	 * 根据查询字符串返回可执行用例的列表页
	 * 
	 * @param searchString
	 * @return
	 */
	String getExecutePage(String searchString) throws TestException;
}
