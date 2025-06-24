package com.creepyx.template.bukkit.implementation;
import com.creepyx.template.api.CustomConfig;
import com.creepyx.template.bukkit.TemplatePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CustomConfigImpl extends CustomConfig {

	private final File file;

	public CustomConfigImpl(File file) {
		this.file = file;
		try {
			if (!this.file.exists()) {
				this.file.getParentFile().mkdirs();
				if (TemplatePlugin.getPlugin().getResource(file.getName()) != null)
					TemplatePlugin.getPlugin().saveResource(file.getName(), false);
				else {
					file.createNewFile();
				}
			}
			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public CustomConfigImpl(String file) {
		this(new File(TemplatePlugin.getPlugin().getDataFolder(), file));
	}

	public CustomConfigImpl(String folder, String file) {
		File directory = new File(TemplatePlugin.getPlugin().getDataFolder(), folder);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		this.file = new File(directory, file);

		try {
			if (!this.file.exists()) {
				if (!this.file.getParentFile().exists())
					this.file.getParentFile().mkdirs();
				if (TemplatePlugin.getPlugin().getResource(file) != null)
					TemplatePlugin.getPlugin().saveResource(file, false);
				else this.file.createNewFile();
			}
			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public @NotNull String getString(@NotNull String path) {
		if (!super.isSet(path)) return "";
		return Objects.requireNonNull(super.getString(path));
	}

	@Override
	public void set(@NotNull String path, @Nullable Object value) {
		if (value instanceof Location location) {
			this.set(path + ".World", location.getWorld());
			this.set(path + ".X", location.getX());
			this.set(path + ".Y", location.getY());
			this.set(path + ".Z", location.getZ());
			this.set(path + ".Yaw", location.getYaw());
			this.set(path + ".Pitch", location.getPitch());
			return;
		}
		super.set(path, value);
	}

	@Override
	public @Nullable Location getLocation(@NotNull String path) {
		String world = this.getString(path + ".World");
		int x = this.getInt(path + ".X");
		int y = this.getInt(path + ".Y");
		int z = this.getInt(path + ".Z");
		float yaw = (float) this.getDouble(path + ".Yaw");
		float pitch = (float) this.getDouble(path + ".Pitch");

		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	@Override
	public void save() {
		try {
			super.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
