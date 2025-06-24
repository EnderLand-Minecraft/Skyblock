package com.creepyx.template.bukkit.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlConnection {

	private Connection connection;

	@Getter
	private static SqlConnection instance = new SqlConnection();

	@Getter
	private List<String> lastCredentials = new ArrayList<>();

	public SqlConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to find driver class for mariadb", e);
		}


		try {
			this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/project", "root", "");
			this.lastCredentials.addAll(List.of("jdbc:mariadb://localhost:3306/project", "root", ""));
		} catch (SQLException e) {
			throw new RuntimeException("Cannot connect to the Database, please check the credentials in the Config.yml", e);
		}
	}

	private Connection connectWithLastCredentials() {
		try {
			this.connection = DriverManager.getConnection(this.lastCredentials.get(0), this.lastCredentials.get(1), this.lastCredentials.get(2));
			return this.connection;
		} catch (SQLException e) {
			throw new RuntimeException("Cannot connect to the Database, please check the credentials in the Config.yml", e);
		}
	}

	private Connection validateConnection() {
		try {
			if (connection == null || connection.isClosed() || !connection.isValid(3)) {
				return this.connectWithLastCredentials();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Cannot connect to the Database, please check the credentials in the Config.yml", e);
		}
		return this.connection;
	}

	protected Connection getConnection() {
		return this.validateConnection();
	}
}
