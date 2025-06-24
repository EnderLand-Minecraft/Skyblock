package com.creepyx.template.bukkit.implementation;

import com.creepyx.template.api.CustomConfig;
import com.creepyx.template.api.CustomConfigUtil;

import java.io.File;

public class CustomConfigUtilImpl implements CustomConfigUtil {

	@Override
	public CustomConfig getCustomConfigInstance(File file) {
		return new CustomConfigImpl(file);
	}

	@Override
	public CustomConfig getCustomConfigInstance(String name) {
		return new CustomConfigImpl(name);
	}

	@Override
	public CustomConfig getCustomConfigInstance(String subFolder, String name) {
		return new CustomConfigImpl(subFolder, name);
	}
}
