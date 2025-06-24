package com.creepyx.template.bukkit;

import com.creepyx.template.bukkit.util.Scheduler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class Database_example {

	private Connection connection;

	public void loadAnything(Player player, Consumer<?> callback) {

		Scheduler.runAsync(() -> {

			try {

				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE name = '" + player.getName() + "'");
				ResultSet rs = preparedStatement.executeQuery("SELECT * FROM players WHERE name = '" + player.getName() + "'");

				if (!rs.next()) {
					Scheduler.runLater(() -> callback.accept(null));
				}

				Scheduler.runLater(() -> callback.accept(null));

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}


		});

	}
}
