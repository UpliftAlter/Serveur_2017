package main;

import ihm.FrameServeur;

import java.io.IOException;

import serveur.Serveur;

public class mainProg {

	public static void main(String[] args) throws IOException {
		FrameServeur fs = new FrameServeur();
		fs.setVisible(true);
		Serveur server = new Serveur();
	}

}
