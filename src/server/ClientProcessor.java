package server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

import client.Player;

/**
 * @author Julien, Frantz
 * 
 */
public class ClientProcessor extends Thread {

	private int idPlayer;
	private int posX;
	private Socket socket;
	private Server server;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private int score;
	private int points;
	private int totalballs;
	private Color color;

	private Thread reception;

	public ClientProcessor(Socket socket, Server server) throws UnsupportedEncodingException, IOException {
		this.socket = socket;
		this.server = server;
		this.posX = 0;
		this.score = 0;
		this.points = 0;
		this.totalballs = 0;
		this.idPlayer = server.getNbPlayers() + 1;
		this.color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		out = new PrintWriter(socket.getOutputStream());

		out.println("GET_MY_ID");
		out.flush();
		out.println(idPlayer);
		out.flush();
		out.println("GET_MY_COLOR");
		out.flush();
		out.println(color.getRed()+"-"+color.getGreen()+"-"+color.getBlue());
		out.flush();
		
		out.println("LIST_PLAYERS");
		out.flush();
		out.println(server.getNbPlayers());
		out.flush();
		for (int i = 0; i < server.getNbPlayers(); i++) {
			ClientProcessor cp = server.getClients().get(i);
			if (cp.getIdPlayer() != idPlayer) {
				out.println(cp.getIdPlayer());
				out.flush();
				out.println(cp.getScore());
				out.flush();
				out.println(cp.getColor().getRed()+"-"+cp.getColor().getGreen()+"-"+cp.getColor().getBlue());
				out.flush();
			}
		}
	}

	@Override
	public void run() {
		reception = new Thread(new Reception(socket, in, server, idPlayer));
		reception.start();
	}

	public PrintWriter getOut() {
		return out;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public synchronized int getScore() {

		if (this.totalballs == 0) {
			return 0;
		}
		int lescore = (this.points * 100) / this.totalballs;
		System.out.println("LE SCORE DU JOUEUR " + lescore);
		return lescore;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}

	public synchronized void incrementScore() {
		this.score += server.getNbPlayers();
	}

	public synchronized void incrementPoints() {
		this.points += server.getNbPlayers();
	}

	public synchronized void incrementTotalBalls() {
		this.totalballs += server.getNbPlayers();
	}

	public int getPosX() {
		return posX;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}	

}
