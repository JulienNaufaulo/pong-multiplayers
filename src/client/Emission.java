package client;

import java.io.PrintWriter;

/**
 * @author Julien, Frantz
 * Gestion de l'envoi des donn�es au serveur
 */
public class Emission implements Runnable {
	
	private PrintWriter out;
	private ModeleJeu jeu;
	private int cachePosRaquette;
	
	public Emission(PrintWriter out, ModeleJeu jeu) {
		this.out = out;
		this.jeu = jeu;
		cachePosRaquette = 0;
	}

	@Override
	public void run() {
		
		try {

			while(true){
			
				// R�cup�ration de la position actuelle de la raquette
				int posRaquette = jeu.getMainPlayer().getRaquette().getPosX();
				
				// Envoi de la position au serveur si celle-ci est diff�rente de la derni�re fois
				if(cachePosRaquette != posRaquette) {
					
					out.println("RAQUETTE_POSITION");
					out.flush();
					out.println(posRaquette);
					out.flush();
					
					
					cachePosRaquette = posRaquette;
					
				}

				Thread.sleep(10);
				
			} 
			
		} catch (InterruptedException e) {
				e.printStackTrace();
		}	
	}
}
