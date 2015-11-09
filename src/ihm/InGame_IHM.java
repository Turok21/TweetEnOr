package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.portable.InputStream;

import Sounds.Player;
import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Pa;
import ihm.components.Tf;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import utils.TweetParser;
import utils.TweetWord;


public class InGame_IHM extends IHM_Iterface implements ActionListener,KeyListener{

	private Tf _tf_saisie;
	private Bt _b_verifier;
	
	private Txt _txt;
	private Txt _compteur_de_point;
	private Txt _hashtag;
	private ArrayList<Txt> _vie;
	
	private JFrame _fram_given;
		
	private BufferedImage _Buffered_image_mort,_Buffered_image_vie;
	
	private Image _image_mort,_image_vie;
	
	
	private CtrlTweetEnOr _verifier;
	private List<TweetWord> _listword;
	private List<Txt> _listword_label;
	
	
	private String hasttag;
	
	private int _nb_point;
	private int _nb_vie;
	private int _nb_vie_total;
	
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	public static void main(String[] args) throws FontFormatException, IOException{
		new InGame_IHM(10,"test",new JFrame());
	}
	

	
	
	
	public InGame_IHM(int Difficulte,String hastag_theme,JFrame fram) throws FontFormatException, IOException{
		super();

		
		_vie = new ArrayList<>();
		
		_Buffered_image_mort = ImageIO.read(new File("./data/images/dead_bullet.png"));
		_Buffered_image_vie = ImageIO.read(new File("./data/images/vie.png"));
		_image_mort = _Buffered_image_mort.getScaledInstance(45, 94, Image.SCALE_SMOOTH);
		_image_vie = _Buffered_image_vie.getScaledInstance(45, 94, Image.SCALE_SMOOTH);
	    
		_fram_given = fram;
	    hasttag = hastag_theme;
	    _nb_vie = Difficulte;
		_nb_vie_total = Difficulte;
		_nb_point = 0;
		
		_listword_label = new ArrayList<Txt>();
	    _verifier = new CtrlTweetEnOr(hastag_theme);
		
		draw(0);
		
		
		
	    
	    
	    
		
		show_windows();	
		

	}
	
