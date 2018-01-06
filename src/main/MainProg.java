package main;

import java.io.IOException;

import ihm.FrameServeur;
import serveur.Serveur;

public class MainProg {

	public static void main(String[] args) throws IOException {
		FrameServeur fs = new FrameServeur();
		fs.setVisible(true);
		Serveur server = new Serveur();
	}

}
