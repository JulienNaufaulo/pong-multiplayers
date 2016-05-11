package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe Main côté client
 * 
 */
public class Main {

	public static void main(String[] args) {
			createClient();
			
	}
	
	public static void createClient()
	{	
		Socket socket;
		Thread threadClient;
		try {
			socket = new Socket(InetAddress.getLocalHost(), 5555);

			ModeleJeu jeu = new ModeleJeu();
			VueJeu vueJeu = new VueJeu(jeu);
			new PongGUI(jeu, vueJeu);

			threadClient = new Thread(new Connexion(socket, jeu, vueJeu));
			threadClient.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
