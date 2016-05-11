package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

/**
 * @author Yohann, Kévin
 * dessin de l'Objet Balle
 */
public class VueBalle implements GameComponent {

	private Balle balle;

	public VueBalle(Balle balle) {

		this.balle = balle;

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(new Color(0,0,0));
		g.fillOval(this.balle.getPosX(), this.balle.getPosY(), this.balle.TAILLE_BALLE, this.balle.TAILLE_BALLE);

	}

}
