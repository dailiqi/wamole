package com.baidu.wamole.task;

public class Result {
	private String name;
	private int fail;
	private int total;
	private String browser;
	private long timeStamp;
	private String task;
	private boolean isError;

	@Override
	public String toString() {
		return "result: name = " + name + "	fail:" + fail + "	total:" + total;
	}

	public String getName() {
		return name;
	}

	public int getTotal() {
		return total;
	}

	public int getFail() {
		return fail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public boolean isError() {
		return isError;
	}

}
