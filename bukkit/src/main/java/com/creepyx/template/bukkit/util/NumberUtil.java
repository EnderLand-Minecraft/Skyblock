package com.creepyx.template.bukkit.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class NumberUtil {

	public String formatDouble(double d, int digits) {
		DecimalFormat formatter = new DecimalFormat("#.##");
		formatter.setMaximumFractionDigits(digits);
		return formatter.format(d);
	}

}
