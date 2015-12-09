package utils;

public class Joueur {
	
	private String _pseudo;
	private int _points,_vies;
	
	public Joueur(String pseudo) {
		construct(pseudo, 0);
	}
	
	public Joueur() {
		construct("Player", 0);
	}
	
	private void construct(String pseudo,int point){
		if(pseudo != "") 
			_pseudo = pseudo;
		else
			_pseudo = "unknown";	
		_points = point;	
	}
	
	public void setPseudo(String pseudo){
		if(pseudo != "") {
			_pseudo = pseudo;
		}
	}
	public String getPseudo(){
		return _pseudo;
	}
	
	public void addPoint(int point){
		_points += Math.abs(point);
	}
	public void setPoint(int point){
		_points = Math.abs(point);
	}
	
	public int getPoint(){
		return _points;
	}

}
