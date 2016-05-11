package client;

import java.awt.Color;

/**
 * @author Yohann, Julien 
 * Modele du joueur
 */
public class Player extends Listenable {
	private int id;
	private int score;
	private Raquette raquette;
	private Color couleur;

	public Player(int id) {
		this.id = id;
		score = 0;
		this.couleur = new Color(150,150,150);
		raquette = new Raquette();
	}

	public int getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Raquette getRaquette() {
		return raquette;
	}

	public void setScore(int score) {
		this.score = score;
		this.fireSimpleChange();
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		
		this.couleur = couleur;
		this.getRaquette().setCouleur(couleur);
		this.fireSimpleChange();
	}

}
