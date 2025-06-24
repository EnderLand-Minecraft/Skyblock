package com.creepyx.template.bukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentUtil {

	public static TextComponent asComponent(String text) {
		if (text == null) {
			return Component.empty();
		}

		if (text.contains("&") || text.contains("§")) {
			return LegacyComponentSerializer.legacyAmpersand().deserialize("§f" + text);
		}

		return (TextComponent) MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.color()).resolver(StandardTags.clickEvent()).resolver(StandardTags.rainbow()).resolver(StandardTags.decorations()).resolver(StandardTags.font()).resolver(StandardTags.newline()).resolver(StandardTags.translatable()).resolver(StandardTags.keybind()).resolver(StandardTags.reset()).resolver(StandardTags.insertion()).resolver(StandardTags.hoverEvent()).resolver(StandardTags.score()).resolver(StandardTags.selector()).resolver(StandardTags.translatableFallback()).resolver(StandardTags.transition()).resolver(StandardTags.decorations(TextDecoration.ITALIC.withState(true).decoration())).build()).build().deserialize(text);
	}

	public static TextComponent asComponent(@NotNull String text, @NotNull Map<String, String> placeholders) {
		String finalText = text;
		for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			String key = "%" + entry.getKey() + "%";
			String value = entry.getValue();
			finalText = finalText.replace(key, value);
		}
		return asComponent(finalText);
	}

	public static List<TextComponent> asFormattedList(@NotNull List<String> list) {
		return asFormattedList(list, new HashMap<>());
	}

	public static List<TextComponent> asFormattedList(@NotNull List<String> list, @NotNull Map<String, String> placeholders) {
		List<TextComponent> components = new ArrayList<>();

		for (String line : list) {
			components.add(asComponent(line, placeholders));
		}
		return components;
	}
}
