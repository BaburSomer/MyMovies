package com.bilgeadam.boost.course01.mymovies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

 public class DatabaseSetup {

	 public static void main(String[] args) {
		 DatabaseSetup setup = new DatabaseSetup();
		 setup.execute();
	}
	
	 
	public void execute () {
		String url = "jdbc:postgresql://localhost:5432/";
		
		try (Connection con = DriverManager.getConnection(url, "postgres", "root");) {
			Statement stmt = con.createStatement();
			this.drop(stmt);
			this.create(stmt);
			this.createRole(stmt);
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		String url2 = "jdbc:postgresql://localhost:5432/mmdb";
		try (Connection con = DriverManager.getConnection(url2, "postgres", "root");) {
			Statement stmt = con.createStatement();
			this.createTableMovies(stmt);
			this.createTableUsers(stmt);
			this.createTableTags(stmt);
			this.createTableMovieTags(stmt);
			this.createTableTypes(stmt);
			this.createTableMovieTypes(stmt);
			this.createTableMovieRatings(stmt);
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}
	 
//	private void insertIntoMovies(Statement stmt) throws SQLException  {
//		int success = stmt.executeUpdate("insert into movies (id, name, year) values (1, 'kdslfjdk', 2022);");
//		System.out.println("Insertion "  + (success!=0?"successful":"failed"));
//	}

	private void createTableMovieRatings(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS movie_ratings CASCADE; "
				+ " "
				+ "CREATE TABLE IF NOT EXISTS movie_ratings "
				+ "( "
				+ "    movie_id integer NOT NULL, "
				+ "    user_id integer NOT NULL, "
				+ "    rating real NOT NULL, "
				+ "    rated_at bigint NOT NULL, "
				+ "    PRIMARY KEY (movie_id, user_id), "
				+ "    CONSTRAINT movie_id FOREIGN KEY (movie_id) "
				+ "        REFERENCES movies (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION "
				+ "        NOT VALID, "
				+ "    CONSTRAINT user_id FOREIGN KEY (user_id) "
				+ "        REFERENCES users (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION "
				+ "        NOT VALID "
				+ "); "
				+ " "
				+ "ALTER TABLE movie_ratings "
				+ "    OWNER to postgres; "
				+ " "
				+ "GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE movie_ratings TO omdb;");
		
		System.out.println("Table MOVIE_RATINGS creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableMovieTags(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS movie_tags CASCADE; "
				+ " "
				+ "CREATE TABLE IF NOT EXISTS movie_tags "
				+ "( "
				+ "    movie_id integer NOT NULL, "
				+ "    user_id integer NOT NULL, "
				+ "    tag_id integer NOT NULL, "
				+ "    tagged_at bigint NOT NULL, "
				+ "    CONSTRAINT movie_tags_pkey PRIMARY KEY (movie_id, user_id, tag_id), "
				+ "    CONSTRAINT movie_id FOREIGN KEY (movie_id) "
				+ "        REFERENCES movies (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION, "
				+ "    CONSTRAINT tag_id FOREIGN KEY (tag_id) "
				+ "        REFERENCES tags (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION, "
				+ "    CONSTRAINT user_id FOREIGN KEY (user_id) "
				+ "        REFERENCES users (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION "
				+ ") "
				+ " "
				+ "TABLESPACE pg_default; "
				+ " "
				+ "ALTER TABLE movie_tags "
				+ "    OWNER to postgres; "
				+ " "
				+ "GRANT DELETE, INSERT, SELECT, UPDATE ON TABLE movie_tags TO mmdb; "
				+ " "
				+ "GRANT ALL ON TABLE movie_tags TO postgres; "
				+ "");
		
		System.out.println("Table MOVIE_TAGS creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableMovieTypes(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS movie_types CASCADE; "
				+ " "
				+ "CREATE TABLE IF NOT EXISTS movie_types "
				+ "( "
				+ "    movie_id integer NOT NULL, "
				+ "    type_id integer NOT NULL, "
				+ "    CONSTRAINT movie_types_pkey PRIMARY KEY (movie_id, type_id), "
				+ "    CONSTRAINT movie_id FOREIGN KEY (movie_id) "
				+ "        REFERENCES movies (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION, "
				+ "    CONSTRAINT type_id FOREIGN KEY (type_id) "
				+ "        REFERENCES types (id) MATCH SIMPLE "
				+ "        ON UPDATE NO ACTION "
				+ "        ON DELETE NO ACTION "
				+ ") "
				+ " "
				+ "TABLESPACE pg_default; "
				+ " "
				+ "ALTER TABLE movie_types "
				+ "    OWNER to postgres; "
				+ " "
				+ "GRANT DELETE, INSERT, SELECT, UPDATE ON TABLE movie_types TO mmdb; "
				+ " "
				+ "GRANT ALL ON TABLE movie_types TO postgres; "
				+ "");
		
		System.out.println("Table MOVIE_TYPES creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableTypes(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS types CASCADE; "
				+ "CREATE TABLE IF NOT EXISTS types "
				+ "( "
				+ "    id integer NOT NULL, "
				+ "    type text NOT NULL, "
				+ "    PRIMARY KEY (id) "
				+ "); "
				+ "ALTER TABLE types OWNER to postgres; "
				+ "GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE types TO mmdb;");
		
		System.out.println("Table TYPES creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableUsers(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS users CASCADE; "
				+ "CREATE TABLE IF NOT EXISTS users "
				+ "( "
				+ "    id integer NOT NULL, "
				+ "    name text NOT NULL, "
				+ "    PRIMARY KEY (id) "
				+ "); "
				+ "ALTER TABLE users OWNER to postgres; "
				+ "GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE users TO mmdb;");
		
		System.out.println("Table USERS creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableTags(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS tags CASCADE; "
				+ "CREATE TABLE IF NOT EXISTS tags "
				+ "( "
				+ "    id integer NOT NULL, "
				+ "    tag text NOT NULL, "
				+ "    PRIMARY KEY (id) "
				+ "); "
				+ "ALTER TABLE tags OWNER to postgres; "
				+ "GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE tags TO mmdb;");
		
		System.out.println("Table TAGS creation "  + (success==0?"successful":"failed"));
	}
	
	private void createTableMovies(Statement stmt) throws SQLException  {
		int success = stmt.executeUpdate("DROP TABLE IF EXISTS movies CASCADE; "
				+ "CREATE TABLE IF NOT EXISTS movies "
				+ "( "
				+ "    id integer NOT NULL, "
				+ "    name text NOT NULL, "
				+ "    year integer NOT NULL, "
				+ "    imdb_id text, "
				+ "    PRIMARY KEY (id) "
				+ "); "
				+ "ALTER TABLE movies OWNER to postgres; "
				+ "GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE movies TO mmdb; ");
		System.out.println("Table MOVIES creation "  + (success==0?"successful":"failed"));
	}
	
	private void createRole(Statement stmt) throws SQLException {
		int success = stmt.executeUpdate(
				"DROP USER IF EXISTS omdb;"
						+ "CREATE USER omdb WITH "
						+ "    	LOGIN "
						+ "    	NOSUPERUSER "
						+ "    	NOCREATEDB "
						+ "    	NOCREATEROLE "
						+ "    	INHERIT "
						+ "    	NOREPLICATION "
						+ "    	CONNECTION LIMIT -1 "
						+ "    	PASSWORD 'omdb';"
						+ "GRANT pg_read_all_data, pg_write_all_data TO omdb;" );
		System.out.println("User creation " + (success==0?"successful":"failed"));
	}
	
	private void create(Statement stmt) throws SQLException {
		int success = stmt.executeUpdate(
				  "SET statement_timeout = 0; "
				+ "SET lock_timeout = 0; "
				+ "SET client_encoding = 'UTF8'; "
				+ "SET standard_conforming_strings = on; "
				+ "SET check_function_bodies = false; "
				+ "SET client_min_messages = warning; "
				+ " "
				+ "SET default_tablespace = ''; "
				+ " "
				+ "SET default_with_oids = false; "
				+ ""
				+ "CREATE DATABASE mmdb "
				+ "    WITH "
				+ "    OWNER = postgres "
				+ "    ENCODING = 'UTF8' "
				+ "    LC_COLLATE = 'English_United States.1252' "
				+ "    LC_CTYPE = 'English_United States.1252' "
				+ "    TABLESPACE = pg_default "
				+ "    CONNECTION LIMIT = -1; ");
		System.out.println("Database creation " + (success==0?"successful":"failed"));
	}

	private void drop(Statement stmt) {
		int success = -2;
		try {
			success = stmt.executeUpdate("DROP DATABASE mmdb;");
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Database dropping " + (success==0?"successful":"failed"));
	}
	
}
