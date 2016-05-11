package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Julien, Frantz 
 * Gestion de la réception des données côté server,
 * envoyées par les clients
 */
public class Reception implements Runnable {

	private Socket socket;
	private Server server;
	private int idPlayer;
	private BufferedReader in;

	public Reception(Socket socket, BufferedReader in, Server server, int idPlayer) {
		this.socket = socket;
		this.server = server;
		this.idPlayer = idPlayer;
		this.in = in;
	}

	@Override
	public void run() {

		while (true) {

			String response;
			try {
				response = read();

				switch (response) {

				case "RAQUETTE_POSITION":
					int positionRaquette = Integer.parseInt(in.readLine());
					ClientProcessor c = server.getClient(idPlayer);

					if (positionRaquette == -999999) {
						server.removeClient(c);
					} else {
						c.setPosX(positionRaquette);
						server.broadcastRaquettesPositions(idPlayer, positionRaquette);
					}
					break;
				default:

					break;
				}

				Thread.sleep(10);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String read() throws IOException {
		String messageDistant = "";
		try {
			messageDistant = in.readLine();
		} catch (IOException e) {
			messageDistant = "";
		}
		return messageDistant;
	}

}
