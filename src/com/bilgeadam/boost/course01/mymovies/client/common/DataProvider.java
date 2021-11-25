package com.bilgeadam.boost.course01.mymovies.client.common;

import java.util.HashMap;
import java.util.TreeMap;

import com.bilgeadam.boost.course01.mymovies.client.model.Movie;
import com.bilgeadam.boost.course01.mymovies.client.model.MovieType;
import com.bilgeadam.boost.course01.mymovies.client.model.Tag;
import com.bilgeadam.boost.course01.mymovies.client.model.User;

public class DataProvider {
	private static DataProvider        instance;
	private TreeMap<Long, Movie>       movies;
	private HashMap<String, MovieType> types;
	private HashMap<String, Tag>       tags;
	private HashMap<Long, User>        users;

	private DataProvider() {
		super();
	}

	public static DataProvider getInstance() {
		if (DataProvider.instance == null) {
			DataProvider.instance = new DataProvider();
		}
		return DataProvider.instance;
	}

	public TreeMap<Long, Movie> getMovies() {
		if (this.movies == null) {
			this.movies = new TreeMap<>();
		}
		return this.movies;
	}

	public HashMap<String, MovieType> getTypes() {
		if (this.types == null) {
			this.types = new HashMap<>();
		}
		return this.types;
	}

	public HashMap<String, Tag> getTags() {
		if (this.tags == null) {
			this.tags = new HashMap<>();
		}
		return this.tags;
	}

	public HashMap<Long, User> getUsers() {
		if (this.users == null) {
			this.users = new HashMap<>();
		}
		return this.users;
	}
	
	public Movie getMovie(long movieId) {
		return this.getMovies().get(movieId);
	}
}
