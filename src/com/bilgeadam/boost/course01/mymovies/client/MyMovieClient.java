package com.bilgeadam.boost.course01.mymovies.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

import com.bilgeadam.boost.course01.mymovies.client.communication.ServerCommunication;
import com.bilgeadam.boost.course01.mymovies.client.model.Database;
import com.bilgeadam.boost.course01.mymovies.client.model.Data;
import com.bilgeadam.boost.course01.mymovies.client.view.Menu;
import com.bilgeadam.boost.course01.mymovies.database.DatabaseSetup;

public class MyMovieClient {
	private String              id;
	private ServerCommunication communication;
	private Socket              socket;

	public MyMovieClient() {
		this.id = UUID.randomUUID().toString();
	}

	public static void main(String[] args) {

		MyMovieClient movieClient = new MyMovieClient();
		movieClient.checkDatabase();
		movieClient.connect2Server();
		try {
			movieClient.startUI();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void checkDatabase() {
		if (!Database.getInstance().isInitialized()) {
			System.err.println("Database is not initialized. Creating it...");
			 new DatabaseSetup().execute();
		}
		else {
			System.out.println("Database initialized");
			if (Database.getInstance().isLoaded()) {
				System.out.println("Data is loaded");
			}
			else {
				System.out.println("Data is not loaded");
				Data.parse();
				Data.load();
			}
		}
	}

	private void startUI() throws IOException {
		Menu menu = new Menu.Builder().title("Babür Hoca'nın Filmleri").body("IMDB verilerinden oluşturulmuştur")
				.build();
		menu.addMenu(1, "Artistin filmleri");
		menu.addMenu(2, "Yılın Filimleri");
		menu.addMenu(80, "CSV'leri yükle");
		menu.addMenu(99, "Programdan çık");
		int selection = -1;
		while (selection != 99) {
			selection = menu.show().readInteger();
			this.processSelection(selection);
		}
	}

	private void processSelection(int selection) throws IOException {
		switch (selection) {
		case 1: {
			String reply = communication.askForActorsMovies("Ingmar Bergman");
			this.showReply(reply);
			break;
		}
		case 2: {
			Scanner sc = new Scanner(System.in);
			System.out.println("Lütfen bir yıl giriniz: ");
			String reply = communication.ask4MoviesInYear(sc.nextLine());
			sc.close();
			this.showReply(reply);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + selection);
		}
	}

	private void showReply(String reply) {
		StringTokenizer tokenizer = new StringTokenizer(reply, "|");
		int             cnt       = 1;
		while (tokenizer.hasMoreElements()) {
			String token = (String) tokenizer.nextElement();
			System.out.printf("%03d - %s\n", cnt++, token);
		}
	}

	private void connect2Server() {
		try {
			this.socket        = new Socket("localhost", 4711);
			this.communication = new ServerCommunication(this.socket);
			communication.introduceClient(this.id);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
