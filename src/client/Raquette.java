package client;

import java.awt.Color;

/**
 * @author Yohann, Kévin
 * Modèle de la raquette
 */
public class Raquette extends Listenable {

	public static final int LONGUEUR = 150;
	public static final int HAUTEUR = 20;
	private int posX;
	private int posY;
	private int centre;
	private Color couleur;

	public Raquette() {
		posX = PongGUI.LONGUEUR_FENETRE / 2 - LONGUEUR / 2;
		posY = PongGUI.HAUTEUR_FENETRE - HAUTEUR;
		centre = posX + (LONGUEUR / 2);
		
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int posX) {
		this.posX = posX;
		this.fireSimpleChange();
	}

	public void setPosY(int posY) {
		this.posY = posY;
		this.fireSimpleChange();
	}

	public void setCenter(int centre) {
		this.posX = centre - (LONGUEUR / 2);
		this.fireSimpleChange();
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

}
