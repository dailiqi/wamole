package com.baidu.wamole.exception;

public class ConfigException extends Exception {
	/** ID */
	private static final long serialVersionUID = 8410741233046776891L;

	public ConfigException(Exception e) {
		super(e);
	}
}
