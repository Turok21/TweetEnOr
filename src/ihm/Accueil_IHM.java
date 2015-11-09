package ihm;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Sounds.Player;

public class Accueil_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Player player;

	private JButton _b_next;
	private Component _spacer;
	private Box _box_spacer_dif;
	private JPanel _jp_principal;
	
	
	
	public static void main(String[] args) {
		new Accueil_IHM();
	}

	
	
	public Accueil_IHM(){
		super();

	    _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ","fond_Accueil.jpg",this);
	    _jp_principal.setPreferredSize(_screen);
	    _fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
	    _jp_principal.setLayout(null);
	    
 
	    
	    _b_next = new JButton("#NEXT");
	    _b_next.setFont(arista_light.deriveFont(55));
	    _b_next.addActionListener(this);
	    _b_next.setSize(150,50);
	    _b_next.setLocation((int) ((_screen.width/2)-(_b_next.getWidth()/2))
	    				   ,(int) (_screen.height-_b_next.getHeight()-(int)(_screen.height*0.1)));
	
		_jp_principal.add( _b_next);
		
		
	
		JLabel image = new JLabel(
			new ImageIcon(
				new ImageIcon( "./data/images/fond_Accueil.jpg").getImage().getScaledInstance(_screen.width, _screen.height, Image.SCALE_SMOOTH)
			)
		);
		image.setBounds(0, 0, _screen.width, _screen.height);
		_jp_principal.add(image,null);
		
		
		
		
	    
		



    
		
	    
	    int vx=2,vy=2;
	    while(true){
	    	
	    	if(_b_next.getLocation().x < 0)
	    		vx = 2;
	    	else if(_b_next.getLocation().x > _screen.width-_b_next.getWidth())
	    		vx = -2;
	    	
	    	if(_b_next.getLocation().y < 0)
	    		vy = 2;
	    	else if(_b_next.getLocation().y > _screen.height-_b_next.getHeight())
	    		vy = -2;
	    	
	    	
	    	_b_next.setLocation(_b_next.getLocation().x+vx, _b_next.getLocation().y+vy);
	    	_b_next.setText("x : "+vx+"  y : "+vy);
	    	_fenetre.repaint();
	    	try {
				Thread.sleep(10);
			} catch (InterruptedException e) { e.printStackTrace(); }
	    	 
	    }
	    
	    //applique_fond();
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_next)
			player = new Player();
			player.playTwitter();
			lauchconfig();
	}

	private void lauchconfig(){
		new Config_IHM(_fenetre);
	}


	


	

}

