package ihm;

import controllers.*;
import utils.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class InGame_IHM extends JFrame implements ActionListener,KeyListener{
	
	private JTextField _tf_saisie;
	private JButton _b_verifier;
	
	private JLabel _txt;
	private JLabel _compteur_de_point;
	private JLabel _compteur_de_vie;
	private JLabel _hashtag;
	
	private JPanel _jp_principal;
	private JPanel _paneltxt;
	
	private JFrame _fenetre;
	
	
	private CtrlTweetEnOr _verifier;
	private  List<TweetWord> _listword;
	private  List<JLabel> _listword_label;
	
	private int _nb_point;
	private int _nb_vie;
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	
	public static void main(String[] args) throws FontFormatException, IOException{
		new InGame_IHM(MEDIUM,"test",new JFrame());
	}
	
	
	
	public InGame_IHM(int Difficulte,String hastag_theme,JFrame fram) throws FontFormatException, IOException{
		
		_nb_vie = Difficulte;
		_nb_point = 0;
		_listword_label = new ArrayList();
		
	    _verifier = new CtrlTweetEnOr(hastag_theme);
		
		
	    _fenetre = fram;
	    _fenetre.getContentPane().removeAll();


		_fenetre.setTitle("Un Tweet en Or ");
	    
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
	    
	    
	    
	    Box box = new Box(BoxLayout.X_AXIS);
	    box.setMaximumSize(new Dimension(9999, 50));
	    box.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    

	    _compteur_de_point = new JLabel("Points :"+_nb_point);	    
	    _compteur_de_vie = new JLabel("vie :"+_nb_vie);
	    
	    box.add(Box.createRigidArea(new Dimension(20,58)));
	    box.add(_compteur_de_point);
	    box.add(Box.createGlue());
	    box.add(_compteur_de_vie);
	    box.add(Box.createRigidArea(new Dimension(20,58)));

	    _jp_principal.add(box);
	    
	    
	    
	    
	    Box box2 = new Box(BoxLayout.X_AXIS);
	    box2.setMaximumSize(new Dimension(9999, 50));
	    box2.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));
	    
	    //Font font = Font.createFont(Font.TRUETYPE_FONT, new File("data/font/arista.ttf"));
	    //font.deriveFont(12f);
	    
		   
	    _hashtag = new JLabel("#"+hastag_theme);
	    _hashtag.setForeground(Color.blue);
	    Font hash_font = new Font("",Font.BOLD,24 );
	   _hashtag.setFont(hash_font);
	   
	   
	    
	    box2.add(Box.createGlue());
	    box2.add(_hashtag);
	    box2.add(Box.createGlue());
	    
	    _jp_principal.add(box2);
	    
	    
	    
	    Box box3 = new Box(BoxLayout.X_AXIS);
	    box3.setMaximumSize(new Dimension(9999, 40));
	    box3.setMinimumSize(new Dimension(_fenetre.getSize().width, 40));
	    _tf_saisie.setMaximumSize(new Dimension(300,40));
	    _tf_saisie.setFont(new Font("",Font.TYPE1_FONT,16 ));
	    _tf_saisie.addKeyListener(this);
	    
	    box3.add(Box.createRigidArea(new Dimension(20,40)));
	    box3.add(Box.createGlue());
	    box3.add(_tf_saisie);
	    box3.add(Box.createGlue());
	    box3.add(Box.createRigidArea(new Dimension(20,40)));
	    
	    _jp_principal.add(box3);
	    
	    
	    
	    
	    
	    Box box4 = new Box(BoxLayout.X_AXIS);
	    box4.setMaximumSize(new Dimension(9999, 0));
	    box4.setMinimumSize(new Dimension(_fenetre.getSize().width, 0));
	    
	    _txt = new JLabel("");
	    
	    box4.add(Box.createGlue());
	    box4.add(_txt);
	    box4.add(Box.createGlue());
	    
	    _jp_principal.add(box4);
	    
	    
	    
	    
	    
	    
	    Box box5 = new Box(BoxLayout.X_AXIS);
	    box5.setMaximumSize(new Dimension(9999, 50));
	    box5.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));
	    
	    
	    _b_verifier = new JButton("vérifier");
		_b_verifier.addActionListener(this);
	    
	    box5.add(Box.createGlue());
	    box5.add(_b_verifier);
	    box5.add(Box.createGlue());
	    
	    _jp_principal.add(box5);
	    
	    
	    
	    
	    
	    
	    
	    _listword = _verifier.getListWords();
	    
	    
	    
	    Box box6 = new Box(BoxLayout.X_AXIS);
	    JPanel pgl = new JPanel(new FlowLayout());
	    pgl.setBackground(new Color(0, 0, 0, 0));
	    pgl.setMaximumSize(new Dimension(900, 900));
	    
	    box6.add(Box.createGlue());
	    box6.add(pgl);
	    box6.add(Box.createGlue());
	    
		int i=0;
		for(TweetWord word : _listword){
			
			
			JPanel p = new JPanel() {
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
			txt.setForeground(new Color(29, 202, 255,255));
			_listword_label.add(txt);
			p.add(txt);

			
			i++;
		}
		
		
	    
	    _jp_principal.add(box6);
	    
	    

	    _fenetre.setVisible(true);
	    
	    
	    
	    
	    
	    
	}

	
	
	public void verifier(String mots_a_verifier){
		
		if(!mots_a_verifier.isEmpty()){
        	_txt.setText(_tf_saisie.getText());
        	TweetWord mots = _verifier.isMotValid(mots_a_verifier);
        	
        	if(mots.getPonderation() == -1){
        		_txt.setText("Mots incorect !");
        		loose_vie();
        	}else if(mots.getPonderation() == -3)
        		_txt.setText("Mots déja rentré !");
        	else if(mots.getPonderation() == -2)
        		_txt.setText("Mots déja rentré et coresspond à "+mots.getWord()+" !");
        	else if(mots.getPonderation() > 0){
        		_txt.setText("Mots "+mots.getWord()+" correct ! plus "+mots.getPonderation()+" points.");
        		add_point(mots.getPonderation(),mots);
        		
        	}
        	_tf_saisie.setText("");
    	}else{
    		_txt.setText("veulliez entrer un mots!");
    	}
		
		if(_nb_point == 100)
			new End_IHM(_fenetre, 1);
		if(_nb_vie == 0)
			new End_IHM(_fenetre, 0);
		
		_fenetre.repaint();
		
	}
	
	
	private void loose_vie(){
		if(_nb_vie != 0){
			_nb_vie--;
			_compteur_de_vie.setText("vie :"+_nb_vie);
		}
		if(_nb_vie == 0)
			javax.swing.JOptionPane.showMessageDialog(null,"Fin de partie : you loose !"); 
	}
	private void add_point(int nb_point,TweetWord mots){
		
		for(JLabel label : _listword_label){
			if(label.getText().compareTo(mots.getWord()) == 0){
				label.setForeground(new Color(255,255,255));
				_fenetre.repaint();
				break;
			}
			
		}
		
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
