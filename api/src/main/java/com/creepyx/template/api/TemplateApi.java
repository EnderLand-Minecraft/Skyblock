package com.creepyx.template.api;

public class TemplateApi {

	private static CustomConfigUtil customConfigUtil;

	public static void reload(CustomConfigUtil customConfigUtil) {
		TemplateApi.customConfigUtil = customConfigUtil;
	}

	public static CustomConfigUtil getCustomConfigUtil() {
		return customConfigUtil;
	}
}