package com.creepyx.template.bukkit.database;

import com.creepyx.template.bukkit.TemplatePlugin;
import com.creepyx.template.bukkit.util.Scheduler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlUtil {

	private TemplatePlugin templatePlugin;

	public void init(TemplatePlugin templatePlugin) {
		this.templatePlugin = templatePlugin;
	}

	public ResultSet executeQuery(String query) {
		try {
			ResultSet rs = templatePlugin.getSqlConnection().getConnection().prepareStatement(query).executeQuery();
			templatePlugin.getSqlConnection().getConnection().close();
			return rs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public void insert(String table, String... values) {
		try {
			PreparedStatement ps = templatePlugin.getSqlConnection().getConnection().prepareStatement("INSERT INTO `"+ table +"` VALUES (` "+ String.join("`, `", values) +"`)");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(String table, String queryKey, String queryValue, String updateKey,String updateValue) {
		try {
			PreparedStatement ps = templatePlugin.getSqlConnection().getConnection().prepareStatement("UPDATE `" + table + "` SET `"+updateKey+"`=? WHERE `" + queryKey + "`=?");
			ps.setString(1,updateValue);
			ps.setString(2,queryValue);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void exampleQuery() {

		Scheduler.runAsync(() -> {

			try {
				PreparedStatement ps = templatePlugin.getSqlConnection().getConnection().prepareStatement("SELECT * FROM jobs WHERE uuid = ?");
				ps.setString(1, UUID.randomUUID().toString());
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Scheduler.runLater(() -> {
						try {
							rs.getString(" fsgwsg");
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					});
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}


		});

	}
	public ResultSet executeQuery(String table, String queryKey, String queryValue) {
		try {
			PreparedStatement ps = templatePlugin.getSqlConnection().getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + queryKey + "`=?");
			ps.setString(1,queryValue);
			ResultSet rs = ps.executeQuery();
			ps.close();
			return rs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



}
