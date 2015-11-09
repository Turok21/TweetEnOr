package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import utils.TweetWord;

public class End_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bt _b_again;


	public static void main(String[] args){		
		CtrlTweetEnOr verifier = new CtrlTweetEnOr("test");
		ArrayList<TweetWord> listword = (ArrayList<TweetWord>) verifier.getListWords();
		new End_IHM(new JFrame(""),1,listword);
	}

	public End_IHM(JFrame fram,int fin,ArrayList<TweetWord> listword) {
		super();
		
		
		
		if(fin == 0)
			loose_screen(fram, fin, listword);
		else
			win_screen(fram, fin, listword);
		
    
	    
	   show_windows();

	}
	

	private void loose_screen(JFrame fram,int fin,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Fail.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
			
	    boolean left = true;
	    float lastY = 23;
	    boolean firtWordAtLef = true;
	    boolean firtWordAtright = true;
	    
	    int k = 1;
	    
		for(TweetWord word : listword){		
		
			
			Txt txt = new Txt(""+word.getWord());
			txt.setForeground(new Color(87, 1, 8 ,255));
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,40));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();
			
			if(left)
			{
				if(firtWordAtLef)
				{
					txt.setxy(7,  lastY);
					firtWordAtLef = false;
				}
				else
				{
					if(k == 5)
					{
						txt.setxy(7, ( 23 + k*6) + k );
					}
					else if( k == 7)
					{
						txt.setxy(7 ,(23 + k*6) + (k -1)*2);
					}
					else if( k == 9)
					{
						txt.setxy(7 ,(23 + k*6) + k*2);
					}
					else
					{
						txt.setxy(7, 23 + k*6 );
					}
				}
				left = false;
			}
			else
			{
				if(firtWordAtright)
				{
					txt.setxy(93 ,23);
					firtWordAtright = false;
				}
				else
				{
					if(k == 6 )
					{
						txt.setxy(93 ,(23 + (k - 1)*6) + (k-1));
					}
					else if( k == 8)
					{
						txt.setxy(93 ,(23 + (k - 1)*6) + (k-2)*2);
					}
					else if( k == 10)
					{
						txt.setxy(93 ,(23 + (k - 1)*6) + (k-1)*2);
					}
					else
					{
						txt.setxy(93 ,(23 + (k - 1)*6));
					}
				}
				left = true;
			}
			_jp_principal.add(txt);
			
			k++;
		}
	    
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setFont(arista_light.deriveFont(55));
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy(50,85);
		_jp_principal.add(_b_again);
			
	}

	
	private void win_screen(JFrame fram,int fin,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Win.jpg",fram, false);
		
		_fenetre.addKeyListener(this);
			
	    int k = 1;
	    float lastX = 10;
	    float lastY = 75;
		for(TweetWord word : listword){		
					    
			Txt txt = new Txt(""+word.getWord());
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,40));
			txt.setForeground(new Color(242, 209, 0 ,255));
			txt.setGravity(GRAVITY.CENTER);
			txt.auto_resize();
			txt.setxy(lastX, lastY);

			lastX += 9;
			if(k < 4  )
				lastY -= 10;
			if( k > 6 )
				lastY += 10;
		
			_jp_principal.add(txt);
			k++;
		}
		
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy(50,50);
		_jp_principal.add(_b_again);
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource() == _b_again)
			new Config_IHM(_fenetre);
	}
}
