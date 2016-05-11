package client;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Yohann, Kévin
 * Vue de la raquette
 */
public class VueRaquette implements GameComponent {

	private Raquette raquette;
	private boolean myRaquette;

	public VueRaquette(Raquette r, boolean myRaquette) {

		raquette = r;
		this.myRaquette = myRaquette;
	}

	@Override
	public void draw(Graphics2D g) {

		if (this.myRaquette) {
			g.setColor(raquette.getCouleur());

		} else {
			//System.out.println(raquette.getCouleur());
			g.setColor(new Color(raquette.getCouleur().getRed(), raquette.getCouleur().getGreen(), raquette.getCouleur().getBlue(), 120));

		}
		g.fillRect(raquette.getPosX(), raquette.getPosY() - 40,
				Raquette.LONGUEUR, Raquette.HAUTEUR);

	}
}
