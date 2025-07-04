package de.enderland.skyblock.util.config;

import de.enderland.skyblock.SkyBlockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Config extends CustomConfig {

	private final File file;


	public Config(File file) {
		this.file = file;

		try {
			if (!this.file.exists()) {
				this.file.getParentFile().mkdirs();

				if (SkyBlockPlugin.getInstance().getResource(file.getName()) != null) {
					SkyBlockPlugin.getInstance().saveResource(file.getName(), false);
				} else {
					file.createNewFile();
				}
			}

			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public Config(String file) {
		this(new File(SkyBlockPlugin.getInstance().getDataFolder(), file));
	}

	public Config(File folder, File file) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("The folder must be a directory");
		}
		if (!folder.exists()) {
			folder.mkdirs();
		}

		this.file = new File(folder, file.getName());

		try {
			if (!this.file.exists()) {
				File parentFile = this.file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}

				String resourcePath = folder.toPath().resolve(file.getName()).toFile().getPath();

				if (SkyBlockPlugin.getInstance().getResource(resourcePath) != null) {
					SkyBlockPlugin.getInstance().saveResource(resourcePath, false);
				} else {
					this.file.createNewFile();
				}
			}

			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public Config(String folder, String file) {
		File directory = new File(SkyBlockPlugin.getInstance().getDataFolder(), folder);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		this.file = new File(directory, file);

		try {
			if (!this.file.exists()) {
				if (!this.file.getParentFile().exists()) {
					this.file.getParentFile().mkdirs();
				}

				if (SkyBlockPlugin.getInstance().getResource(file) != null) {
					SkyBlockPlugin.getInstance().saveResource(file, false);
				} else {
					this.file.createNewFile();
				}
			}
			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public @NotNull String getString(@NotNull String path) {
		if (!super.isSet(path)) {
			return "";
		}

		return Objects.requireNonNull(super.getString(path));
	}

	@Override
	public void set(@NotNull String path, @Nullable Object value) {
		if (value instanceof Location location) {
			this.set(path + ".World", location.getWorld().getName());
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
		double x = this.getDouble(path + ".X");
		double y = this.getDouble(path + ".Y");
		double z = this.getDouble(path + ".Z");
		float yaw = (float) this.getDouble(path + ".Yaw");
		float pitch = (float) this.getDouble(path + ".Pitch");

		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	@Override
	public CustomConfig get(File file) {
		return new Config(file);
	}

	@Override
	public CustomConfig get(File directory, File file) {
		return new Config(directory, file);
	}

	@Override
	public CustomConfig get(@NotNull String file) {
		return new Config(file);
	}

	@Override
	public CustomConfig get(String folder, String file) {
		return new Config(folder, file);
	}

	public void save() {
		try {
			super.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}