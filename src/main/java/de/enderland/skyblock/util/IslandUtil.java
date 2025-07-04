package de.enderland.skyblock.util;

import de.enderland.skyblock.PlayerCache;
import de.enderland.skyblock.SkyBlockPlugin;
import de.enderland.skyblock.database.model.IslandModel;
import de.enderland.skyblock.database.model.IslandOptionsModel;
import de.enderland.skyblock.util.serializer.Base64Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

public class IslandUtil {

	private static final String TEMPLATE_DIR = "templates/skyblock";
	private static final String DEFAULT_TEMPLATE = "skyblock_template";
	private static final File WORLD_DIR = Bukkit.getWorldContainer();


	public static void createIsland(UUID uuid, Consumer<World> callback) {
		String worldName = "skyblock_" + uuid;
		File targetFolder = new File(WORLD_DIR + "/skyblock", worldName);
		File templateFolder = new File(WORLD_DIR, TEMPLATE_DIR);

		if (!templateFolder.exists()) {
			LogUtil.log(LogType.WARNING, "Template-World '" + DEFAULT_TEMPLATE + "' inside '" + templateFolder + "' was not found!");
			return;
		}

		Bukkit.getScheduler().runTaskAsynchronously(SkyBlockPlugin.getInstance(), () -> {
			copyDirectory(templateFolder.toPath(), targetFolder.toPath());

			Bukkit.getScheduler().runTask(SkyBlockPlugin.getInstance(), () -> {
				World world = Bukkit.createWorld(new WorldCreator(worldName));
				if (world == null) {
					LogUtil.log(LogType.WARNING,"World '" + worldName + "' could not be loaded!");
					return;
				}
				callback.accept(world);
			});
		});
	}

	public static void createIslandModel(Player player, Location centerLocation) {
		String worldName = centerLocation.getWorld() != null ? centerLocation.getWorld().getName() : "unknown";

		IslandModel islandModel = new IslandModel();
		islandModel.setIsland_id(worldName);
		islandModel.setOwner(player.getUniqueId().toString());
		islandModel.setLocation(Base64Serializer.serialize(centerLocation));

		try {
			SkyBlockPlugin.getInstance().getDatabaseConnection().getIslandDao().createOrUpdate(islandModel);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		for (IslandOptions islandOption : IslandOptions.values()) {
			SkyBlockPlugin.getInstance().getIslandService().setOption(islandModel, islandOption, false);
		}

		try {
			PlayerCache.get(player).setIsland(
					SkyBlockPlugin.getInstance()
							.getDatabaseConnection()
							.getIslandDao()
							.queryForEq("owner", player.getUniqueId()).getFirst()
			);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void copyDirectory(Path source, Path target) {
		try(DirectoryStream<Path> sourceStream = Files.newDirectoryStream(source)) {
			sourceStream.forEach(path -> {
						try {
							Path destination = target.resolve(source.relativize(path));
							if (Files.isDirectory(path)) {
								if (!Files.exists(destination)) Files.createDirectory(destination);
							} else {
								Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING);
							}
						} catch (IOException e) {
							throw new RuntimeException("Error during copy of " + path, e);
						}
					});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
