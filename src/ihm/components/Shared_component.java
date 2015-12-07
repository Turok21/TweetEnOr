package ihm.components;

import javax.swing.JProgressBar;

public class Shared_component {
	public JProgressBar _progressbar;// progressbar du loader lors du chargement des données de jeux
	public Txt txt_line1;// label d'info utilisateur du loader lors du chargement des données de jeux
	
	public boolean _is_message;
	
	
	public Shared_component() {
		_progressbar = new JProgressBar();
		txt_line1 = new Txt();
		_is_message = false;
	}

	public enum LEVEL {
		  EASY(15),
		  MEDIUM(10),
		  HARD(8);  
  	  private int value = 10;
  	  LEVEL(int val){
  	    this.value = val;
  	  }
  	  
  	  public int getvalue(){
  		  return value;
  	  }

	};
}
