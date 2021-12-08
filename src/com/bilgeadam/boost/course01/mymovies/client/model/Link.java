package com.bilgeadam.boost.course01.mymovies.client.model;

public class Link {
	private static long idCnt = 1;
	private long   id;
	private String imdb;
	private String tmdb;
	
	public Link(String imdb, String tmdb) {
		super();
		this.id   = Link.idCnt++;
		this.imdb = imdb;
		this.tmdb = tmdb;
	}

	public static long getIdCnt() {
		return idCnt;
	}

	public long getId() {
		return this.id;
	}

	public String getImdb() {
		return this.imdb;
	}

	public String getTmdb() {
		return this.tmdb;
	}
}
