package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * @author Yohann, Frantz
 * 
 */
public class PongGUI extends JFrame {

	public static final int LONGUEUR_FENETRE = 1000;
	public static final int HAUTEUR_FENETRE = 700;
	public static final int LARGEUR_SCORE = 200;
	public static final int LONGUEUR_TERRAIN = LONGUEUR_FENETRE - LARGEUR_SCORE;
	public static final int HAUTEUR_TERRAIN = HAUTEUR_FENETRE - 40;
	public ModeleJeu jeu;

	public PongGUI(ModeleJeu jeu, VueJeu vueJeu) {
		setTitle("Pong");
		this.jeu = jeu;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane jsp = new JScrollPane(new JTable(new PlayersToTableAdapter(jeu)));
		jsp.setPreferredSize(new Dimension(LARGEUR_SCORE, 700));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(vueJeu, BorderLayout.CENTER);
		getContentPane().add(jsp, BorderLayout.WEST);

		setSize(LONGUEUR_FENETRE, HAUTEUR_FENETRE);
		setVisible(true);
		
	}

	protected void processWindowEvent(WindowEvent e) {

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {

			int exit = JOptionPane.showConfirmDialog(this, "Êtes-vous sur de vouloir quitter la partie ?");
			if (exit == JOptionPane.YES_OPTION) {
				this.jeu.getMainPlayer().getRaquette().setPosX(-999999);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				System.exit(0);
			}

		} else {

			super.processWindowEvent(e);
		}
	}

}
