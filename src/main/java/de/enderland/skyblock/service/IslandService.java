package de.enderland.skyblock.service;

import com.j256.ormlite.dao.Dao;
import de.enderland.skyblock.SkyBlockPlugin;
import de.enderland.skyblock.database.model.IslandModel;
import de.enderland.skyblock.database.model.IslandOptionsModel;
import de.enderland.skyblock.database.model.MemberModel;
import de.enderland.skyblock.util.IslandOptions;
import de.enderland.skyblock.util.IslandUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public record IslandService(Dao<IslandModel, Integer> islandDao, Dao<IslandOptionsModel, Integer> islandOptionsDao,
							Dao<MemberModel, Integer> memberDao) {

	public IslandModel loadIsland(UUID uuid) {
		try {
			IslandModel island = islandDao.queryForEq("owner", uuid).getFirst();

			if (island == null) {
				createIsland(uuid);
				return islandDao.queryForEq("owner", uuid).getFirst();
			}
			return island;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public IslandModel findByIslandId(String islandId) {
		try {
			return islandDao.queryForEq("island_id", islandId).getFirst();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public IslandModel findByOwner(UUID uuid) {
		try {
			return islandDao.queryForEq("owner", uuid).getFirst();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setOption(IslandModel island, IslandOptions option, boolean value) {
		try {
			IslandOptionsModel optionsModel = islandOptionsDao.queryBuilder().where().eq("island_id", island.getIsland_id()).and().eq("option", option.name().toLowerCase()).queryForFirst();
			if (optionsModel == null) {
				optionsModel = new IslandOptionsModel();
				optionsModel.setIsland(island);
				optionsModel.setOption(option.getLowerCaseName());
				optionsModel.setEnabled(value);
				islandOptionsDao.createOrUpdate(optionsModel);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean getOption(IslandModel island, IslandOptions option) {
		try {
			if (Bukkit.getOfflinePlayer(UUID.fromString(island.getOwner())).isOnline())
				return island.getOptions().stream().anyMatch(optionModel -> optionModel.getOption().equalsIgnoreCase(option.getLowerCaseName()));
			IslandOptionsModel islandOptions = islandOptionsDao.queryBuilder().where().eq("island_id", island.getIsland_id()).and().eq("option", option.name().toLowerCase()).queryForFirst();
			if (islandOptions == null) {
				return false;
			}
			return islandOptions.isEnabled();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void addMember(IslandModel island, String memberUUID) {
		try {
			MemberModel memberModel = new MemberModel();
			memberModel.setIsland(island);
			memberModel.setRole("member");
			memberModel.setUuid(memberUUID);
			memberDao.createOrUpdate(memberModel);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeMember(IslandModel island, String memberUUID) {
		try {
			MemberModel memberModel = memberDao.queryBuilder()
					.where().eq("island_id", island).and()
					.eq("uuid", memberUUID).queryForFirst();
			if (memberModel == null) {
				return;
			}
			memberDao.delete(memberModel);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(IslandModel island) {
		try {
			islandDao.createOrUpdate(island);

			for (MemberModel memberModel : island.getMembers()) {
				memberDao.createOrUpdate(memberModel);
			}

			for (IslandOptionsModel islandOptionsModel : island.getOptions()) {
				islandOptionsDao.createOrUpdate(islandOptionsModel);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void createIsland(UUID uuid) {

		IslandUtil.createIsland(uuid, world -> {
			Location centerLocation = new Location(world, 0, 65, 0);
			Bukkit.getScheduler().runTaskLater(SkyBlockPlugin.getInstance(), () -> {
				IslandUtil.createIslandModel(Objects.requireNonNull(Bukkit.getPlayer(uuid)), centerLocation);
			}, 1);
		});
	}
}
