package de.enderland.skyblock.util;

import de.enderland.skyblock.SkyBlockPlugin;
import de.enderland.skyblock.util.config.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;

public class LogUtil {

	private static String prefix;

	public static void log(LogType logType, String message) {

		ConsoleCommandSender consoleSender = SkyBlockPlugin.getInstance().getServer().getConsoleSender();
		if (prefix == null) {
			prefix = getPrefix();
		}
		if (logType == LogType.DEBUG) {
			if (!new Config("config.yml").getBoolean("debug"))
				return;
		}
		Component finalMessage = StringUtil.asComponent(prefix).append(StringUtil.asComponent(logType.prefix).color(logType.color)).append(StringUtil.asComponent(message).color(logType.color));
		consoleSender.sendMessage(finalMessage);
	}

	private static String getPrefix() {
		return SkyBlockPlugin.getInstance().getLogPrefix() != null ? SkyBlockPlugin.getInstance().getLogPrefix() : ("[" + SkyBlockPlugin.getInstance().getName() + "]");
	}
}