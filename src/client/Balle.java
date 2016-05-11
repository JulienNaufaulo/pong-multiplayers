package client;

/**
 * @author Yohann, Kevin 
 * Modèle de la balle
 */
public class Balle extends Listenable {
	private int posX;
	private int posY;
	public static final int TAILLE_BALLE = 40;
	public static final int BASE_VITESSE = 4;
	private int vitesse;
	private double vx;
	private double vy;
	private int alpha;

	public Balle(int posX, int posY) {

		this.posX = posX - Balle.TAILLE_BALLE / 2;
		this.posY = posY - Balle.TAILLE_BALLE / 2;
		this.alpha = (int) (Math.random() * 360);
		this.vitesse = 0;
		this.vx = Math.cos(Math.toRadians(alpha)) * BASE_VITESSE;
		this.vy = Math.sin(Math.toRadians(alpha)) * BASE_VITESSE;

	}

	public Balle() {
		this(0, 0);
	}

	public synchronized int getPosX() {

		return this.posX;
	}

	public synchronized int getPosY() {

		return this.posY;
	}

	public synchronized void setPosX(int posX) {

		this.posX = posX;
		this.fireSimpleChange();
	}

	public synchronized void setPosY(int posY) {

		this.posY = posY;
		this.fireSimpleChange();
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public synchronized int getAlpha() {

		return this.alpha;
	}

	public synchronized void move() {

		this.posX += Math.round(this.vx);
		this.posY += Math.round(this.vy);
	}

	public synchronized int getPosMidX() {

		return (this.posX + Balle.TAILLE_BALLE / 2);
	}

	public void rebond(char c) {

		switch (c) {

		case 'x':
			this.vx = -vx;
			break;
		case 'y':
			this.vy = -vy;
			break;
		default:
			break;
		}

	}

	public void rebondRaquette(int posBalle) {

		vitesse++;
		int degree = (posBalle*180) / Raquette.LONGUEUR;
		this.vy = -(Math.sin(Math.toRadians(degree)) * (BASE_VITESSE+vitesse));
		this.vx = -(Math.cos(Math.toRadians(degree)) * (BASE_VITESSE+vitesse));
	}

	public void initPos() {

		this.posX = PongGUI.LONGUEUR_TERRAIN / 2 - Balle.TAILLE_BALLE / 2;
		this.posY = 200;
		this.vitesse = 0;

	}

}
