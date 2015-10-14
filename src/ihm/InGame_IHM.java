package ihm;

import controllers.*;
import utils.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class InGame_IHM extends JFrame implements ActionListener{
	
	private JTextField _tf_saisie;
	private JButton _b_verifier;
	private JLabel _txt;
	private CtrlTweetEnOr _verifier;
	
	public static void main(String[] args){
		InGame_IHM ingame = new InGame_IHM();
	}
	
	public InGame_IHM(){
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Ma première fenêtre Java");
	    
	    fenetre.setSize(800, 600);
	    
	    fenetre.setLocationRelativeTo(null);
	    
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    
	    
	    _tf_saisie = new JTextField(50);
	    _tf_saisie.setVisible(true);
	    
	    JPanel jp_principal = new JPanel();
	    jp_principal.setBackground(Color.ORANGE);
	    
	    fenetre.setContentPane(jp_principal);
	    jp_principal.add(_tf_saisie);
	    
	    _b_verifier = new JButton("vérifier");
	    jp_principal.add(_b_verifier);
	    _b_verifier.addActionListener(this);
	    
	   _txt = new JLabel("rsdhglrdkshgdf");
	    
	    jp_principal.add(_txt);
	    
	    fenetre.setVisible(true);
	    
	    
	    //_verifier = new CtrlTweetEnOr("ski")
	    
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();

        if(source == _b_verifier){
        	if(!_tf_saisie.getText().isEmpty()){
	        	_txt.setText(_tf_saisie.getText());
	        	//_verifier.isMotValid(_tf_saisie.getText());
        	}else{
        		_txt.setText("veulliez entrer un mots!");
        	
        	}
        	
        }
		
	}
	
	

}
