package de.enderland.skyblock;

import de.enderland.skyblock.database.model.IslandModel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache {

	@Getter
	private static final Map<UUID, PlayerCache> caches = new HashMap<>();

	private final UUID uuid;

	@Getter@Setter
	private IslandModel island;

	private PlayerCache(UUID uuid) {
		this.uuid = uuid;
		load();
	}

	private PlayerCache(Player player) {
		this(player.getUniqueId());
	}

	private void load() {
		Player player = Bukkit.getPlayer(this.uuid);
		if (player == null) {
			throw new RuntimeException("Player not found");
		}

		try {
			this.island = SkyBlockPlugin.getInstance().getDatabaseConnection()
					.getIslandDao()
					.queryForEq("owner", player.getUniqueId())
					.stream().findFirst().orElse(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save() {
		SkyBlockPlugin.getInstance().getIslandService().save(island);
	}

	public static PlayerCache get(Player player) {
		UUID uuid = player.getUniqueId();
		PlayerCache result = caches.get(uuid);
		if (result == null) {
			result = new PlayerCache(uuid);
			caches.put(uuid, result);
		}
		return result;
	}

}
