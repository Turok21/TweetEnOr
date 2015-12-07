package ihm.components;

import java.util.HashMap;

import javax.swing.JProgressBar;

import reseaux.DataType;



public class Shared_component {
	public JProgressBar _progressbar;// progressbar du loader lors du chargement des données de jeux
	public Txt txt_line1;// label d'info utilisateur du loader lors du chargement des données de jeux
	public boolean is_message;
	public DataType _datatype; 
	public HashMap<DataType , Object> data_hash;
	
	
	public Shared_component() {
		_progressbar = new JProgressBar();
		txt_line1 = new Txt();
	}

}
