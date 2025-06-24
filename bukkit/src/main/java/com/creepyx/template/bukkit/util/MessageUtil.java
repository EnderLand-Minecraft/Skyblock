package com.creepyx.template.bukkit.util;

import com.creepyx.template.api.CustomConfig;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.List;
import java.util.Map;

@UtilityClass
public class MessageUtil {

	@Setter
	private static CustomConfig config;

	public static Component get(String messageKey) {
		return ComponentUtil.asComponent(config.getString(messageKey));
	}

	public Component get(String messageKey, Map<String, String> placeholders) {
		return ComponentUtil.asComponent(config.getString(messageKey));
	}

	public List<TextComponent> getList(String messageKey) {
		return ComponentUtil.asFormattedList(config.getStringList(messageKey));
	}

	public List<TextComponent> getList(String messageKey, Map<String, String> placeholders) {
		return ComponentUtil.asFormattedList(config.getStringList(messageKey), placeholders);
	}

	public String getLegacy(String messageKey) {
		return config.getString(messageKey);
	}

	public List<String> getLegacyList(String messageKey) {
		return config.getStringList(messageKey);
	}
}
