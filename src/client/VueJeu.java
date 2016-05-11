package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPanel;

/**
 * @author Yohann, Kévin
 * Vue générale dessinant tous les composants
 */
public class VueJeu extends JPanel implements SimpleChangeListener, MouseMotionListener {

	private ModeleJeu jeu;
	private VueRaquette vueMaRaquette;
	private VueBalle vueBalle;
	private Hashtable<Integer, VueRaquette> raquetteOthers = new Hashtable<>();

	public VueJeu(ModeleJeu jeu) {
		this.jeu = jeu;
		this.setPreferredSize(new Dimension(200, 300));
		vueMaRaquette = new VueRaquette(jeu.getMainPlayer().getRaquette(), true);
		vueBalle = new VueBalle(jeu.getBalle());
		jeu.getMainPlayer().getRaquette().addSimpleChangeListener(this);
		jeu.addSimpleChangeListener(this);
		jeu.getBalle().addSimpleChangeListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(52, 152, 219));
		g.fillRect(0, 0, PongGUI.LONGUEUR_FENETRE - 200, PongGUI.HAUTEUR_FENETRE - 40);
		g.setColor(new Color(0, 0, 0));
		vueMaRaquette.draw((Graphics2D) g);

		Enumeration<Integer> enumKey = raquetteOthers.keys();
		while (enumKey.hasMoreElements()) {
			Integer idPlayer = enumKey.nextElement();
			VueRaquette vr = raquetteOthers.get(idPlayer);
			vr.draw((Graphics2D) g);
		}
		vueBalle.draw((Graphics2D) g);
	}

	@Override
	public void stateChanged(Object source) {
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		jeu.getMainPlayer().getRaquette().setCenter(e.getX());
	}

	public void addVueRaquette(int idPlayer) {
		raquetteOthers.put(idPlayer, new VueRaquette(jeu.getPlayerById(idPlayer).getRaquette(), false));
		jeu.getPlayerById(idPlayer).getRaquette().addSimpleChangeListener(this);
	}

	public void removeVueRaquette(int idPlayer) {
		raquetteOthers.remove(idPlayer);
	}

}
