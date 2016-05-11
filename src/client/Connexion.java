package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Julien, Frantz
 * Thread de mise en place des classes d'émission et de réception de données du serveur
 */
public class Connexion implements Runnable {
	
	private Socket socket;
	private ModeleJeu jeu;
	private VueJeu vueJeu;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public Connexion(Socket socket, ModeleJeu jeu, VueJeu vueJeu) throws IOException {
		this.socket = socket;
		this.jeu = jeu;
		this.vueJeu = vueJeu;
	}

	@Override
	public void run() {
		
		try {
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new PrintWriter(socket.getOutputStream());
	
			// Thread réception de messages
			Thread reception = new Thread(new Reception(in, jeu, vueJeu));
			reception.start();
				
			// Thread d'emission de messages
			Thread emission = new Thread(new Emission(out, jeu));
			emission.start();
	            
		} catch (IOException e) {
			e.printStackTrace();
		}
              
	}
       
}