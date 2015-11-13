package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Shared_component;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
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
	private String _hastag; // hashtag used for the game
	private int _points; // nb point gained

	public static void main(String[] args){		
		CtrlTweetEnOr verifier = new CtrlTweetEnOr("test", new Shared_component());
		ArrayList<TweetWord> listword = (ArrayList<TweetWord>) verifier.getListWords();
		new End_IHM(new JFrame(""),0,listword,"test",666);
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
			loose_screen(fram, fin, listword);
		}else{
			win_screen(fram, fin, listword);
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
	private void loose_screen(JFrame fram,int fin,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Fail.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
			
	    boolean left = true; // playe the word at the leste
	    float lastY = 23;
	    boolean firtWordAtLef = true;
	    boolean firtWordAtright = true;
	    
	    int k = 1;
	    
		for(TweetWord word : listword){		
		
			// foreach word creating a new Txt and setting different properties
			Txt txt = new Txt(""+word.getWord());
			txt.setForeground(new Color(87, 1, 8 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();
			
			//displaying the hashtag
			Txt hash = new Txt("#"+_hastag);
	        hash.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
	        hash.setForeground(new Color(140, 0, 0 ,255));
	        hash.auto_resize();
	        hash.setxy(20, 12);
	        _jp_principal.add(hash);
	        
	        // displaying the points she gained 
	        Txt point = new Txt("Score : "+_points);
	        point.setFont(arista.deriveFont(Font.TRUETYPE_FONT,82));
	        point.setForeground(new Color(140, 0, 0 ,255));
	        point.auto_resize();
	        point.setxy(80, 12);
	        _jp_principal.add(point);
	        
	        
			// absolute positioning of all word
			if(left)
			{
				if(firtWordAtLef)
				{ // the first to be displayed
					txt.setxy(11,  lastY);
					firtWordAtLef = false;
				}
				else
				{   // Adjusting all positioning to symmetric
					if(k == 5)
					{
						txt.setxy(11, ( 23 + k*6) + k );
					}
					else if( k == 7)
					{
						txt.setxy(11 ,(23 + k*6) + (k -1)*2);
					}
					else if( k == 9)
					{
						txt.setxy(11 ,(23 + k*6) + k*2);
					}
					else
					{
						txt.setxy(11, 23 + k*6 );
					}
				}
				left = false;
			}
			else
			{
				if(firtWordAtright)
				{
					txt.setxy(89 ,23);
					firtWordAtright = false;
				}
				else
				{
					if(k == 6 )
					{
						txt.setxy(89 ,(23 + (k - 1)*6) + (k-1));
					}
					else if( k == 8)
					{
						txt.setxy(89 ,(23 + (k - 1)*6) + (k-2)*2);
					}
					else if( k == 10)
					{
						txt.setxy(89 ,(23 + (k - 1)*6) + (k-1)*2);
					}
					else
					{
						txt.setxy(89 ,(23 + (k - 1)*6));
					}
				}
				left = true;
			}
			_jp_principal.add(txt);
			
			k++;
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
	
	private void win_screen(JFrame fram,int fin,ArrayList<TweetWord> listword){
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource() == _b_again)
			new Config_IHM(_fenetre);
	}
}