	public void draw(int redraw){
		
		_listword = _verifier.getListWords();
		
		
		if(redraw == 1){
			_fenetre.remove(_jp_principal);
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fenetre);
		}else{
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fram_given);
		}
	    _fenetre.addKeyListener(this);
		_jp_principal.addKeyListener(this);
		
		
		
	    _tf_saisie = new Tf(50);
	    _tf_saisie.setVisible(true);
	    _tf_saisie.setwh((float)(_screen.width * 0.5), (float)50);
	    _tf_saisie.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,30));
	    _tf_saisie.addKeyListener(this);
	    _tf_saisie.setxy(50, 45);
	    _tf_saisie.setFocusable(true);
	    _jp_principal.add(_tf_saisie);

	    
	  

	    _compteur_de_point = new Txt("Points :"+_nb_point);	 
	    _compteur_de_point.setFont(arista_light.deriveFont(32));
	    
	    
	    float ratio_size_vie = ((float)45/(float)_screen.width)*100;

	    float xtmp=1;
	    for(int i = 0;i<_nb_vie_total;i++){
	    	Txt tmp = new Txt(new ImageIcon(_image_vie));
	    	tmp.setGravity(GRAVITY.TOP_LEFT);
	    	tmp.setxy(xtmp, 2);
	    	xtmp+=ratio_size_vie;
	    	_vie.add(tmp);
	    	_jp_principal.add(tmp);
	    }
	    

	    _hashtag = new Txt("#"+hasttag);
	    _hashtag.setForeground(Color.blue);
	    _hashtag.setFont(arista_light.deriveFont(Font.BOLD,72));
	    _hashtag.auto_resize();
	    _hashtag.setxy(50, 35);
	    _jp_principal.add(_hashtag);

	

	    _txt = new Txt("");
	    
	    
	    _b_verifier = new Bt("vérifier");
	    _b_verifier.setFont(arista_light.deriveFont(Font.BOLD,28));
	    _b_verifier.setGravity(GRAVITY.CENTER);
	    _b_verifier.setwh(150, 75);
	    _b_verifier.auto_resize();
	    _b_verifier.setxy(50, 50);
		_b_verifier.addActionListener(this);
	    _jp_principal.add(_b_verifier);
	    
	 

	
	    List<Pa> words = new ArrayList<Pa>();
		float x=10,wline=0,hline=0,wline2=0,hline2=0;
	  
	    int i = 0;
		for(TweetWord word : _listword){
			
			Pa p = new Pa(null) {
			     
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
			       // graphics.setColor(getForeground());
			       graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			     }
			  };
			  p.setBounds(10,10,100,30);
			  p.setOpaque(false);
			
			  p.setBackground(new Color(29, 202, 255,255));
			
			
			Txt txt = new Txt(""+word.getWord());
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,45));
			txt.setForeground(new Color(29, 202, 255,255));
			//txt.setForeground(new Color(255, 255, 255,255));
			txt.setGravity(GRAVITY.CENTER);		
			_listword_label.add(txt);

			p.add(txt);
			p.setwh(txt.getWidth()+15,txt.getHeight()+5);
			txt.setxyin(50,50,p.getWidth(),p.getHeight());
			
			words.add(p);
			if(i < 5){
				wline += p.getWidth()+15;
				hline = p.getHeight()+5;
			}else {
				wline2 += p.getWidth()+15;
				hline2 = p.getHeight()+5;
			}
			
			i++;
		}
		
		float x_decalage = 0,y_de=60;
		i=1;
		Pa pline = new Pa(null);
		pline.setwh(wline, hline);
		pline.setGravity(GRAVITY.CENTER);
		for(Pa pword : words){
			
	    	pword.setGravity(GRAVITY.TOP_LEFT);
	    	pword.setxyin(x_decalage,0,pline.getWidth(),pline.getHeight());
	    	x_decalage += (((float)pword.getWidth()+15)/(float)pline.getWidth())*100;
	    
	    	
			pline.add(pword);
			
			if(i == 5 || i == 10){
	    		pline.setxy(50,y_de);
	    		pline.setOpaque(false);
	    		_jp_principal.add(pline);
	    		
	    		pline = new Pa(null);
	    		pline.setwh(wline2, hline2);
	    		pline.setGravity(GRAVITY.CENTER);
	    		x_decalage = 0;
	    		y_de += (((float)hline+15)/(float)_screen.getHeight())*100;
	    		System.out.println(y_de);
	    	}
			
			i++;
		}
		
		_fenetre.repaint();
		
		
		
	}
	
	
	
	private void redraw_vie(){
		 for(int i = 0;i<_nb_vie_total;i++){
			 Image myPicture = null;
		    	if(i < _nb_vie){
		    		myPicture = _image_vie;
		    	}else{
		    		myPicture = _image_mort;	    		    		
		    	}

		    	_vie.get(i).setIcon(new ImageIcon(myPicture));
		    }
		 _fenetre.repaint();
	}

	public void verifier(String mot_a_verifier){
		mot_a_verifier = mot_a_verifier.trim();
		if(mot_a_verifier.isEmpty()){
			_txt.setText("Veuillez saisir un (ou plusieurs) mot !");
		}
		else {
			List<String> mots_a_verifier = Arrays.asList(mot_a_verifier.split(" "));
			String affichage  = "";
			for(String mot: mots_a_verifier) {
				mot = TweetParser.cleanWord(mot);
				if(mot.isEmpty()) {
					affichage += "Un des mots proposé est invalide, il a été ignoré ! ";
					continue;
				}
	        	TweetWord motVerifie = _verifier.isMotValid(mot);
	        	
	        	if(motVerifie.getPonderation() == -1){
	        		affichage += "Le mot " + mot + " est incorrect ! ";
	        		loose_vie();
	        	}else if(motVerifie.getPonderation() == -2)
	        		affichage += "Le mot " + mot + " a déja été proposé ! ";
	        	else if(motVerifie.getPonderation() == -3)
	        		affichage += "Le mot " + mot  + " a déja été proposé et correspond à " + motVerifie.getWord() + " ! ";
	        	else if(motVerifie.getPonderation() > 0){
	        		affichage += "Le mot " + motVerifie.getWord() + " est correct (" + motVerifie.getPonderation() + " points) !";
	        		add_point(motVerifie.getPonderation(), motVerifie);
	        	}
	        	_txt.setText(affichage);
	        	if(_nb_point == 100){
					new End_IHM(_fenetre, 1,(ArrayList<TweetWord>)_listword);
					break;
				}
				if(_nb_vie == 0){
					new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword);
					break;
				}
	        	_tf_saisie.setText("");
			}
		}

		_fenetre.repaint();
		
	}
	
	
	private void loose_vie(){
	 
		new Thread(new Runnable() {
			@Override
			public void run() {
				Player player = new Player();
				player.playDie();
				if(_nb_vie != 0){
					_nb_vie--;
					redraw_vie();
				}
			}
		}).start();
	}
	private void add_point(int nb_point,TweetWord mots){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Player player = new Player();
				player.playGoodAnswer();
			}
		}).start();
		
		
		for(Txt label : _listword_label){
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
		super.actionPerformed(e);
        if( e.getSource() == _b_verifier)
        	verifier( _tf_saisie.getText());
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        	verifier(_tf_saisie.getText());
    }
}
