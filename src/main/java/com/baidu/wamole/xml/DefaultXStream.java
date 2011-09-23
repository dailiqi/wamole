package com.baidu.wamole.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;

public class DefaultXStream extends XStream {
	public DefaultXStream() {
		super();
		init();
	}

	private void init() {
		//添加注册converter
		registerConverter(new ConcurrentHashMapConverter(getMapper(),
				getReflectionProvider()));
		registerConverter(new CopyOnWriteList.ConverterImpl(getMapper()));
	}

	public DefaultXStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
		super(hierarchicalStreamDriver);
		init();
	}
}
