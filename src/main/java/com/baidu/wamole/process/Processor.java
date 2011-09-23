package com.baidu.wamole.process;

import com.baidu.wamole.model.Kiss;
import com.caucho.config.ConfigException;

public interface Processor<T extends Kiss> {

	/**
	 * 根据用例进行处理
	 * @param kiss
	 * @return
	 * @throws ConfigException 
	 */
	public String process(T kiss) throws ConfigException;
}
