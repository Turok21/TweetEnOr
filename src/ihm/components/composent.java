package ihm.components;

/**
 * Interface implémenter dans tout les composent de la surcouche de swing
 * La particulariter de cette surcouche est quelle place les composent en pourcentage par raport a la taille de l'ecran
 * @author Guilhem Eyraud
 *
 */
public interface composent {
	
	/**
	 * enumère les diffirent centre de graviter utilisable pour le placement des composent 
	 * exemple : CENTER utilisera le centre du composent pour calculer les coordonée donc:
	 * 	 un composent avec x=0,y=0 et TOP_LEFT sera parfaitement position dans le coin en haut a gauche  mais
	 * 	 un composent avec x=0,y=0 et CENTER sera couper et on ne verra que sa partie en bas a droite 
	 * @author Guilhem Eyraud
	 *
	 */
	public enum GRAVITY {
		  TOP_LEFT,
		  TOP_RIGHT,
		  CENTER;  
		  //BOTTOM_LEFT,
		  //BOTTOM_RIGHT;
	};
	
	/**
	 * définie le centre de gravite
	 * @param flag
	 */
	void setGravity(GRAVITY flag);
	/**
	 * resize le composent en fonction de ce quil contient , si il ne contient rien il auras une taille de 0,0
	 */
	void auto_resize();

	/**
	 * définie la possition du composent en pourcentage dans une largeur et hauteur donnée
	 * @param x
	 * @param y
	 * @param in_w
	 * @param in_h
	 */
	public void setxyin(float x,float y,int in_w,int in_h);
	/**
	 * définie la possition du composent en pourcentage dans la largeur et hauteur de l'ecran
	 * @param x
	 * @param y
	 */
	public void setxy(float x,float y);
	/**
	 * définie la possition du composent en pourcentage dans la largeur et hauteur de l'ecran
	 * @param x
	 */
	public void setx(float x);
	/**
	 * définie la possition du composent en pourcentage dans la largeur et hauteur de l'ecran
	 * @param y
	 */
	public void sety(float y);
	
	/**
	 * définie la taille du composent
	 * @param w
	 * @param h
	 */
	public void setwh(float w,float h);
	/**
	 * définie la taille du composent
	 * @param w
	 */
	public void setw(float w);
	/**
	 * définie la taille du composent
	 * @param h
	 */
	public void seth(float h);
	
	/***************** ACCESSEUR *****************/
	public float getx();
	public float gety();
	public float geth();
	public float getw();

}
