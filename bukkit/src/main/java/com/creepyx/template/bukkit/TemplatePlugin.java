package com.creepyx.template.bukkit;

import com.creepyx.template.api.CustomConfig;
import com.creepyx.template.api.TemplateApi;
import com.creepyx.template.bukkit.command.SimpleCommand;
import com.creepyx.template.bukkit.database.SqlConnection;
import com.creepyx.template.bukkit.implementation.CustomConfigImpl;
import com.creepyx.template.bukkit.implementation.CustomConfigUtilImpl;
import com.creepyx.template.bukkit.util.MessageUtil;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TemplatePlugin extends JavaPlugin implements Listener {

	@Getter
	private static TemplatePlugin instance;
	@Getter
	private static CustomConfig settings;
	@Getter
	private static Plugin plugin;
	@Getter
	private SqlConnection sqlConnection;


	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
		super.onLoad();
	}

	@Override
	public void onEnable() {
		CommandAPI.onEnable();
		instance = this;
		plugin = this;
		TemplateApi.reload(new CustomConfigUtilImpl());
		settings = new CustomConfigImpl("Config.yml");
		MessageUtil.setConfig(new CustomConfigImpl("Lang/" + settings.getString("Lang") + ".yml"));
		if (MessageUtil.get("no_permission") == null) {
			MessageUtil.setConfig(new CustomConfigImpl("Lang/en_US.yml"));
		}
//		sqlConnection = SqlConnection.getInstance();
		new SimpleCommand();
		getLogger().info("Template-Plugin enabled!");
	}

	@Override
	public void onDisable() {
		CommandAPI.onDisable();
		getLogger().info("Template-Plugin disabled!");
	}
}