package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import client.Player;

/**
 * @author Julien, Frantz
 * 
 */
public class Server {

	private ServerSocket socketServer;
	public static final int PORT_SERVEUR = 5555;
	private boolean isRunning;
	private ArrayList<ClientProcessor> clients;
	private ThreadBalle threadballe;

	public Server() throws IOException {

		isRunning = true;
		clients = new ArrayList<ClientProcessor>();
		socketServer = new ServerSocket(PORT_SERVEUR);

	}

	public void start() throws IOException {

		threadballe = new ThreadBalle(this);
		// En attente de connexion client
		while (isRunning) {

			Socket socket = socketServer.accept();

			ClientProcessor cp = new ClientProcessor(socket, this);

			if (threadballe == null || !threadballe.isAlive()) {
				threadballe = new ThreadBalle(this);
				System.out.println("LANCEMENT THREAD BALLE");
				threadballe.start();
			}

			addClient(cp);
			cp.start();

			System.out.println("Il y a " + getNbPlayers() + " joueur(s) connect�(s)");

		}

		socketServer.close();

	}

	public void close() {
		isRunning = false;
		socketServer = null;
	}

	public synchronized void sendBalle() {
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).getOut().println("SEND_BALLE");
			clients.get(i).getOut().println(threadballe.getBalle().getPosX());
			clients.get(i).getOut().println(threadballe.getBalle().getPosY());
			clients.get(i).getOut().flush();
		}
	}

	private void newConnexion(int idPlayer) {
		for (int i = 0; i < clients.size(); i++) {
			if (idPlayer != clients.get(i).getIdPlayer()) {
				clients.get(i).getOut().println("NEW_PLAYER");
				clients.get(i).getOut().flush();
				clients.get(i).getOut().println(idPlayer);
				clients.get(i).getOut().flush();
				clients.get(i).getOut().println(getClient(idPlayer).getColor().getRed()+"-"+getClient(idPlayer).getColor().getGreen()+"-"+getClient(idPlayer).getColor().getBlue());
				clients.get(i).getOut().flush();
			}
		}
	}

	public synchronized void broadcastRaquettesPositions(int idPlayer, int positionRaquette) {

		for (int i = 0; i < clients.size(); i++) {

			// �mission du message � tous les clients sauf � celui qui a
			// envoy�
			// la position
			if (idPlayer != clients.get(i).getIdPlayer()) {
				clients.get(i).getOut().println("POSITION_RECUE");
				clients.get(i).getOut().flush();
				clients.get(i).getOut().println(idPlayer);
				clients.get(i).getOut().flush();
				clients.get(i).getOut().println(positionRaquette);
				clients.get(i).getOut().flush();
			}

		}

	}

	public synchronized void updateScore(int idPlayer) {

		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).getOut().println("UPDATE_SCORE");
			clients.get(i).getOut().flush();
			clients.get(i).getOut().println(idPlayer);
			clients.get(i).getOut().flush();
			clients.get(i).getOut().println(getClient(idPlayer).getScore());
			clients.get(i).getOut().flush();
		}

	}

	public ArrayList<ClientProcessor> getClients() {
		return clients;
	}

	public ClientProcessor getClient(int id) {

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getIdPlayer() == id) {
				return clients.get(i);
			}
		}
		return null;
	}

	public int getNbPlayers() {

		return clients.size();
	}

	public void addClient(ClientProcessor cp) {
		clients.add(cp);
		System.out.println("je vais broadcaster � tt le monde sauf � " + cp.getIdPlayer());
		newConnexion(cp.getIdPlayer());
	}

	public void removeClient(ClientProcessor cp) {
		for (int i = 0; i < clients.size(); i++) {

			if (cp.getIdPlayer() == clients.get(i).getIdPlayer()) {
				clients.remove(i);
			}
		}

		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).getOut().println("CLIENT_DISCONNECT");
			clients.get(i).getOut().flush();
			clients.get(i).getOut().println(cp.getIdPlayer());
			clients.get(i).getOut().flush();
		}

		if (getNbPlayers() == 0) {
			threadballe = null;

		}

	}

	public void updateTotalBalls() {
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).incrementTotalBalls();

		}
	}

	public void updateAllScores() {

		for (int i = 0; i < clients.size(); i++) {

			this.updateScore(clients.get(i).getIdPlayer());

		}

	}

	public void updateAllTotalBallsAndScores() {
		this.updateTotalBalls();
		this.updateAllScores();
	}

}
