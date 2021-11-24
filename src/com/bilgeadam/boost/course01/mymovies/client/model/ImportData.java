package com.bilgeadam.boost.course01.mymovies.client.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bilgeadam.boost.course01.mymovies.client.ClientProperties;

public class ImportData {
//	final String query = "COPY movies FROM '" + path + "' DELIMITER ',' CSV HEADER;";

	public ImportData() {
		super();
	}

	public boolean databaseInitialized() {
		final String query = "SELECT * FROM pg_database WHERE datname=?;";
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432", "postgres", "root")) {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, ClientProperties.getInstance().getDBName());
			ResultSet resut = st.executeQuery();
			while (resut.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			System.out.println("'isThereDatabase'" + /*+ language.LOG_ERROR() + ": " + language.LOG_IS_NOT_SUCCESSFUL() + "\n" */ e.getMessage());
			return false;
		}
		return false;

	}

	public void importMovies() {

	}
}
