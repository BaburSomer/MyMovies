package com.bilgeadam.boost.course01.mymovies.client.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import com.bilgeadam.boost.course01.mymovies.client.ClientProperties;
import com.bilgeadam.boost.course01.mymovies.client.common.DataProvider;

public class Data {

	public Data() {
		super();
	}

	public static void parse() {
		parseMovies();
		parseLinks();
		parseTags();
		parseRatings();
	}

	private static void parseTags() {
		File file;
		file = new File(ClientProperties.getInstance().getTagsCSV());
		try (FileReader fR = new FileReader(file); BufferedReader bR = new BufferedReader(fR);) {
			while (true) {
				String line = bR.readLine();
				if (line == null)
					break;
				if (line.startsWith("userId"))
					continue;
				Tag.parse(line);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void parseRatings() {
		File file;
		file = new File(ClientProperties.getInstance().getRatingsCSV());
		try (FileReader fR = new FileReader(file); BufferedReader bR = new BufferedReader(fR);) {
			while (true) {
				String line = bR.readLine();
				if (line == null)
					break;
				if (line.startsWith("userId"))
					continue;
				Rating.parse(line);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void parseLinks() {
		File file;
		file = new File(ClientProperties.getInstance().getLinksCSV());
		try (FileReader fR = new FileReader(file); BufferedReader bR = new BufferedReader(fR);) {
			while (true) {
				String line = bR.readLine();
				if (line == null)
					break;
				if (line.startsWith("movieId"))
					continue;
				Movie.parseLink(line);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void parseMovies() {
		File file = new File(ClientProperties.getInstance().getMoviesCSV());

		try (FileReader fR = new FileReader(file); BufferedReader bR = new BufferedReader(fR);) {
			while (true) {
				String line = bR.readLine();
				if (line == null)
					break;
				if (line.startsWith("movieId"))
					continue;
				Movie.parse(line);
			}
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean importMovies() {
		try (Connection conn = Database.getInstance().getConnection(); Statement stmt = conn.createStatement();) {
//			return stmt.execute("COPY movies FROM '" + ClientProperties.getInstance().getMoviesCSV() + "' DELIMITER ',' CSV HEADER");
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return false;
	}

	public boolean importTags() {
		try (Connection conn = Database.getInstance().getConnection(); Statement stmt = conn.createStatement();) {
//			return stmt.execute("COPY movies FROM '" + ClientProperties.getInstance().getMoviesCSV() + "' DELIMITER ',' CSV HEADER");
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return false;
	}

	public static void load() {
		loadMovies();
		loadTypes();
		loadTags();
		loadUsers();
	}

	private static void loadMovies() {
		Set<Long> keys = DataProvider.getInstance().getMovies().keySet();
		String query = "INSERT INTO movies (id, name, year, imdb_id) VALUES (?, ?, ?, ?)";
		try (Connection conn = Database.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			for (Iterator<Long> iterator = keys.iterator(); iterator.hasNext();) {
				Movie movie = DataProvider.getInstance().getMovie(iterator.next());
				stmt.setLong(1, movie.getId());
				stmt.setString(2, movie.getName());
				stmt.setInt(3, movie.getYear());
				stmt.setString(4, movie.getImdb());
				stmt.execute();
			}
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	private static void loadTypes() {
		int cnt = 0;
		Set<String> keys = DataProvider.getInstance().getTypes().keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			System.out.println(DataProvider.getInstance().getTypes().get(iterator.next()));
			if (++cnt > 100)
				break;
		}
	}
	
	private static void loadTags() {
		int cnt = 0;
		Set<String> keys = DataProvider.getInstance().getTags().keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			System.out.println(DataProvider.getInstance().getTags().get(iterator.next()));
			if (++cnt > 100)
				break;
		}
	}
	
	private static void loadUsers() {
		int cnt = 0;
		Set<Long> keys = DataProvider.getInstance().getUsers().keySet();
		for (Iterator<Long> iterator = keys.iterator(); iterator.hasNext();) {
			System.out.println(DataProvider.getInstance().getUsers().get(iterator.next()));
			if (++cnt > 100)
				break;
		}
	}
}
