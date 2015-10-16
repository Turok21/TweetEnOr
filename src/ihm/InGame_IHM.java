package ihm;

import controllers.*;
import utils.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class InGame_IHM extends JFrame implements ActionListener,KeyListener{
	
	private JTextField _tf_saisie;
	private JButton _b_verifier;
	
	private JLabel _txt;
	private JLabel _compteur_de_point;
	
	private JPanel _jp_principal;
	
	private CtrlTweetEnOr _verifier;
	
	private JPanel _paneltxt;
	
	private JFrame _fenetre;
	
	private int _nb_point;
	private int _nb_vie;
	
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	
	public static void main(String[] args){
		InGame_IHM ingame = new InGame_IHM(MEDIUM);
	}
	
	
	
	public InGame_IHM(int Difficulte){
		
		_nb_vie = Difficulte;
		
		_nb_point = 0;
	    _verifier = new CtrlTweetEnOr("ski");
		
		
	    _fenetre = new JFrame();
		_fenetre.setTitle("Un Tweet en Or ");
	    
	    _fenetre.setSize(800, 600);
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    
	    
	    _tf_saisie = new JTextField(50);
	    _tf_saisie.setVisible(true);
	    
	    JPanel _jp_principal = new JPanel();
	    _jp_principal.setBackground(Color.ORANGE);
	    _fenetre.add(_jp_principal);
	    
	    
	    GridLayout gl = new GridLayout(0,1);
	    _jp_principal.setLayout(gl);
	    
	    
	   
	    for(int i=0;i<1;i++){
		    JPanel panelz = new JPanel();
			_jp_principal.add(panelz);
			panelz.setBackground(new Color(0, 0, 0, 0));
	    }
	    
	    
	    

	    JPanel panel1 = new JPanel();
	    _jp_principal.add(panel1);
	    panel1.setBackground(new Color(0, 0, 0, 0));
	    _compteur_de_point = new JLabel("Points "+_nb_point);
	    panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
	    panel1.add(_compteur_de_point);
	    
	    
	    
	    
	    
	    JPanel panelb = new JPanel();
		_jp_principal.add(panelb);
		panelb.setBackground(new Color(0, 0, 0, 0));
		JLabel test = new JLabel("#test");
		
		panelb.add(test);
	    
	  
	 
	    JPanel panel2 = new JPanel();
	    _jp_principal.add(panel2);
	    panel2.setBackground(new Color(0, 0, 0, 0));
		panel2.add(_tf_saisie);
		_tf_saisie.addKeyListener(this);
	
		
		
		JPanel panel3 = new JPanel();
		_jp_principal.add(panel3);
		panel3.setBackground(new Color(0, 0, 0, 0));
		 _b_verifier = new JButton("vérifier");
		 panel3.add(_b_verifier);
		 _b_verifier.addActionListener(this);
		 


		 _paneltxt = new JPanel();
		_jp_principal.add(_paneltxt);
		_paneltxt.setBackground(new Color(0, 0, 0, 0));
		_txt = new JLabel("");
		_paneltxt.add(_txt);
		

	    
	    
		
	    
		for(int i=0;i<3;i++){
		    JPanel panelz = new JPanel();
			_jp_principal.add(panelz);
			panelz.setBackground(new Color(0, 0, 0, 0));
	    }
    
	    
	    _fenetre.setVisible(true);
	    
	    
	    
	    
	    
	    
	}
	
	private void add_panel(JPanel panel){
		
		_jp_principal.add(panel);
		panel.setBackground(new Color(0, 0, 0, 0));
		
	
	}
	
	public void verifier(String mots_a_verifier){
		
		if(!mots_a_verifier.isEmpty()){
        	_txt.setText(_tf_saisie.getText());
        	TweetWord mots = _verifier.isMotValid(mots_a_verifier);
        	
        	if(mots.getPonderation() == -1)
        		_txt.setText("Mots incorect !");
        	else if(mots.getPonderation() == -3)
        		_txt.setText("Mots déja rentré !");
        	else if(mots.getPonderation() == -2)
        		_txt.setText("Mots déja rentré et coresspond à "+mots.getWord()+" !");
        	else if(mots.getPonderation() > 0){
        		_txt.setText("Mots "+mots.getWord()+" correct ! plus "+mots.getPonderation()+" points.");
        		add_point(mots.getPonderation());
        		
        	}
        	
    	}else{
    		_txt.setText("veulliez entrer un mots!");
    	}
		
		_fenetre.repaint();
		
	}
	
	
	
	public void add_point(int nb_point){
		_nb_point += nb_point;
		_compteur_de_point.setText("Points "+_nb_point);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
        if( e.getSource() == _b_verifier)
        	verifier( _tf_saisie.getText());
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        	verifier(_tf_saisie.getText());
    }
	
	
	

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

}
