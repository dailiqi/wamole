package com.baidu.wamole.config;

import com.baidu.wamole.core.Ext;
import com.baidu.wamole.core.ExtensionPoint;
import com.baidu.wamole.core.Node;
import com.baidu.wamole.exception.ConfigException;

@Ext()
public interface Config extends ExtensionPoint, Node {

	public void config() throws ConfigException;

	public static class NOOBConfig implements Config {

		@Override
		public void config() throws ConfigException {
			System.out.println("noob do nothing");
		}
	}
}
