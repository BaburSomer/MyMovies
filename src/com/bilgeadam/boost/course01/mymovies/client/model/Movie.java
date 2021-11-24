package com.bilgeadam.boost.course01.mymovies.client.model;

import java.util.ArrayList;

public class Movie {
	private long              id;
	private String            name;
	private int               year;
	private String            imdb;
	private ArrayList<String> types;

	public Movie(long id, String name, int year) {
		super();
		this.id   = id;
		this.name = name;
		this.year = year;
		this.imdb = null;
		this.types = new ArrayList<>();
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getYear() {
		return this.year;
	}

	public String getImdb() {
		return this.imdb;
	}

	public void setImdb(String id) {
		this.imdb = id;
	}

	public void addType (String type) {
		this.types.add(type);
	}
	
	public ArrayList<String> getTypes() {
		return this.types;
	}
	
	@Override
	public String toString() {
		return "Movie [id=" + this.id + ", name=" + this.name + ", year=" + this.year + ", imdb=" + this.imdb + "]";
	}
	
}
