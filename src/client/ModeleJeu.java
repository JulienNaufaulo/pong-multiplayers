package client;

import java.util.ArrayList;

/**
 * @author Yohann, Kévin
 * Modele contenant les éléments nécessaires au fonctionnement du jeu
 */
public class ModeleJeu extends Listenable implements SimpleChangeListener {

	private Player mainPlayer;
	private ArrayList<Player> players;
	private ArrayList<Player> allPlayers;
	private Balle balle;

	public ModeleJeu() {
		players = new ArrayList<Player>();

		mainPlayer = new Player(0);
		mainPlayer.addSimpleChangeListener(this);

		allPlayers = new ArrayList<Player>();
		allPlayers.add(mainPlayer);
		balle = new Balle();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Player> getAllPlayers() {

		return allPlayers;
	}

	public Player getMainPlayer() {
		return mainPlayer;
	}

	public Player getPlayerById(int idPlayer) {

		for (int i = 0; i < allPlayers.size(); i++) {
			if (allPlayers.get(i).getId() == idPlayer) {
				return allPlayers.get(i);
			}
		}
		return null;
	}

	public void setMainPlayer(int idPlayer) {
		mainPlayer.setId(idPlayer);
		this.fireSimpleChange();
	}

	public void addPlayer(Player player) {
		players.add(player);
		player.addSimpleChangeListener(this);
		allPlayers.add(player);
		this.fireSimpleChange();
	}

	public void removePlayer(int idPlayer) {
		for (int i = 0; i < allPlayers.size(); i++) {
			if (allPlayers.get(i).getId() == idPlayer) {
				allPlayers.remove(i);
			}
		}
		this.fireSimpleChange();
	}

	public Balle getBalle() {
		return balle;
	}

	@Override
	public void stateChanged(Object source) {

		this.fireSimpleChange();
	}

}
