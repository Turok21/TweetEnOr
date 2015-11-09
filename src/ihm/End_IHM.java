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
	private JLabel _text;
	private Bt _b_again;


	public static void main(String[] args){		
		CtrlTweetEnOr verifier = new CtrlTweetEnOr("test");
		ArrayList<TweetWord> listword = (ArrayList<TweetWord>) verifier.getListWords();
		new End_IHM(new JFrame(""),0,listword);
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
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Fail.jpg",fram);
		
		_fenetre.addKeyListener(this);
			
	    boolean left = true;
	    boolean firtWordAtLef = true;
	    boolean firtWordAtright = true;
	    int k = 1;
		for(TweetWord word : listword){		
		
			
			Txt txt = new Txt(""+word.getWord());
			txt.setFont(new Font("",Font.BOLD,40 ));
			txt.setForeground(new Color(242, 40, 0 ,255));
			//txt.setGravity(GRAVITY.CENTER);
			
			if(left)
			{
				if(firtWordAtLef)
				{
					txt.setxy(7,  20);
					left = false;
					firtWordAtLef = false;
				}
				else
					txt.setxy(7,  k*10);
			}
			else
			{
				if(firtWordAtright)
				{
					txt.setxy(93 ,20);
					left = true;
					firtWordAtright = false;
				}
				else
					txt.setxy(93 ,k*10);
			}
			_jp_principal.add(txt);
			
			k++;
		}

		_text = new JLabel();
	    
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setText("Recommencer");
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy(50,50);
		_jp_principal.add(_b_again);
		
		
	}

	
	private void win_screen(JFrame fram,int fin,ArrayList<TweetWord> listword){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Win.jpg",fram);
		
		_fenetre.addKeyListener(this);
			
		JPanel jp_sec = new JPanel();
		jp_sec.setOpaque(false);
		jp_sec.setLayout(new BoxLayout(jp_sec,BoxLayout.Y_AXIS));
		_jp_principal.add(jp_sec);
	    
		
		
		Box spacer3 = new Box(BoxLayout.X_AXIS);
	    spacer3.setPreferredSize(new Dimension(40, 400));
	    jp_sec.add(spacer3);
 
	  
	    Box box6 = new Box(BoxLayout.X_AXIS);
	    JPanel pgl = new JPanel(new FlowLayout());
	    pgl.setBackground(new Color(0, 0, 0, 0));
	    pgl.setMaximumSize(new Dimension(900, 900));
	    
	    box6.add(Box.createGlue());
	    box6.add(pgl);
	    box6.add(Box.createGlue());
	    
	   
	   
	    Box box3 = null;
	    int k = 1;
		for(TweetWord word : listword){		
					    
		    if(k%2 != 0){
		    	box3 = new Box(BoxLayout.X_AXIS);
		    	System.out.println(""+(int) ((Toolkit.getDefaultToolkit().getScreenSize().width*0.8)-(k*100)));
		    	box3.setMaximumSize(new Dimension((int) ((Toolkit.getDefaultToolkit().getScreenSize().width*0.8)-(k*100)),
												  (int) (Toolkit.getDefaultToolkit().getScreenSize().height*0.05) ));
		    	//box3.setOpaque(true);
		    }
			
			JPanel p = new JPanel() {
			     /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			     protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Dimension arcs = new Dimension(15,15);
			        int width = getWidth();
			        int height = getHeight();
			        Graphics2D graphics = (Graphics2D) g;
			        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			        graphics.setColor(getBackground());
			        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
			        graphics.setColor(getForeground());
			       // graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			     }
			  };
			  p.setBounds(10,10,100,30);
			  p.setOpaque(false);
			
			 p.setBackground(new Color(29, 202, 255,255));
			
			pgl.add(p);
			
			
			
			JLabel txt = new JLabel(""+word.getWord());
			txt.setFont(new Font("",Font.BOLD,24 ));
			txt.setForeground(new Color(242, 209, 0 ,255));
			p.add(txt);
			
			Box box4 = new Box(BoxLayout.X_AXIS);
			box4.add(Box.createGlue());
			box4.add(p);
			box4.add(Box.createGlue());
			
			if(k%2 == 0){
				Box spacer4 = new Box(BoxLayout.X_AXIS);
				spacer4.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width*0.8)-((k*100)), 0));
				jp_sec.add(spacer4);
				box3.add(spacer4);
				box3.add(box4);
				jp_sec.add(box3);
			}else{
				box3.add(box4);
			}
			
			
			k++;
		}
		
		
	    
	    jp_sec.add(box6);
	    
	    
	    _text = new JLabel();
		_b_again = new Bt();
		_b_again.setPreferredSize(new Dimension(150, 50));
		_b_again.addActionListener(this);


		Box spacer = new Box(BoxLayout.X_AXIS);
		spacer.setPreferredSize(new Dimension(40, (int) (Toolkit.getDefaultToolkit().getScreenSize().width*0.08)));
		jp_sec.add(spacer);
		    
		   
		_b_again.setText("Recommencer");
		jp_sec.add(_b_again);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource() == _b_again)
			new Config_IHM(_fenetre);
	}
}
