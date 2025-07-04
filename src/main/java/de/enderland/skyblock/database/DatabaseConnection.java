package de.enderland.skyblock.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.enderland.skyblock.database.model.IslandModel;
import de.enderland.skyblock.database.model.IslandOptionsModel;
import de.enderland.skyblock.database.model.MemberModel;
import lombok.Getter;

import java.sql.SQLException;
import java.util.List;

public class DatabaseConnection {

	private final JdbcPooledConnectionSource connectionSource;
	private static final String SQLITE_PATH = "jdbc:sqlite:skyblock/island.db";
	@Getter
	private final Dao<IslandModel, Integer> islandDao;
	@Getter
	private final Dao<MemberModel, Integer> memberDao;
	@Getter
	private final Dao<IslandOptionsModel, Integer> islandOptionsDao;

	public DatabaseConnection(String host, int port, String database, String username, String password) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find MariaDB driver", e);
		}

		try {
			this.connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + host + ":" + port + "/" + database, username, password);
			this.connectionSource.setMaxConnectionAgeMillis(60 * 1000);
			this.connectionSource.setTestBeforeGet(true);

			this.islandDao = DaoManager.createDao(connectionSource, IslandModel.class);
			this.memberDao = DaoManager.createDao(connectionSource, MemberModel.class);
			this.islandOptionsDao = DaoManager.createDao(connectionSource, IslandOptionsModel.class);

			TableUtils.createTableIfNotExists(connectionSource, IslandModel.class);
			TableUtils.createTableIfNotExists(connectionSource, MemberModel.class);
			TableUtils.createTableIfNotExists(connectionSource, IslandOptionsModel.class);
		} catch (SQLException e) {
			throw new RuntimeException("Could not connect to MariaDB database", e);
		}
	}

	public DatabaseConnection() {
		try {
			this.connectionSource = new JdbcPooledConnectionSource(SQLITE_PATH);
			this.connectionSource.setMaxConnectionAgeMillis(60 * 1000);
			this.connectionSource.setTestBeforeGet(true);

			this.islandDao = DaoManager.createDao(connectionSource, IslandModel.class);
			this.memberDao = DaoManager.createDao(connectionSource, MemberModel.class);
			this.islandOptionsDao = DaoManager.createDao(connectionSource, IslandOptionsModel.class);

			TableUtils.createTableIfNotExists(connectionSource, IslandModel.class);
			TableUtils.createTableIfNotExists(connectionSource, MemberModel.class);
			TableUtils.createTableIfNotExists(connectionSource, IslandOptionsModel.class);
		} catch (SQLException e) {
			throw new RuntimeException("Could not connect to SQLite database", e);
		}
	}

	public List<MemberModel> getMembers(int id,String uuid) throws SQLException {
		return memberDao.queryBuilder().where().eq("island_id", id).and().eq("uuid", uuid).query();
	}
}
