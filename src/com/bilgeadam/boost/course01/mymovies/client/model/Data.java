package com.bilgeadam.boost.course01.mymovies.client.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.bilgeadam.boost.course01.mymovies.client.ClientProperties;
import com.bilgeadam.boost.course01.mymovies.utils.Props;

public class Data {
	private static LinkedList<Movie>          movies;
	private static HashMap<String, MovieType> types;

	public Data() {
		super();
		Data.movies = new LinkedList<>();
		Data.types  = new HashMap<>();
	}

	public static void parse() {
		File file = new File(ClientProperties.getInstance().getMoviesCSV());

		try (FileReader fR = new FileReader(file); BufferedReader bR = new BufferedReader(fR);) {
			while (true) {
				String line = bR.readLine();
				if (line == null)
					break;
				if (line.startsWith("movieId"))
					continue;

				int firstCommaPos = line.indexOf(",");
				int lastCommaPos  = line.lastIndexOf(",");

				long   id     = Long.parseLong(line.substring(0, firstCommaPos));
				String genres = line.substring(lastCommaPos + 1);
				String name   = line.substring(firstCommaPos + 1, lastCommaPos);
				int    pos    = name.lastIndexOf("(");
				int    year   = Integer.parseInt(name.substring(pos + 1, name.lastIndexOf(")")));
				name = name.substring(0, pos).trim();
				Movie movie = new Movie(id, name, year);

				StringTokenizer tokenizer = new StringTokenizer(genres, "|");
				while (tokenizer.hasMoreElements()) {
					String genre = ((String) tokenizer.nextToken()).toUpperCase();
					Object obj = Data.types.get(genre);
					if (obj == null) {
						MovieType type = new MovieType(genre);
						Data.types.put(genre, type);
					}
					movie.addType(genre);
				}

				Data.movies.add(movie);
			}
		}
		catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		catch (IOException ex) {
			// TODO Auto-generated catch block
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
}
