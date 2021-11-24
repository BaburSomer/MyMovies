package com.bilgeadam.boost.course01.mymovies.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.bilgeadam.boost.course01.mymovies.server.common.CommonData;
import com.bilgeadam.boost.course01.mymovies.utils.Props;

public class MyMovieServer {

	public static void main(String[] args) {
		
		CommonData.getInstance().loadPropertiesFile(args);
		
		(new MyMovieServer()).start();
	}

	private void start() {
		CommonData.getInstance().logInfo("Server starting ...");
		
		
		// (1)  eðer gerekli ise TSV'lerden verileri okuyacak, parse edecek ve seriyalize edecek, yoksa doðrudan verileri deserialize edecek
		new Thread(new DataProcessing()).start();
		
		// (2)  client'lar ile iletiþim kurup gelen sorularý yanýtlayacak
		this.startServer(); //client'lar ile iletiþim
				
		CommonData.getInstance().logInfo("Server stopped");
	}

	private void startServer() {
		try (ServerSocket server = new ServerSocket(Props.getInstance().getServerPort());) {
			server.setReuseAddress(true);  								// port'u bir çok client'ýn kullanabileceði þekilde ayarla. 
			CommonData.getInstance().logInfo("Server started and waiting for clients...");
			while (true) {
				Socket client = server.accept();
				CommonData.getInstance().logInfo("New client connected" + client.getInetAddress().getHostAddress());
				ClientHandler clienthandler = new ClientHandler(client); 	// create a new thread object her yeni baðlantý isteði geldiðinde bir clientHandle oluþturuyor
				new Thread(clienthandler).start(); 							// This thread will handle the client separately ve onu yeni bir thread içinde çalýþtýrýyor
				System.out.println("Number of clients connected: " + CommonData.getInstance().numClients());
			}
		}
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
