package client;

import java.util.ArrayList;

public class Listenable {

	public ArrayList<SimpleChangeListener> listenTable;

	public Listenable() {

		listenTable = new ArrayList<SimpleChangeListener>();
	}

	public void addSimpleChangeListener(SimpleChangeListener l) {

		listenTable.add(l);

	}

	public void removeSimpleChangeListener(SimpleChangeListener l) {

		listenTable.remove(l);

	}

	protected void fireSimpleChange() {

		for (int i = 0; i < listenTable.size(); i++) {

			listenTable.get(i).stateChanged(this);
		}

	}

}
