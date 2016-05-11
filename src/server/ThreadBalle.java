package server;

import client.*;


/**
 * @author Yohann, Julien
 * Gestion de la balle coté serveur
 */
public class ThreadBalle extends Thread {

	private Balle balle;
	private Server serveur;
	private boolean moving;

	public ThreadBalle(Server s) {
		balle = new Balle(PongGUI.LONGUEUR_TERRAIN / 2 - Balle.TAILLE_BALLE / 2, 200);
		serveur = s;
		this.moving = true;
	}

	public Balle getBalle() {
		return balle;
	}

	@Override
	public void run() {
		while (moving) {
			try {

				if (this.balle.getPosX() >= (PongGUI.LONGUEUR_TERRAIN - Balle.TAILLE_BALLE)) { // mur
																								// gauche

					int diff = PongGUI.LONGUEUR_TERRAIN - this.balle.getPosX();
					System.out.println("diff : " + diff);

					this.balle.rebond('x');

				} else if (this.balle.getPosX() <= 0) { // mur droit

					this.balle.rebond('x');

				} else if (this.balle.getPosY() <= 0) { // mur haut

					System.out.println("Mur haut " + this.balle.getVy());
					this.balle.rebond('y');

				} else if (this.balle.getPosY() >= (PongGUI.HAUTEUR_TERRAIN - Balle.TAILLE_BALLE - Raquette.HAUTEUR)) { // Raquette

					for (ClientProcessor clientProcessor : this.serveur.getClients()) {

						int posraquetteclient = clientProcessor.getPosX();
						int id = clientProcessor.getIdPlayer();

						if (this.balle.getPosMidX() >= posraquetteclient
								&& this.balle.getPosMidX() <= (posraquetteclient + Raquette.LONGUEUR)) {

							ClientProcessor player = serveur.getClient(id);
							player.incrementPoints();
							serveur.updateAllTotalBallsAndScores();
							this.balle.rebondRaquette(this.balle.getPosMidX() - posraquetteclient);
						}

					}

					if (this.balle.getPosY() >= (PongGUI.HAUTEUR_TERRAIN - Balle.TAILLE_BALLE)) {
						// mur bas (balle perdue)

						serveur.updateAllTotalBallsAndScores();
						this.balle.initPos();

					}

				}
				this.balle.move();

				serveur.sendBalle();

				Thread.sleep(10);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}
