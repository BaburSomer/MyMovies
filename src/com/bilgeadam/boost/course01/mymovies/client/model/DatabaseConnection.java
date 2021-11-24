package com.bilgeadam.boost.course01.mymovies.client.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bilgeadam.boost.course01.mymovies.client.ClientProperties;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private Connection            connection;

	private DatabaseConnection() {
		super();
	}

	public static DatabaseConnection getInstance() {
		if (DatabaseConnection.instance == null) {
			DatabaseConnection.instance = new DatabaseConnection();
		}
		return DatabaseConnection.instance;
	}

	public Connection getConnection() {
		if (this.connection == null) {
			try {
				this.connection = DriverManager.getConnection(ClientProperties.getInstance().getDbURL());
			}
			catch (SQLException ex) {
				ex.printStackTrace();
				System.exit(-10);
			}
		}
		return this.connection;
	}
}
