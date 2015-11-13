package ihm.components;

import javax.swing.JProgressBar;

public class Shared_component {
	public JProgressBar _progressbar;// progressbar du loader lors du chargement des données de jeux
	public Txt txt_line1;// label d'info utilisateur du loader lors du chargement des données de jeux
	
	public Shared_component() {
		_progressbar = new JProgressBar();
		txt_line1 = new Txt();
	}

}
