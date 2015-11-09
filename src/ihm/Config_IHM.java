package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import ihm.components.Bt;
import ihm.components.Tbt;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;

public class Config_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Tbt _b_easy, _b_medium, _b_hard;
	private Bt _b_play;
	
	private Txt _title_fram,_title_dif,_title_hastag;
	private List<JToggleButton> _list_theme;
	

	
	int _difficulte;
	String _hastag_theme;  
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	
	public static void main(String[] args) {
		new Config_IHM(new JFrame("test"));
	}
	
	
	
	public Config_IHM(JFrame fram) {


		load_fenetre_and_panel_principale("Un Tweet en Or - Config ","fond_Tweet_en_or.jpg",fram,false);
				

		
		
	    
	    _title_fram = new Txt("Paramètrage");
	    _title_fram.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 42));
	    //_title_fram.setSize(_screen.width,_title_fram.getFont().getSize());
	    _jp_principal.add(_title_fram);

	    
	    
	    _title_dif = new Txt("Difficulté :");
	    _title_dif.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 24));
	    _title_dif.setxy(20, 20);
	    _jp_principal.add(_title_dif);
	    
	    _b_easy = new Tbt("Facile");
	    _b_easy.setFont(arista_btn);
	    _b_easy.addActionListener(this);
	    _b_easy.setGravity(GRAVITY.TOP_RIGHT);
	    _b_easy.setxy(100, 0);
	    _jp_principal.add(_b_easy);
	    
	    
	    _b_medium = new Tbt("Moyen");
	    _b_medium.setFont(arista_btn);
	    _b_medium.setSelected(true);
	    _b_medium.addActionListener(this);
	    _b_medium.setxy(50, 20);
	    _difficulte = InGame_IHM.MEDIUM;
	    _jp_principal.add(_b_medium);
	    
	    _b_hard = new Tbt("Difficile");
	    _b_hard.setFont(arista_btn);
	    _b_hard.addActionListener(this);
	    _b_hard.setxy(60, 20);
	    _jp_principal.add(_b_hard);
	    
	    
	  /*
	   *   
	   
	    _b_easy = new JToggleButton("Facile");
	    _b_easy.setFont(arista_btn);
	    _b_easy.addActionListener(this);
	    /*_b_easy.setBounds((int) (_screen.getWidth()*0.1)
	    		, (int) (_screen.getHeight()*0.1)
	    		, _b_easy.
	    		, _b_easy.getFont().getSize());
	    	
	    _jp_principal.add(_b_easy);
	    
	    
	    _b_medium = new JToggleButton("Moyen");
	    _b_medium.setFont(arista_btn);
	    _b_medium.setSelected(true);
	    _b_medium.addActionListener(this);
	    _difficulte = InGame_IHM.MEDIUM;
	    _jp_principal.add(_b_medium);
	    
	    _b_hard = new JToggleButton("Difficile");
	    _b_hard.setFont(arista_btn);
	    _b_hard.addActionListener(this);
	    _jp_principal.add(_b_hard);
	    */
	    
	  /*  
	    Box space1 = new Box(BoxLayout.X_AXIS);
	    space1.setPreferredSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    space1.setMinimumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    space1.setMaximumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    jp_sec.add(space1);
	    
	    Box box = new Box(BoxLayout.X_AXIS);
	    box.setMaximumSize(new Dimension(9999, _screen.height/15));
	    box.setMinimumSize(new Dimension(_screen.width, _screen.height/15));

	    box.add(Box.createRigidArea(new Dimension(20,_screen.height/30)));
	    box.add(Box.createGlue());
	    box.add(_title_fram);
	    box.add(Box.createGlue());
	    box.add(Box.createRigidArea(new Dimension(20,_screen.height/5)));

	    jp_sec.add(box);
	    
	    Box space = new Box(BoxLayout.X_AXIS);
	    space.setPreferredSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    space.setMinimumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    space.setMaximumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.05)));
	    jp_sec.add(space);
	    
	    _title_dif = new JLabel("Difficulté :");
	    _title_dif.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 24));
	    Box box_title_dif = new Box(BoxLayout.X_AXIS);
	    box_title_dif.setMaximumSize(new Dimension(9999, _screen.height/25));
	    box_title_dif.setMinimumSize(new Dimension(_screen.width, _screen.height/25));

	    box_title_dif.add(Box.createRigidArea(new Dimension(20,_screen.height/20)));
	    box_title_dif.add(_title_dif);
	    box_title_dif.add(Box.createGlue());
	    box_title_dif.add(Box.createRigidArea(new Dimension(20,_screen.height/50)));

	    jp_sec.add(box_title_dif);
	    

	    Box boxdif = new Box(BoxLayout.X_AXIS);
	    boxdif.setMaximumSize(new Dimension(9999, 50));
	    boxdif.setMinimumSize(new Dimension(_screen.width, 50));
	    
	    _b_easy = new JToggleButton("Facile");
	    _b_easy.setFont(arista_btn);
	    _b_easy.addActionListener(this);
	    
	    _b_medium = new JToggleButton("Moyen");
	    _b_medium.setFont(arista_btn);
	    _b_medium.setSelected(true);
	    _b_medium.addActionListener(this);
	    _difficulte = InGame_IHM.MEDIUM;
	    
	    _b_hard = new JToggleButton("Difficile");
	    _b_hard.setFont(arista_btn);
	    _b_hard.addActionListener(this);
	    
	    
	    boxdif.add(Box.createGlue());
	    boxdif.add(_b_easy);
	    boxdif.add(Box.createRigidArea(new Dimension(20,58)));
	    boxdif.add(_b_medium);
	    boxdif.add(Box.createRigidArea(new Dimension(20,58)));
	    boxdif.add(_b_hard);
	    boxdif.add(Box.createGlue());
	    
	    jp_sec.add(boxdif);
	    

	    Box space2 = new Box(BoxLayout.X_AXIS);
	    space2.setPreferredSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.03)));
	    space2.setMinimumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.03)));
	    space2.setMaximumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.03)));
	    jp_sec.add(space2);
	    
	   
	   
	    
	    _title_hastag = new JLabel("Thèmes :");
	    _title_hastag.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 24));
	    Box box_title_theme = new Box(BoxLayout.X_AXIS);
	    box_title_theme.setMaximumSize(new Dimension(9999, 50));
	    box_title_theme.setMinimumSize(new Dimension(_screen.width, 50));

	    box_title_theme.add(Box.createRigidArea(new Dimension(20,70)));
	    box_title_theme.add(_title_hastag);
	    box_title_theme.add(Box.createGlue());
	    box_title_theme.add(Box.createRigidArea(new Dimension(20,58)));

	    jp_sec.add(box_title_theme);
	    
	    _list_theme = new ArrayList<>();
	    _list_theme.add(new JToggleButton("ski"));
	    _list_theme.add(new JToggleButton("Politique"));
	    _list_theme.add(new JToggleButton("grec"));
	    _list_theme.add(new JToggleButton("russie"));
	    _list_theme.add(new JToggleButton("GOT"));
	    _list_theme.add(new JToggleButton("syrie"));
	    _list_theme.add(new JToggleButton("migrants"));
	    _list_theme.add(new JToggleButton("mail"));
	    _list_theme.add(new JToggleButton("informatique"));
	    _list_theme.add(new JToggleButton("Microsoft"));
	    _list_theme.add(new JToggleButton("apple"));
	    _list_theme.add(new JToggleButton("Playsation"));
	    _list_theme.add(new JToggleButton("Xbox"));
	    _list_theme.add(new JToggleButton("réalité augmentée"));
	    _list_theme.add(new JToggleButton("pollution"));
	    _list_theme.add(new JToggleButton("aircocaine"));
	    _list_theme.add(new JToggleButton("volkswagen"));
	    _list_theme.add(new JToggleButton("pokemon"));
	    _list_theme.add(new JToggleButton("France"));
	    _list_theme.add(new JToggleButton("Licorne"));
	    _list_theme.add(new JToggleButton("Fallout"));
	    _list_theme.add(new JToggleButton("Noel"));
	    _list_theme.add(new JToggleButton("Chine"));
	    _list_theme.add(new JToggleButton("COP21"));
	    _list_theme.add(new JToggleButton("NASA"));
	   

	    _title_hastag = new JLabel("Thèmes :");
	    _title_hastag.setFont(arista_light);
	    Box box_theme = new Box(BoxLayout.X_AXIS);
	    box_theme.setMaximumSize(new Dimension(_screen.width, 50));
	    box_theme.setMinimumSize(new Dimension(_screen.width, 10));
	    
	    box_theme.add(Box.createGlue());
	    int i=0;
	    for(JToggleButton lab : _list_theme){
	    	lab.addActionListener(this);
	    	lab.setFont(arista_btn);
	    	i++;
	    	if(i == 7){
	    		i=0;
	    		
	    		jp_sec.add(box_theme);
	    		box_theme = new Box(BoxLayout.X_AXIS);
	    		box_theme.setMaximumSize(new Dimension(_screen.width, 50));
	    	    box_theme.setMinimumSize(new Dimension(_screen.width, 10));
	    	    box_theme.add(Box.createGlue());
	    		
	    	}
	    	
		    box_theme.add(lab);
		    box_theme.add(Box.createGlue());
	    	
	    }
	    if(i != 0)
	    	jp_sec.add(box_theme);
	    
	    _b_play = new JButton("#Jouer !");
	    _b_play.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 24));
	    _b_play.setEnabled(false);
	    _b_play.addActionListener(this);
	    
	    Box space3 = new Box(BoxLayout.X_AXIS);
	    space3.setPreferredSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.17)));
	    space3.setMinimumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.17)));
	    space3.setMaximumSize(new Dimension((int) (_screen.width*0.9), (int) (_screen.height*0.17)));
	    jp_sec.add(space3);
	    
	    
	    _b_play = new JButton("#Jouer !");
	    _b_play.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 26));
	    _b_play.setEnabled(false);
	    _b_play.setMinimumSize(new Dimension((int) (_screen.width*0.12), (int) (_screen.height*0.07)));
	    _b_play.setMaximumSize(new Dimension((int) (_screen.width*0.12), (int) (_screen.height*0.07)));
	    _b_play.addActionListener(this);
	    Box box_go = new Box(BoxLayout.X_AXIS);
	    box_go.setMaximumSize(new Dimension(_screen.width, 100));
	    box_go.setMinimumSize(new Dimension(_screen.width, 100));

	    box_go.add(Box.createRigidArea(new Dimension(100, 300)));
	    box_go.add(Box.createGlue());
	    box_go.add(_b_play);
	    box_go.add(Box.createRigidArea(new Dimension(20,108)));

	    jp_sec.add(box_go);
	    */

	    
	    show_windows();	
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if( e.getSource() == _b_play ){
			lauchegame();
		}else if( e.getSource() == _b_easy){
        	_b_medium.setSelected(false);
        	_b_hard.setSelected(false);
        	_difficulte = InGame_IHM.EASY;
        }else if( e.getSource() == _b_medium){
        	_b_easy.setSelected(false);
        	_b_hard.setSelected(false);
        	_difficulte = InGame_IHM.MEDIUM;
        }else if( e.getSource() == _b_hard){
        	_b_easy.setSelected(false);
        	_b_medium.setSelected(false);
        	_difficulte = InGame_IHM.HARD;
        }else{
        	 for(JToggleButton lab : _list_theme){
        		 if( e.getSource() == lab){ 	
        			 _hastag_theme = lab.getText();
        			 for(JToggleButton lab2 : _list_theme){
                		 if( lab2 != lab)
                			 lab2.setSelected(false);
             	     }
        			 _b_play.setEnabled(true);
        		 }
     	    }
        }
		
		
	}
	
	
	private void lauchegame(){
		try {
			new InGame_IHM(_difficulte,_hastag_theme,_fenetre);
		} catch (FontFormatException e1) {} catch (IOException e1) {}
	}
}
