package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class Setting_IHM extends JFrame implements ActionListener{
	


	private JTextField _tf_saisie;

	
	private JToggleButton _b_easy, _b_medium, _b_hard;
	private JButton _b_play;
	
	private JLabel _title_fram,_title_dif,_title_hastag;
	private List<JToggleButton> _list_theme;
	
	
	private JFrame _fenetre;
	
	
	
	
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	
	public static void main(String[] args) throws FontFormatException, IOException{
		new Setting_IHM();
	}
	
	
	
	public Setting_IHM() throws FontFormatException, IOException{
		

	    _fenetre = new JFrame();
		_fenetre.setTitle("Un Tweet en Or - Setting ");
	    
	    _fenetre.setSize(800, 600);
	    _fenetre.setMinimumSize(new Dimension(800, 600));
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    
	    
	    _tf_saisie = new JTextField(50);
	    _tf_saisie.setVisible(true);
	    
	    JPanel _jp_principal = new JPanel();
	    _jp_principal.setBackground(Color.ORANGE);
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    _fenetre.add(_jp_principal);
	    
	    
	    
	    
	    
	    _title_fram = new JLabel("Setting");	    
	    
	    Box box = new Box(BoxLayout.X_AXIS);
	    box.setMaximumSize(new Dimension(9999, 50));
	    box.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    box.add(Box.createRigidArea(new Dimension(20,58)));
	    box.add(Box.createGlue());
	    box.add(_title_fram);
	    box.add(Box.createGlue());
	    box.add(Box.createRigidArea(new Dimension(20,58)));

	    _jp_principal.add(box);
	    
	    
	    _title_dif = new JLabel("Dificulté :");
	    Box box_title_dif = new Box(BoxLayout.X_AXIS);
	    box_title_dif.setMaximumSize(new Dimension(9999, 50));
	    box_title_dif.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    box_title_dif.add(Box.createRigidArea(new Dimension(20,58)));
	    box_title_dif.add(_title_dif);
	    box_title_dif.add(Box.createGlue());
	    box_title_dif.add(Box.createRigidArea(new Dimension(20,58)));

	    _jp_principal.add(box_title_dif);
	    
	    
	    
	    Box boxdif = new Box(BoxLayout.X_AXIS);
	    boxdif.setMaximumSize(new Dimension(9999, 50));
	    boxdif.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));
	    
	    _b_easy = new JToggleButton("EASY");
	    _b_medium = new JToggleButton("MEDUIM");
	    _b_medium.setSelected(true);
	    _b_hard = new JToggleButton("HARD");
	    _b_easy.addActionListener(this);
	    _b_medium.addActionListener(this);
	    _b_hard.addActionListener(this);
	    
	    boxdif.add(Box.createGlue());
	    boxdif.add(_b_easy);
	    boxdif.add(Box.createRigidArea(new Dimension(20,58)));
	    boxdif.add(_b_medium);
	    boxdif.add(Box.createRigidArea(new Dimension(20,58)));
	    boxdif.add(_b_hard);
	    boxdif.add(Box.createGlue());
	    
	    _jp_principal.add(boxdif);
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

	    _title_hastag = new JLabel("Thèmes :");
	    Box box_title_theme = new Box(BoxLayout.X_AXIS);
	    box_title_theme.setMaximumSize(new Dimension(9999, 50));
	    box_title_theme.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    box_title_theme.add(Box.createRigidArea(new Dimension(20,58)));
	    box_title_theme.add(_title_hastag);
	    box_title_theme.add(Box.createGlue());
	    box_title_theme.add(Box.createRigidArea(new Dimension(20,58)));

	    _jp_principal.add(box_title_theme);
	    
	    
	    _list_theme = new ArrayList<>();
	    _list_theme.add(new JToggleButton("SKI"));
	    _list_theme.add(new JToggleButton("Pomme"));
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
	    _list_theme.add(new JToggleButton("XBOX"));
	    _list_theme.add(new JToggleButton("réaliteraugmenter"));
	    _list_theme.add(new JToggleButton("lepain"));
	    _list_theme.add(new JToggleButton("BLABLA"));
	    _list_theme.add(new JToggleButton("ETCETC"));
	    _list_theme.add(new JToggleButton("ETCETC"));
	    _list_theme.add(new JToggleButton("ETCETC"));
	   

	    _title_hastag = new JLabel("Thèmes :");
	    Box box_theme = new Box(BoxLayout.X_AXIS);
	    box_theme.setMaximumSize(new Dimension(_fenetre.getSize().width, 50));
	    box_theme.setMinimumSize(new Dimension(_fenetre.getSize().width, 10));
	    
	    box_theme.add(Box.createGlue());
	    int i=0;
	    for(JToggleButton lab : _list_theme){
	    	lab.addActionListener(this);
	    	i++;
	    	if(i == 7){
	    		i=0;
	    		
	    		_jp_principal.add(box_theme);
	    		box_theme = new Box(BoxLayout.X_AXIS);
	    		box_theme.setMaximumSize(new Dimension(_fenetre.getSize().width, 50));
	    	    box_theme.setMinimumSize(new Dimension(_fenetre.getSize().width, 10));
	    	    box_theme.add(Box.createGlue());
	    		
	    	}
	    	
		    box_theme.add(lab);
		    box_theme.add(Box.createGlue());
	    	
	    }
	    if(i != 0)
	    	_jp_principal.add(box_theme);
	    
	    
	    
	    
	    
	    _b_play = new JButton("#FEU !");
	    _b_play.setEnabled(false);
	    _b_play.addActionListener(this);
	    Box box_go = new Box(BoxLayout.X_AXIS);
	    box_go.setMaximumSize(new Dimension(9999, 50));
	    box_go.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    
	    box_go.add(Box.createGlue());
	    box_go.add(_b_play);
	    box_go.add(Box.createRigidArea(new Dimension(20,108)));
	    
	    

	    _jp_principal.add(box_go);
	    
    
	    
	    _fenetre.setVisible(true);
	    

	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == _b_play ){
			try {
				new InGame_IHM(InGame_IHM.HARD);
			} catch (FontFormatException e1) {} catch (IOException e1) {}
			_fenetre.setVisible(false); 
			_fenetre.dispose(); 
			
			this.dispose();
		}else if( e.getSource() == _b_easy){
        	_b_medium.setSelected(false);
        	_b_hard.setSelected(false);
        }else if( e.getSource() == _b_medium){
        	_b_easy.setSelected(false);
        	_b_hard.setSelected(false);
        }else if( e.getSource() == _b_hard){
        	_b_easy.setSelected(false);
        	_b_medium.setSelected(false);
        }else{
        	
        	 for(JToggleButton lab : _list_theme){
        		 if( e.getSource() == lab){ 	  
        			 for(JToggleButton lab2 : _list_theme){
                		 if( lab2 != lab)
                			 lab2.setSelected(false);
             	    }
        			 _b_play.setEnabled(true);
        		 }
     	    }
        	
        }
        
	}
	
	


}
