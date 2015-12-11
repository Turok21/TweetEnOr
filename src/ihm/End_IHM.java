package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.lang.IllegalStateException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Shared_component;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import utils.Joueur;
import utils.TweetWord;
import Sounds.Player;

/**
 * 
 * @author Azali
 *
 */
public class End_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // Serelisation Id 
	private Bt _b_again;  // restart button
	private String _hastag,_pseudo_adversaire,_pseudo; // hashtag used for the game
	private int _points,_points_adversaire; // nb point gained

	public static void main(String[] args){		
		CtrlTweetEnOr verifier;
		try {
			verifier = new CtrlTweetEnOr("Microsoft", new Shared_component());
			ArrayList<TweetWord> listword = (ArrayList<TweetWord>) verifier.getListWords();
			//new End_IHM(new JFrame(""),0,listword,"Microsoft",666);
			Joueur j1 = new Joueur("Guilhem");
			Joueur j2 = new Joueur("Margaux");
			j1.addPoint(20);
			j2.addPoint(0);
			new End_IHM(new JFrame(""),verifier,j1,j2);
		} catch (Exception e) {
			if(e instanceof IllegalStateException){
				System.out.println("sdffffffffff");
			}
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 
	 * @param fram the maine JFrame
	 * @param fin did She win (1) or loose(0)
	 * @param listword liste of the word she had to found
	 * @param hastag hashtag played
	 * @param point nb point gained
	 */
	public End_IHM(JFrame fram,int fin,ArrayList<TweetWord> listword,String hastag,int point) {
		super();
		_hastag = hastag;
		_points = point;
		
		if(fin == 0){
			loose_screen(fram, listword);
		}else{
			win_screen(fram, listword);
		}
	    
	   show_windows();
	   // displaying different songs 
	   if(fin == 0){
			new Thread(new Runnable() {
				public void run() {
					Player player = new Player();
					player.playPerdu();
				}
			}).start();
	    	 
		}else{
			new Thread(new Runnable() {
				public void run() {
					Player player = new Player();
					player.playOhYeah();
				}
			}).start();
		}

	   
	}
	
	/**
	 * 
	 * @param fram the maine JFrame
	 * @param fin did he win (1) or loose(0)
	 * @param listword liste of the word she had to found
	 * @param hastag hashtag played
	 * @param point nb point gained
	 */
	public End_IHM(JFrame fram, CtrlTweetEnOr cteo,Joueur local,Joueur distant) {
		super();
		_pseudo_adversaire = distant.getPseudo();
		_points_adversaire = distant.getPoint();
		_pseudo = local.getPseudo();
		_points = local.getPoint();
		_hastag = cteo.getWord();
		
		int fin = 0;
		
		if(_points < _points_adversaire){
			loose_screen_multi(fram, (ArrayList<TweetWord>) cteo.getListWords());
			fin = 0;
		}else if(_points == _points_adversaire){
			equality_screen_multi(fram, (ArrayList<TweetWord>) cteo.getListWords());
			fin = 2;
		}else{
			win_screen_multi(fram, (ArrayList<TweetWord>) cteo.getListWords());
			fin = 1;
		}
	    
	   show_windows();
	   // displaying different songs 
	   if(fin == 0){
			new Thread(new Runnable() {
				public void run() {
					Player player = new Player();
					player.playPerdu();
				}
			}).start();
	    	 
		}else{
			new Thread(new Runnable() {
				public void run() {
					Player player = new Player();
					player.playOhYeah();
				}
			}).start();
		}

	   
	}

	/**
	 * 
	 * @param fram the main JFrame
	 * @param fin she loose
	 * @param listword  listword liste of the word she had to found
	 */
	private void loose_screen(JFrame fram,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Fail.jpg",fram, false);
		_fenetre.addKeyListener(this);
	    
	    
	    
	    //displaying the hashtag
		Txt hash = new Txt("#"+_hastag);
		hash.setGravity(GRAVITY.CENTER_LEFT);
        hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
        hash.setForeground(new Color(140, 0, 0 ,255));
        hash.auto_resize();
        hash.setxy(5, 12);
        _jp_principal.add(hash);
        
        
     // displaying the points she gained 
        Txt point = new Txt("Score : "+_points);
        point.setGravity(GRAVITY.CENTER_RIGHT);
        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
        point.setForeground(new Color(140, 0, 0 ,255));
        point.auto_resize();
        point.setxy(95, 12);
        _jp_principal.add(point);
        
	    
	    int k = 0;
		for(TweetWord word : listword){		
		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord());
			txt.setForeground(new Color(87, 1, 8 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();	        
	        
	        if(k%2 == 0){
	        	txt.setGravity(GRAVITY.CENTER_RIGHT);
	        	txt.setxy(20, 22+(9*k));
	        }else{
	        	txt.setGravity(GRAVITY.CENTER_LEFT);
	        	txt.setxy(78, 22+(9*(k-1)));
	        }
	        k++;
			_jp_principal.add(txt);

			
		}
	    
		// restart button
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setFont(arista_btn.deriveFont(65));
		_b_again.auto_resize();
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy((float)49.5,80);
		_jp_principal.add(_b_again);
			
	}

	/**
	 * 
	 * @param fram the main JFrame
	 * @param fin she wan
	 * @param listword liste of all xord
	 */	
	private void win_screen(JFrame fram,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Win.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
		
		// displaying fancy images
		Txt imgGagne = new Txt(new ImageIcon("./data/images/gagne.png"));
        imgGagne.setGravity(GRAVITY.CENTER);
        imgGagne.setxy(50, 12);
        imgGagne.auto_resize();
        _jp_principal.add(imgGagne);

        //displaying the hashtag
        Txt hash = new Txt("#"+_hastag);
        hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
        hash.setForeground(new Color(255, 209, 0 ,255));
        hash.auto_resize();
        hash.setxy(50, 24);
        _jp_principal.add(hash);
        
     // displaying the points she gained 
        Txt point = new Txt("Score : "+_points);
        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
        point.setForeground(new Color(255, 209, 0 ,255));
        point.auto_resize();
        point.setxy(50, 34);
        _jp_principal.add(point);
        
        	

	    int k = 1;
	    float lastX = 10;
	    float lastY = 75;
		for(TweetWord word : listword){		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord());
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,40));
			txt.setForeground(new Color(242, 209, 0 ,255));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();
			txt.setxy(lastX, lastY);
			// absolute positioning of all word
			lastX += 9;
			if(k < 5  )
				lastY -= 7;
			if( k > 5 )
				lastY += 7;
		
			_jp_principal.add(txt);
			k++;
		}
		// restart button
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,40));
		_b_again.setText("Recommencer");
		_b_again.setGravity(GRAVITY.TOP_RIGHT);
		_b_again.setxy(98,90);
		_jp_principal.add(_b_again);
		
		
	    
	}

	
	/**
	 * 
	 * @param fram the main JFrame
	 * @param fin she loose
	 * @param listword  listword liste of the word she had to found
	 */
	private void loose_screen_multi(JFrame fram,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_fail_reseau.jpg",fram, false);
		_fenetre.addKeyListener(this);
	    
	    
		//displaying J1
		Txt pseudoLocal = new Txt(_pseudo);
		pseudoLocal.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
		pseudoLocal.setForeground(new Color(255, 255, 255 ,255));
		pseudoLocal.auto_resize();
		pseudoLocal.setxy(10, 12);
        _jp_principal.add(pseudoLocal);
        
     // displaying the points  
        Txt point = new Txt("Score : "+_points);
        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        point.setForeground(new Color(255, 255, 255 ,255));
        point.auto_resize();
        point.setxy(25, 12);
        _jp_principal.add(point);
        
	    //displaying the hashtag
	    Txt hash = new Txt("#"+_hastag);
	    hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
	    hash.setForeground(new Color(36, 78, 124 ,255));
	    hash.auto_resize();
	    hash.setxy(50, 12);
	    _jp_principal.add(hash);
        
        // displayin j2
        Txt pseudoAdversaire = new Txt(_pseudo_adversaire);
        pseudoAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pseudoAdversaire.setForeground(new Color(255, 209, 0 ,255));
        pseudoAdversaire.auto_resize();
        pseudoAdversaire.setxy(75, 12);
        _jp_principal.add(pseudoAdversaire);
        
        // displaying the points  
        Txt pointAdversaire = new Txt("Score : "+_points_adversaire);
        pointAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pointAdversaire.setForeground(new Color(255, 209, 0 ,255));
        pointAdversaire.auto_resize();
        pointAdversaire.setxy(90, 12);
        _jp_principal.add(pointAdversaire);
        
      //displaying J1
  		Txt pseudoLocalFun = new Txt(_pseudo);
  		pseudoLocalFun.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
  		pseudoLocalFun.setForeground(new Color(255, 255, 255 ,255));
  		pseudoLocalFun.auto_resize();
  		pseudoLocalFun.setxy(35, 57);
        _jp_principal.add(pseudoLocalFun);
          
        //displaying J1
  		Txt pseudoAdversaireFun = new Txt(_pseudo_adversaire);
  		pseudoAdversaireFun.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
  		pseudoAdversaireFun.setForeground(new Color(255, 255, 255 ,255));
  		pseudoAdversaireFun.auto_resize();
  		pseudoAdversaireFun.setxy(68, 58);
        _jp_principal.add(pseudoAdversaireFun);
              
	    int k = 0;
		for(TweetWord word : listword){		
		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord() );
			txt.setForeground(new Color(87, 1, 8 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();	        
	        
	        if(k%2 == 0){
	        	txt.setGravity(GRAVITY.CENTER_RIGHT);
	        	txt.setxy(20, 22+(8*k));
	        }else{
	        	txt.setGravity(GRAVITY.CENTER_LEFT);
	        	txt.setxy(80, 22+(8*(k-1)));
	        }
	        k++;
			_jp_principal.add(txt);

		}
	    
		// restart button
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setFont(arista_btn.deriveFont(65));
		_b_again.auto_resize();
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy((float)49.5,80);
		_jp_principal.add(_b_again);
			
	}

	/**
	 * 
	 * @param fram the main JFrame
	 * @param fin she loose
	 * @param listword  listword liste of the word she had to found
	 */
	private void equality_screen_multi(JFrame fram,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_reseau_egalite.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
			
	    
		//displaying J1
		Txt pseudoLocal = new Txt(_pseudo);
		pseudoLocal.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
		pseudoLocal.setForeground(new Color(255, 255, 255 ,255));
		pseudoLocal.auto_resize();
		pseudoLocal.setxy(10, 12);
        _jp_principal.add(pseudoLocal);
        
     // displaying the points  
        Txt point = new Txt("Score : "+_points);
        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        point.setForeground(new Color(255, 255, 255 ,255));
        point.auto_resize();
        point.setxy(25, 12);
        _jp_principal.add(point);
        
	    //displaying the hashtag
	    Txt hash = new Txt("#"+_hastag);
	    hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
	    hash.setForeground(new Color(36, 78, 124 ,255));
	    hash.auto_resize();
	    hash.setxy(50, 12);
	    _jp_principal.add(hash);
        
        // displayin j2
        Txt pseudoAdversaire = new Txt(_pseudo_adversaire);
        pseudoAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pseudoAdversaire.setForeground(new Color(255, 255, 255 ,255));
        pseudoAdversaire.auto_resize();
        pseudoAdversaire.setxy(75, 12);
        _jp_principal.add(pseudoAdversaire);
        
        // displaying the points  
        Txt pointAdversaire = new Txt("Score : "+_points_adversaire);
        pointAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pointAdversaire.setForeground(new Color(255, 255, 255 ,255));
        pointAdversaire.auto_resize();
        pointAdversaire.setxy(90, 12);
        _jp_principal.add(pointAdversaire);
        
        int k1 = 0;
        for(TweetWord word : listword){		
    		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord() );
			txt.setForeground(new Color(87, 1, 8 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();	        
	        
	        if(k1%2 == 0){
	        	txt.setGravity(GRAVITY.CENTER_RIGHT);
	        	txt.setxy(18, 22+(8*k1));
	        }else{
	        	txt.setGravity(GRAVITY.CENTER_LEFT);
	        	txt.setxy(82, 22+(8*(k1-1)));
	        }
	        k1++;
			_jp_principal.add(txt);

		}
			
		
	    
		// restart button
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setFont(arista_btn.deriveFont(65));
		_b_again.auto_resize();
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy((float)49.5,80);
		_jp_principal.add(_b_again);
			
	}

	
	/**
	 * 
	 * @param fram the main JFrame
	 * @param fin she wan
	 * @param listword liste of all xord
	 */
	private void win_screen_multi(JFrame fram,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_win_reseau.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
		
		/*/ displaying fancy images
		Txt imgGagne = new Txt(new ImageIcon("./data/images/gagne.png"));
        imgGagne.setGravity(GRAVITY.CENTER);
        imgGagne.setxy(50, 12);
        imgGagne.auto_resize();
        _jp_principal.add(imgGagne);
		*/
		//displaying J1
		Txt pseudoLocal = new Txt(_pseudo);
		pseudoLocal.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
		pseudoLocal.setForeground(new Color(255, 209, 0 ,255));
		pseudoLocal.auto_resize();
		pseudoLocal.setxy(10, 12);
        _jp_principal.add(pseudoLocal);
        
     // displaying the points  
        Txt point = new Txt("Score : "+_points);
        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        point.setForeground(new Color(255, 209, 0 ,255));
        point.auto_resize();
        point.setxy(25, 12);
        _jp_principal.add(point);
        
	    //displaying the hashtag
	    Txt hash = new Txt("#"+_hastag);
	    hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
	    hash.setForeground(new Color(36, 78, 124 ,255));
	    hash.auto_resize();
	    hash.setxy(50, 12);
	    _jp_principal.add(hash);
        
	    
	    if(_points_adversaire != -1){
        // displayin j2
        Txt pseudoAdversaire = new Txt(_pseudo_adversaire);
        pseudoAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pseudoAdversaire.setForeground(new Color(255, 255, 255 ,255));
        pseudoAdversaire.auto_resize();
        pseudoAdversaire.setxy(75, 12);
        _jp_principal.add(pseudoAdversaire);
        
        // displaying the points  
        Txt pointAdversaire = new Txt("Score : "+_points_adversaire);
        
        pointAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
        pointAdversaire.setForeground(new Color(255, 255, 255 ,255));
        pointAdversaire.auto_resize();
        pointAdversaire.setxy(90, 12);
    
        _jp_principal.add(pointAdversaire);
        
	    }else{
	    	Txt pointAdversaire = new Txt(_pseudo_adversaire + ": abandon par déconnexion !");
	    	pointAdversaire.setGravity(GRAVITY.CENTER);
	        pointAdversaire.setFont(arista.deriveFont(Font.TRUETYPE_FONT,41));
	        pointAdversaire.setForeground(new Color(255, 35, 10 ,255));
	        pointAdversaire.auto_resize();
	        pointAdversaire.setxy(50, 22);
	    
	        _jp_principal.add(pointAdversaire);
	    	
	    }
      
        int k1 = 0;
        for(TweetWord word : listword){		
    		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord() );
			txt.setForeground(new Color(0, 0, 0 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();	        
	        
	        if(k1%2 == 0){
	        	txt.setGravity(GRAVITY.CENTER_RIGHT);
	        	txt.setxy(14, 22+(8*k1));
	        }else{
	        	txt.setGravity(GRAVITY.CENTER_LEFT);
	        	txt.setxy(78, 22+(8*(k1-1)));
	        }
	        k1++;
			_jp_principal.add(txt);

		}
		// restart button
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
		_b_again.setText("Recommencer");
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy(50,70);
		_jp_principal.add(_b_again);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource() == _b_again)
			new Config_IHM(_fenetre);
	}
}
