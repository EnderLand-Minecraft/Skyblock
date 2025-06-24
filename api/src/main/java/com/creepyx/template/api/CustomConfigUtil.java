package com.creepyx.template.api;
import java.io.File;

public interface CustomConfigUtil {

	/**
	 * get a Instance of a Config from the requested File
	 */
	CustomConfig getCustomConfigInstance(File file);

	/**
	 * get a Instance of a Config from the requested Filename
	 */
	CustomConfig getCustomConfigInstance(String name);

	/**
	 * get a Instance of a Config from the requested subFolder and Filename
	 */
	CustomConfig getCustomConfigInstance(String subFolder, String name);
}
