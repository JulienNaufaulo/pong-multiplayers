package client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Julien, Frantz Gestion de la réception des données envoyées par le
 *         serveur
 */
public class Reception implements Runnable {

	private BufferedReader in;
	private ModeleJeu jeu;
	private VueJeu vueJeu;

	public Reception(BufferedReader in, ModeleJeu jeu, VueJeu vueJeu) {
		this.in = in;
		this.jeu = jeu;
		this.vueJeu = vueJeu;
	}

	@Override
	public void run() {

		String response;

		while (true) {

			try {

				response = read();

				switch (response) {
				case "GET_MY_ID":
					int myId = Integer.parseInt(in.readLine());
					jeu.setMainPlayer(myId);
					break;

				case "GET_MY_COLOR":
					Color color = createColor(in.readLine());
					jeu.getMainPlayer().setCouleur(color);
					break;

				case "LIST_PLAYERS":
					int nbPlayers = Integer.parseInt(in.readLine());
					for (int i = 0; i < nbPlayers; i++) {
						int idPlayer = Integer.parseInt(in.readLine());
						int score = Integer.parseInt(in.readLine());
						Color couleur = createColor(in.readLine());
						System.out.println(couleur);
						jeu.addPlayer(new Player(idPlayer));
						jeu.getPlayerById(idPlayer).setScore(score);
						jeu.getPlayerById(idPlayer).setCouleur(couleur);
						vueJeu.addVueRaquette(idPlayer);
					}
					break;
				case "POSITION_RECUE":
					int idPlayer = Integer.parseInt(in.readLine());
					int positionRaquette = Integer.parseInt(in.readLine());

					jeu.getPlayerById(idPlayer).getRaquette().setPosX(positionRaquette);
					break;
					
				case "NEW_PLAYER":
					int idNewPlayer = Integer.parseInt(in.readLine());
					Player newPlayer = new Player(idNewPlayer);
					jeu.addPlayer(newPlayer);
					Color couleur = createColor(in.readLine());
					jeu.getPlayerById(idNewPlayer).setCouleur(couleur);
					vueJeu.addVueRaquette(idNewPlayer);
					break;

				case "SEND_BALLE":
					String x = in.readLine();
					this.jeu.getBalle().setPosX(Integer.parseInt(x));
					String y = in.readLine();
					this.jeu.getBalle().setPosY(Integer.parseInt(y));
					break;

				case "UPDATE_SCORE":
					String idp = in.readLine();
					String score = in.readLine();
					jeu.getPlayerById(Integer.parseInt(idp)).setScore(Integer.parseInt(score));
					break;

				case "CLIENT_DISCONNECT":
					int idp1 = Integer.parseInt(in.readLine());
					vueJeu.removeVueRaquette(idp1);
					jeu.removePlayer(idp1);
					break;

				default:
					String toSend = "Commande inconnu !";
					break;
				}
			}

			catch (IOException e) {
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

	private Color createColor(String data) {

		String[] couleur = data.split("-");
		System.out.println(data);
		return new Color(Integer.parseInt(couleur[0]), Integer.parseInt(couleur[1]), Integer.parseInt(couleur[2]));
	}

}
