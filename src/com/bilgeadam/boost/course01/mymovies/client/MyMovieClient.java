package com.bilgeadam.boost.course01.mymovies.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class MyMovieClient {
	private String         id;
	private PrintWriter    out;
	private BufferedReader in;

	public MyMovieClient() {
		this.id  = UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		MyMovieClient movieClient = new MyMovieClient();
		movieClient.connect2Server();
	}

	private void connect2Server() {
		try (Socket socket = new Socket("localhost", 4711)) {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Scanner sc = new Scanner(System.in);

			boolean waitForServer = true;

			while (waitForServer) {
				waitForServer = this.introduce();
				if (waitForServer) {
					System.out.println("Sunucu verileri yüklüyor... ");
					try {
						Thread.sleep(10000);
					}
					catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}

			String line = "FILMS:Ingmar Bergman";
			out.println(line);
			out.flush();
			System.out.println("Server replied " + in.readLine());
			
			
			line = "YEAR:" + sc.nextLine();
			out.println(line);
			out.flush();
			System.out.println("Server replied " + in.readLine());
			
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean introduce() throws IOException {
		out.println("INTR:" + this.id);
		out.flush();
		String answer = in.readLine();
		System.out.println(">>>" + answer);
		if (answer.startsWith("WAIT"))
			return true;
		else
			return false;
	}
}
