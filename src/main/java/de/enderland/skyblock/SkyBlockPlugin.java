package de.enderland.skyblock;

import de.enderland.skyblock.database.DatabaseConnection;
import de.enderland.skyblock.service.IslandService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class SkyBlockPlugin extends JavaPlugin {

	@Getter
	private static SkyBlockPlugin instance;

	@Getter
	private IslandService islandService;

	@Getter
	private DatabaseConnection databaseConnection;

	@Getter
	private String prefix;

	private BukkitTask savePlayerCaches;

	@Getter
	@Setter
	private String logPrefix;

	@Override
	public void onEnable() {
		super.onEnable();
		this.prefix = "[EnderLand]";
		this.databaseConnection = new DatabaseConnection(/* no parameters because SQLite **/);
		instance = this;
		this.islandService = new IslandService(this.databaseConnection.getIslandDao(), this.databaseConnection.getIslandOptionsDao(), this.databaseConnection.getMemberDao());
		startTasks();
	}

	@Override
	public void onDisable() {
		this.savePlayerCaches.cancel();
	}

	private void startTasks() {
		this.savePlayerCaches = Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
			for (PlayerCache cache : PlayerCache.getCaches().values()) {
				cache.save();
			}
		}, 1, 5*60*20);
	}

}
