package main;

import java.io.IOException;

import serveur.Serveur;

public class MainProg {

	public static void main(String[] args){
		try {
			new Serveur();
		} catch (IOException e) {
			System.out.println("Server has stopped successfully");
		}
	}
}
