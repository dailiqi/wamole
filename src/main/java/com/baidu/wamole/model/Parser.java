package com.baidu.wamole.model;

import java.util.Map;

public interface Parser<T extends Kiss, P extends Project<? , ?>> {
	public Map<String ,T> parse(P project);
}
