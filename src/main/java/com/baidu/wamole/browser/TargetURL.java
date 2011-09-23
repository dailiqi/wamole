package com.baidu.wamole.browser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.baidu.wamole.browser.StaticBrowser;

public class TargetURL {

	private String host;// 127.0.0.1
	private String project;// tangram
	private String path;// run.do
	private String port;
	private Map<String, String> params; // case=baidu.ajax.post

	public TargetURL() {
		params = new HashMap<String, String>();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void addParam(String key, String value) {
		this.params.put(key, value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(host);
		if (null != port && !"".equals(port)) {
			sb.append(":");
			sb.append(port);
		}
		sb.append("/");
		if (null != project && !"".equals(project)) {
			sb.append(project);
			sb.append("/");
		}
		sb.append(path);
		if (params.size() > 0) {
			sb.append("?");
			for (String key : params.keySet()) {
				sb.append(key);
				sb.append("=");
				sb.append(params.get(key));
				sb.append("&");
			}
			sb.setLength(sb.length()-1);
		}
		return sb.toString();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public static TargetURL getURL(StaticBrowser browser) {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		TargetURL url = new TargetURL();
		url.setHost(addr.getHostAddress().toString());// 获得本机IP
		url.setPath("capture");
		return url;
	}
}
