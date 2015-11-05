package ihm;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
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
		
  
	    _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ","gif3.gif"/*"fond_Accueil.jpg"*/,this);
	    _fenetre.addKeyListener(this);
	    

	    
	    _b_next = new JButton("next");
	    _b_next.setFont(arista_light.deriveFont(55));
	    _b_next.addActionListener(this);
	    _b_next.setMinimumSize(new Dimension(150, 75));
	    _b_next.setMaximumSize(new Dimension(150, 50));
	    _b_next.setPreferredSize(new Dimension(150, 75));
	    
	   
	   
	    _box_spacer_dif = new Box(BoxLayout.Y_AXIS);
		_box_spacer_dif.setMaximumSize(new Dimension(9999, 200));
		_box_spacer_dif.setMinimumSize(new Dimension(_fenetre.getSize().width, _fenetre.getSize().height));
		_spacer = Box.createVerticalStrut(Toolkit.getDefaultToolkit().getScreenSize().height-_b_next.getHeight()-150);
		_box_spacer_dif.add(_spacer);
		System.out.println(""+_fenetre.getHeight());
		_box_spacer_dif.add(_b_next);
		_jp_principal.add(_box_spacer_dif);
			


    
		_fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
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

