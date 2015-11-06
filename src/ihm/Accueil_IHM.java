package ihm;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

public class Accueil_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



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
	    _fenetre.addKeyListener(this);
	    _jp_principal.setLayout(null);
	    

		Icon myImgIcon = new ImageIcon("./data/images/gif3.gif");
		JLabel imageLbl = new JLabel(myImgIcon);
		imageLbl.setBounds(0, 0,_screen.width,_screen.height);
		
		_jp_principal.add(imageLbl, BorderLayout.CENTER);
		
		
		
		
		_b_next = new JButton("#NEXT");
	    _b_next.setFont(arista.deriveFont(55));
	    _b_next.addActionListener(this);
	    _b_next.setSize(150,50);
	    _b_next.setLocation((int) ((_screen.width/2)-(_b_next.getWidth()/2))
	    				   ,(int) (_screen.height-_b_next.getHeight()-(int)(_screen.height*0.1)));
	
		_jp_principal.add( _b_next);
		
	    
		_jp_principal.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());



    
		_fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
	    
	    int vx=1,vy=1;
	    while(true){
	    	
	    	if(_b_next.getLocation().x < 0)
	    		vx = 1;
	    	else if(_b_next.getLocation().x > _screen.width)
	    		vx = -1;
	    	
	    	if(_b_next.getLocation().y < 0)
	    		vy = 1;
	    	else if(_b_next.getLocation().y > _screen.height)
	    		vy = -1;
	    	
	    	
	    	_b_next.setLocation(_b_next.getLocation().x+vx, _b_next.getLocation().y+vy);
	    	_fenetre.repaint();
	    	try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    }
	}


	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_next)
			lauchconfig();
	}

	private void lauchconfig(){
		new Config_IHM(_fenetre);
	}


	


	

}

