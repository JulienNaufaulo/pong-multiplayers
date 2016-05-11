package client;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * @author Yohann, Julien 
 * Classe permettant de transformer la liste des joueurs
 * en un tableau des scores
 */
public class PlayersToTableAdapter extends AbstractTableModel implements SimpleChangeListener {

	private ModeleJeu jeu;
	private static final int NB_COLUMNS = 2;
	private Object[] columnNames = { "ID Player", "Score" };

	public PlayersToTableAdapter(ModeleJeu jeu) {

		this.jeu = jeu;
		this.jeu.addSimpleChangeListener(this);

	}

	@Override
	public int getColumnCount() {

		return NB_COLUMNS;
	}

	@Override
	public int getRowCount() {

		return jeu.getAllPlayers().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		int rouge = jeu.getAllPlayers().get(row).getCouleur().getRed();
		int vert = jeu.getAllPlayers().get(row).getCouleur().getGreen();
		int bleu = jeu.getAllPlayers().get(row).getCouleur().getBlue();

		switch (col) {

		case 0:
			return "<html><div style='color:rgb("+rouge+","+vert+","+bleu+")'> Joueur " + jeu.getAllPlayers().get(row).getId()+"</div></html>";

		case 1:
			return "<html><div style='color:rgb("+rouge+","+vert+","+bleu+")'>"+jeu.getAllPlayers().get(row).getScore() + "%</div></html>";

		default:
			return null;
		}

	}

	public String getColumnName(int column) {

		return (String) columnNames[column];
	}

	@Override
	public void stateChanged(Object source) {

		this.fireTableDataChanged();

	}

}
