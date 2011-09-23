package com.baidu.wamole.exception;

public class TestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6165496379875666200L;

	public TestException(String msg, Exception e) {
		super(msg, e);
	}

	public TestException(Exception e) {
		super(e);
	}

	public TestException(String msg) {
		super(msg);
	}
}
