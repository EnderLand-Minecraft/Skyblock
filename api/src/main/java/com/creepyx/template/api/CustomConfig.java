package com.creepyx.template.api;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class CustomConfig extends YamlConfiguration {

	/**
	 * Save a Custom Config in a yaml Format
	 */
	public abstract void save();
}
