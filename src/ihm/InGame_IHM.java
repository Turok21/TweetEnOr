package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import Sounds.Player;
import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Pa;
import ihm.components.Shared_component;
import ihm.components.Tbt;
import ihm.components.Tf;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import twitter4j.TwitterException;
import utils.Joueur;
import utils.TweetParser;
import utils.TweetWord;


public class InGame_IHM extends IHM_Iterface implements ActionListener,KeyListener, MouseListener{

	protected static final long serialVersionUID = 1L;

	protected Tf _tf_saisie;
	
	protected Bt _b_verifier;
	
	protected Tbt _b_hintShuffle;
	protected Bt _b_hintNbLetters;
	protected Tbt _b_hintColor;
	
	protected Txt _loader;
	protected Txt _info_player;
	protected Txt _compteur_de_point,_compteur_de_point_adversaire;
	protected Txt _t_hashtag;
	protected ArrayList<Txt> _vie;
	protected ArrayList<Txt> _listword_label;
	protected ArrayList<Txt> _listLetters;
	protected ArrayList<Txt> _listPts;
	
	protected ArrayList<String> _coloredPan;
	protected ArrayList<String> _AnaPan;
	protected ArrayList<String> _VerifPan;
	protected Txt _msg;
	protected Integer _nbAna;
	protected Txt _nbAnaCpt;
	protected Integer _nbColor;
	protected Txt _nbColorCpt;

	
	protected HashMap<String, String> wordAna;
	
	protected JFrame _fram_given;
	
	protected Pa _Panel_loader;
	
	protected Shared_component _shared;//objet d'échange avec CtrlTweetEnOr pour la bar de progression du loader
	
	protected BufferedImage _Buffered_image_mort,_Buffered_image_vie;
	
	protected Image _image_mort,_image_vie;
	
	protected Player player;//gestionnaire des sons
	
	protected List<TweetWord> _listword;
	
	protected Joueur _j_local;
	
	protected String _hashtag;
	
	protected int _nb_vie;
	protected LEVEL _difficulte;
	protected int _maj,_tab;//cheat afficher/cacher les mots
	
	protected int _mots_trouver;
	
	protected Bt _retourConfig;

	protected CtrlTweetEnOr _CTEO;
	
	protected MouseListener ml;

	public static void main(String[] args) throws TwitterException, Exception{
		new InGame_IHM(LEVEL.MEDIUM,"StarWars",new JFrame());
		
	}
	

	
	
	/**
	 * Constructeur 
	 * 
	 * @param Difficulte
	 * @param hastag_theme
	 * @param fram
	 * @throws IOException
	 */
	public InGame_IHM(LEVEL difficulte,String hastag_theme,JFrame fram) throws IOException {
		/*************** initialisation des variables ***************/
		super();
		_shared = new Shared_component();
		_maj = 0;
		_tab = 0;
		_vie = new ArrayList<>();
		_fram_given = fram;
	    _hashtag = hastag_theme;
	    _nb_vie = difficulte.getvalue();
	    _difficulte = difficulte;
	    _mots_trouver = 0;
		
		_j_local = new Joueur();
		
		_listword_label = new ArrayList<Txt>();

		_listLetters = new ArrayList<Txt>();
		_listPts = new ArrayList<Txt>();
		
		_coloredPan = new ArrayList<String>();
		_VerifPan = new ArrayList<String>();
		
		switch(_difficulte){ 
			case EASY:
				_nbAna = 3;
				_nbColor = 3;
			break;
			case MEDIUM :
				_nbAna = 2;
				_nbColor = 2;
			break;
			case HARD :
				_nbAna = 1;
				_nbColor = 1;
			break;
			
		}
		
	
		
		if(fram !=  null){
			load_vie_img();
			    
			drawloader(0);//gestion de l'affichage du loader lors du chargement des tweets
			
			load_game_data();
		}
		
		
	}
	
	
	
	protected void load_vie_img(){
		/*************** chargement des images pour les vies ***************/
		try {
			_Buffered_image_mort = ImageIO.read(new File("./data/images/dead_bullet.png"));
			_Buffered_image_vie = ImageIO.read(new File("./data/images/vie.png"));
			_image_mort = _Buffered_image_mort.getScaledInstance(45, 94, Image.SCALE_SMOOTH);
			_image_vie = _Buffered_image_vie.getScaledInstance(45, 94, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("ERROR : images vie oiseaux impossible à charger => "+ e.getMessage());
		}
		
	}
	protected void load_game_data(){
		/*************** chargement des données de jeu(twwets) dans un thread à pars pour ne pas freezer le loader ***************/	    
		new Thread(new Runnable() {
			@Override
			public void run() {
								
			    try {
					_CTEO = new CtrlTweetEnOr(_hashtag,_shared);
				} catch (Exception e) {
					if(e instanceof IllegalStateException) //credentials missing
					{
						_shared.txt_line1.setText("Impossible de se connecter à Twitter");
						_retourConfig.setVisible(true);
						return;
					}
					e.printStackTrace();
					
				}
			    
			  
				_listword = _CTEO.getListWords();
				System.out.println(_listword);
				if (_listword.size() == 0){
					_retourConfig.setVisible(true);
					_shared.txt_line1.setText("Il n'y a pas assez de tweets");
					return;
				}
				draw_play_screen(0);//affiche l'ecran de jeu

				_fenetre.remove(_Panel_loader);
				_fenetre.getContentPane().repaint();
			}
		}).start();
	}
	
	/**
	 * drawloader
	 * @param redraw : si = 1 une nouvelle fram sera créé
	 * @param redraw
	 */
	protected void drawloader(int redraw){
		
		
		if(redraw == 1){
			_fenetre.remove(_jp_principal);
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","",_fenetre,false);
		}else{
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","",_fram_given,false);
		}
		
		
		
		
		/*************** chargement et paramétrage du loader twitter  ***************/
        _loader = new Txt(new ImageIcon("./data/images/gif.gif"));
        _loader.setxy(50, 35);
        _loader.setOpaque(true);
        
        /*************** text d'informations sous la bar de progression ***************/
        _shared.txt_line1 = new Txt();
        _shared.txt_line1.setGravity(GRAVITY.CENTER);
        _shared.txt_line1.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,24));
        _shared.txt_line1.setForeground(Color.white);
        _shared.txt_line1.settext("Création des données de jeux en cours ...");
        _shared.txt_line1.setxy(50, 64);
        
        

        /*************** ProgressBar ***************/
        _shared._progressbar = new JProgressBar();
        _shared._progressbar.setSize(500,30);
        _shared._progressbar.setForeground(new Color(29, 202, 255,255));
        _shared._progressbar.setLocation((int)((_screen.width/2)-(_shared._progressbar.getSize().width*0.5))
        		, (int)((_screen.height*0.6)-(_shared._progressbar.getSize().height/2)));
        
        
        /**************** Retour_config ******************/
        _retourConfig = new Bt("Retour à l'écran de paramètrage");
		_retourConfig.setFont(arista_light.deriveFont(Font.BOLD,20));
		_retourConfig.setGravity(GRAVITY.CENTER);
		_retourConfig.setwh(100, 100);
		_retourConfig.auto_resize();
		_retourConfig.setxy(50, 75);
		_retourConfig.addActionListener(this);
        
        
        /*************** Panel_loader contient tout les autre composents du loader ***************/
        _Panel_loader = new Pa(null);
        _Panel_loader.setSize(_screen);
        _Panel_loader.setBackground(new Color(40, 170, 225));
        _Panel_loader.add(_retourConfig);
		_retourConfig.setVisible(false);
        _Panel_loader.add(_shared.txt_line1);
        _Panel_loader.add(_shared._progressbar);
        _Panel_loader.add(_loader);
        
        
        
       
        
        
        
        _fenetre.add(_Panel_loader);//ajoute le panel_loader et tout ses composents à la fenetre
      
        show_windows();//affiche la fenetre avec ses composent et sont fond
	}
	
	/**
	 * Affiche l'ecran de jeux  
	 * @param redraw : si = 1 une nouvelle fram sera créé
	 */
	protected void draw_play_screen(int redraw){
		
		
		if(redraw == 1){
			_fenetre.remove(_jp_principal);
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fenetre,false);
		}else{
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fram_given,true);
		}
		
		
		
	    _fenetre.addKeyListener(this);
		_jp_principal.addKeyListener(this);
		
		
		
		
		/*************** _compteur_de_point ***************/
	    _compteur_de_point = new Txt("Points :"+_j_local.getPoint());	 
	    _compteur_de_point.setFont(arista_light.deriveFont(32));
	    _compteur_de_point.setGravity(GRAVITY.TOP_RIGHT);
	    _compteur_de_point.setxy((float)98.5,(float)3);
	    _jp_principal.add(_compteur_de_point);
	    
	    
	    /*************** _vie  ***************/
	    float ratio_size_vie = ((float)45/(float)_screen.width)*100;
	    float xtmp=1;
	    for(int i = 0;i<_difficulte.getvalue();i++){
	    	Txt tmp = new Txt(new ImageIcon(_image_vie));
	    	tmp.setGravity(GRAVITY.TOP_LEFT);
	    	tmp.setxy(xtmp, 2);
	    	xtmp+=ratio_size_vie;
	    	_vie.add(tmp);
	    	_jp_principal.add(tmp);
	    }
	    
	    
	    
	    
		
		
		
		
		
		
		 /*************** _tf_saisie ***************/
	    _tf_saisie = new Tf(50);
	    _tf_saisie.setVisible(true);
	    _tf_saisie.setwh((float)(_screen.width * 0.5), (float)50);
	    _tf_saisie.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,30));
	    _tf_saisie.addKeyListener(this);
	    _tf_saisie.setxy(50,37);
	    _tf_saisie.setFocusable(true);
	    _jp_principal.add(_tf_saisie);


	    /*************** _t_hashtag ***************/
	    _t_hashtag = new Txt("#"+_hashtag);
	    _t_hashtag.setForeground(Color.blue);
	    _t_hashtag.setFont(arista_light.deriveFont(Font.BOLD,72));
	    _t_hashtag.auto_resize();
	    _t_hashtag.setxy(50, 25);
	    _jp_principal.add(_t_hashtag);

	
	    /*************** _info_player déchange avec l'utilisateur ***************/
	    _info_player = new Txt("Entrez un mot en rapport avec ce hashtag !");
	    _info_player.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
	    _info_player.auto_resize();
	    _info_player.setxy(50, 48);
	    _jp_principal.add(_info_player);
	    
	    
	    /*************** _b_verifier ***************/
	    _b_verifier = new Bt("vérifier");
	    _b_verifier.setFont(arista_light.deriveFont(Font.BOLD,28));
	    _b_verifier.setGravity(GRAVITY.CENTER);
	    _b_verifier.setwh(150, 75);
	    _b_verifier.auto_resize();
	    _b_verifier.setxy(50, 43);
		_b_verifier.addActionListener(this);
	    _jp_principal.add(_b_verifier);
	    
	    /*************** Anagramme ***************/
	    _b_hintShuffle = new Tbt("Anagramme");
	    _b_hintShuffle.setFont(arista_light.deriveFont(Font.BOLD,15));
	    _b_hintShuffle.setGravity(GRAVITY.BOTTOM_LEFT);
	    _b_hintShuffle.setwh(75, 75);
	    _b_hintShuffle.auto_resize();
	    _b_hintShuffle.setxy(10, 90);
	    _b_hintShuffle.addActionListener(this);
	    _jp_principal.add(_b_hintShuffle);
	    
	    /*************** Nombre de lettres ***************/
	    _b_hintNbLetters = new Bt("Nombre de Lettre");
	    _b_hintNbLetters.setFont(arista_light.deriveFont(Font.BOLD,15));
	    _b_hintNbLetters.setGravity(GRAVITY.BOTTOM_RIGHT);
	    _b_hintNbLetters.setwh(75, 75);
	    _b_hintNbLetters.auto_resize();
	    _b_hintNbLetters.setxy(90, 90);
	    _b_hintNbLetters.addActionListener(this);
	    _jp_principal.add(_b_hintNbLetters);
	    
	    
	    /*************** Décourverte de lettres aléatoire ***************/
	    _b_hintColor = new Tbt("Découverte de lettres");
	    _b_hintColor.setFont(arista_light.deriveFont(Font.BOLD,15));
	    _b_hintColor.setGravity(GRAVITY.BOTTOM_CENTER);
	    _b_hintColor.setwh(75, 75);
	    _b_hintColor.auto_resize();
	    _b_hintColor.setxy(50, 90);
	    _b_hintColor.addActionListener(this);
	    _jp_principal.add(_b_hintColor);
	    
	    
	    /**********************Message pour indice sur même panel****************************/
	    _msg = new Txt();
    	_msg.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,25));
    	_msg.setForeground(new Color(0, 0, 0,255));
    	_msg.setGravity(GRAVITY.CENTER);	
    	_msg.setxy(50, 17);
    	_jp_principal.add(_msg);
    	_msg.setVisible(false);
    	
    	
    	/********************** Cpt Couleur ****************************/
    	_nbAnaCpt = new Txt();
		_nbAnaCpt.settext(_nbAna.toString());
		_nbAnaCpt.setFont(arista_light.deriveFont(Font.BOLD, 15));
		_nbAnaCpt.setForeground(new Color(0, 0, 0, 255));
		_nbAnaCpt.setGravity(GRAVITY.BOTTOM_LEFT);
		_nbAnaCpt.setxy(20, 90);
		_jp_principal.add(_nbAnaCpt);
    	_nbAnaCpt.setVisible(true);

		_AnaPan = new ArrayList<String>();
		
		/********************** Cpt Couleur ****************************/
		_nbColorCpt = new Txt();
		_nbColorCpt.settext(_nbColor.toString());
		_nbColorCpt.setFont(arista_light.deriveFont(Font.BOLD, 15));
		_nbColorCpt.setForeground(new Color(0, 0, 0, 255));
		_nbColorCpt.setGravity(GRAVITY.BOTTOM_LEFT);
		_nbColorCpt.setxy(58, 90);
		_jp_principal.add(_nbColorCpt);
		_nbColorCpt.setVisible(true);
	    
	    
	    /*************** Gestion de l'affichage des mots à trouver ***************/
	    List<Pa> words = new ArrayList<Pa>();
	    List<Pa> ALletters = new ArrayList<Pa>();
	    wordAna = new HashMap<>();
	   
		float wline=0,hline=0,wline2=0,hline2=0, wline3=0,hline3=0, wline4=0,hline4=0;

	    int i = 0;
		for(TweetWord word : _listword){
			
			Pa p = new Pa(null) {
			   private static final long serialVersionUID = 1L;
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Dimension arcs = new Dimension(15,15);
					int width = getWidth();
					int height = getHeight();
					Graphics2D graphics = (Graphics2D) g;
					graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					
					graphics.setColor(getBackground());
					graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
					graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
				  
				}
			};
			p.addMouseListener(this);
			p.setBounds(10,10,100,30);
			p.setOpaque(false);
			p.setBackground(new Color(29, 202, 255,255));
			  
			wordAna.put(word.getWord(), ""+shuffle(word.getWord()));
			Txt txt = new Txt(""+ word.getWord());
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,45));
			txt.setForeground(new Color(29, 202, 255,255));
			txt.setGravity(GRAVITY.CENTER);	
			_listword_label.add(txt);
			
			p.add(txt);
			p.setName(word.getWord());
				
			p.setwh(txt.getWidth()+15,txt.getHeight()+5);
			txt.setxyin(50,50,p.getWidth(),p.getHeight());
				
				
			words.add(p);
			
			
			
			
			/************Hints**********/
			Pa lettres = new Pa(null) {
				private static final long serialVersionUID = 1L;
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Dimension arcs = new Dimension(15,15);
					int width = getWidth();
					int height = getHeight();
					Graphics2D graphics = (Graphics2D) g;
					graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					
					graphics.setColor(getBackground());
					graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
					graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border       
				}
			};
			lettres.setBounds(10,10,100,30);
			lettres.setOpaque(false);
			lettres.setBackground(new Color(255, 255, 255,255));
			
			//Nombre de letttre
			Txt nbLetters = new Txt(" "+ word.getWord().length());
			nbLetters.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
			nbLetters.setForeground(new Color(255, 255, 255,255));
			nbLetters.setGravity(GRAVITY.CENTER_LEFT);
			
			//Nombre de points
			Txt nbPts = new Txt ("" + word.getPonderation());
			nbPts.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
			nbPts.setForeground(new Color(29, 202, 255,255));
			nbPts.setGravity(GRAVITY.CENTER_RIGHT);
			
			Txt tiret = new Txt ("-");
			tiret.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
			tiret.setForeground(new Color(29, 202, 255,255));
			tiret.setGravity(GRAVITY.CENTER);
			
			_listLetters.add(nbLetters);
			_listPts.add(nbPts);
			
			
			lettres.setwh(p.getWidth(), nbLetters.getHeight() + 5);
			
			nbPts.setxyin(95, 50, lettres);
			nbLetters.setxyin(5, 50, lettres);
			tiret.setxyin(50, 50, lettres);
			
			
			lettres.add(nbPts);
			lettres.add(nbLetters);
			lettres.add(tiret);
			
			ALletters.add(lettres);
			
			
			if(i < 5){
				wline += p.getWidth()+15;
				hline = (float) (p.getHeight()+lettres.getHeight()*1.5);
			}else {
				wline2 += p.getWidth()+15;
				hline2 = (float) (p.getHeight()+lettres.getHeight()*1.5);
			}
			  
			if(i < 5){
				wline3 += p.getWidth()+15;
				hline3 = hline;
			}else {
				wline4 += p.getWidth()+15;
				hline4 = hline2; 
			}
			
			i++;
		}
		
		
		//Placement des mots
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
	    	}
			
			i++;
		}
		
		//Placement du nb de lettres
		x_decalage =  0;
		y_de-= 1.5*(((float)hline+15)/(float)_screen.getHeight())*100;
		i=1;
		pline = new Pa(null);
		pline.setwh(wline3, hline3);
		pline.setGravity(GRAVITY.CENTER);
		for(Pa plettre : ALletters){
			
			plettre.setGravity(GRAVITY.TOP_LEFT);
			plettre.setxyin(x_decalage,0,pline.getWidth(),pline.getHeight());
	    	x_decalage +=(((float)plettre.getWidth()+15)/(float)pline.getWidth())*100;
	    			//(((float)words.get(i-1).getWidth()+15)/(float)pline.getWidth())*100;
	    	
			pline.add(plettre);
			
			if(i == 5 || i == 10){
	    		pline.setxy(50,y_de);
	    		pline.setOpaque(false);
	    		_jp_principal.add(pline);
	    		
	    		pline = new Pa(null);
	    		pline.setwh(wline2, hline3);
	    		pline.setGravity(GRAVITY.CENTER);
	    		x_decalage = 0;
	    		y_de += (((float)hline3+15)/(float)_screen.getHeight())*100;
	    	}
			
			i++;
		}
		
		
		_fenetre.repaint();
		
		
		show_windows();
	}

	
	/**
	 * reraw vie 
	 * Rafraichie les vies(oiseaux en haut a gauche) 
	 */
	protected void redraw_vie(){		
		 for(int i = 0;i<_difficulte.getvalue();i++){
			 Image myPicture = null;
		    	if(i < _nb_vie){
		    		myPicture = _image_vie;
		    	}else{
		    		myPicture = _image_mort;	    		    		
		    	}

		    	_vie.get(i).setIcon(new ImageIcon(myPicture));
		    }
		 _fenetre.repaint();
		 
		 if(_nb_vie == 0)
			 new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword,_hashtag,_j_local.getPoint());
	}
	/**
	 * vérifier les mots tapé par l'utilisateur
	 * @param mot_a_CTEO
	 */
	protected void verifier(String mot_a_CTEO){

		boolean dorepaite = true;
		player = new Player();
		mot_a_CTEO = mot_a_CTEO.trim();
		if(mot_a_CTEO.isEmpty()){
			_info_player.setText("Veuillez saisir un (ou plusieurs) mot !");
		    _info_player.auto_resize();
		}
		else {
			List<String> mots_a_CTEO = Arrays.asList(mot_a_CTEO.split(" "));
			String affichage  = "";
			for(String mot: mots_a_CTEO) {
				mot = TweetParser.cleanWord(mot);
				if(mot.isEmpty()) {
					affichage += "Un des mots proposé est invalide, il a été ignoré ! ";
					continue;
				}
	        	TweetWord motVerifie = _CTEO.isMotValid(mot);
	        	
	        	if(motVerifie.getPonderation() == -1){
	        		affichage += "Le mot " + mot + " est incorrect ! ";
	        		loose_vie();
	        	}else if(motVerifie.getPonderation() == -2){
	        		player.playBadAnswer();
	        		affichage += "Le mot " + mot + " a déja été proposé ! ";
	        	}else if(motVerifie.getPonderation() == -3){
	        		player.playBadAnswer();
	        		affichage += "Le mot " + mot  + " a déja été proposé et correspond à " + motVerifie.getWord() + " ! ";
	        	}else if(motVerifie.getPonderation() > 0){
	        		affichage += "Le mot " + motVerifie.getWord() + " est correct (" + motVerifie.getPonderation() + " points) !";
	        		setAnswer(motVerifie.getWord());
	        		_VerifPan.add(motVerifie.getWord());
	        		add_point(motVerifie.getPonderation(), motVerifie);
	        		_mots_trouver ++;
	        	}
	        	_info_player.setText(affichage);
	    	    _info_player.auto_resize();
	    	    
	        	if(_mots_trouver >= 10){
					new End_IHM(_fenetre, 1,(ArrayList<TweetWord>)_listword,_hashtag,_j_local.getPoint());
					dorepaite = false;
				}
				if(_nb_vie == 0){
					new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword,_hashtag,_j_local.getPoint());
					dorepaite = false;
				}
	        	_tf_saisie.setText("");
			}
		}
		
		if(dorepaite)
			_fenetre.repaint();
		
		
	}
	
	/**
	 * loose_vie
	 * enleve une vie au joueur
	 */
	protected void loose_vie(){
	 
		new Thread(new Runnable() {
			@Override
			public void run() {
				Player player = new Player();
				player.playDie();
				_nb_vie--;
				redraw_vie();
			}
		}).start();
	}
	/**
	 * add_point
	 * ajoute les point d'un mots a la somme de point total du joueur
	 * @param nb_point
	 * @param mots
	 */
	protected void add_point(int nb_point,TweetWord mots){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Player player = new Player();
				player.playGoodAnswer();
			}
		}).start();
		
		int i = 0;
		for(Txt label : _listword_label){
			if(label.getText().compareTo(mots.getWord()) == 0){
				label.setForeground(new Color(255,255,255));
				_listPts.get(i).setForeground(new Color(29, 202, 255,255));
				_fenetre.repaint();
				break;
			}
			i++;
		}
		_j_local.addPoint(nb_point);
		_compteur_de_point.setText("Points "+_j_local.getPoint());
		_compteur_de_point.auto_resize();
	}
	
/*************************Afficher les indices***********************************/	
	protected void show_hint_letters(){
		for (Txt l : _listLetters){
			l.setForeground(new Color(29, 202, 255,255));
		}
	}
	
	protected static String shuffle(String sentence) {
	    StringBuilder builder = new StringBuilder();
        List<Character> letters = new ArrayList<Character>();
        for (char letter : sentence.toCharArray()) {
            letters.add(letter);
        }
        if (letters.size() > 0) {
            Collections.shuffle(letters.subList(1, letters.size() - 1));
        }
        for (char letter : letters) {
            builder.append(letter);
        }
	    return builder.toString();
	}
	
	protected void mixWords(String pan_name){
		int j = 0;
		while(j < _listword_label.size()){
			if (_listword_label.get(j).getText().compareTo(pan_name) == 0){
				_listword_label.get(j).setText(wordAna.get(pan_name));
				_listword_label.get(j).setForeground(new Color(255, 255, 255, 255));
			}
			j++;
		}
	}
	
	protected void Color(String pan_name) {
	    StringBuilder builder = new StringBuilder();
        List<Character> letters = new ArrayList<Character>();
        int i = 0;
        
        for (char letter : pan_name.toCharArray()) {
            letters.add(letter);
            i++;
        }
        Random rand = new Random();
        
        ArrayList<Integer> random = new ArrayList<Integer>();
        int nb = (int)(i*0.45);
        while(nb != 0){
	        int r = 0;
	        boolean go=true;
	        while(go){
	            r = rand.nextInt(i + 1);
	            if(!random.contains(r))
	            	go = false;
	        }
	        random.add(r);
	        nb--;
        }
        int j = 0, r= 0;
		while(j < _listword_label.size()){
			if (_listword_label.get(j).getText().compareTo(pan_name) == 0){
				builder.append("<html>");
				for (char letter : _listword_label.get(j).getText().toCharArray()) {
					
						if(random.contains(r)){  
							builder.append( "<font color=rgb(255,255,255)>"+ letter + "</font>" );
						}
						else{
							builder.append(letter);
						}
					r++;
				}
				builder.append("</html>");
				_listword_label.get(j).setText(builder.toString());
			}
			j++;
		}
	}
/************************Mettre le mot correct ******************************/
	protected void setAnswer(String mot)
	{
		int i = 0;
		for(TweetWord w : _listword)
		{
			if (w.getWord().compareTo(mot) != 0)
			{
			i++;
			}
			else
			{
				_listword_label.get(i).setText(mot);
			}
		}
	}

	
	
	
	
	@Override
	/**
	 * gère le click sur un bouton
	 */
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
        if( e.getSource() == _b_verifier)
        	verifier( _tf_saisie.getText());
        
        	
        if( e.getSource() == _b_hintNbLetters){
        	show_hint_letters();
        }
        
        if( e.getSource() == _retourConfig){
        	new Config_IHM(_fenetre);
        }
	}
	
	public void mouseClicked(MouseEvent e) {
		
		if(_b_hintShuffle.isSelected()){
			Pa panel = (Pa)e.getSource();
		    String c = panel.getName();
			if (!_coloredPan.contains(c) && !_VerifPan.contains(c) && _nbAna > 0) {
				mixWords(c);
				_msg.setVisible(false);
				_nbAna--;
				if(_nbAna == 0){
					_b_hintShuffle.setSelected(false);
					_b_hintShuffle.setEnabled(false);
					_nbAnaCpt.setText("0");
				}
				else
				{
					_b_hintShuffle.setSelected(false);
					_b_hintShuffle.setEnabled(true);
					_nbAnaCpt.setText(_nbAna.toString());
				}
				_msg.setVisible(false);
				
		    }
		    else
		    {
		    	_msg.setText("Choisissez un autre mot");
		    	_msg.auto_resize();
		    	_msg.setVisible(true);
		    }
		}
		if( _b_hintColor.isSelected()){
			Pa panel = (Pa)e.getSource();
		    String pan_name = panel.getName();
			_coloredPan.add(pan_name);
			
			if(!_AnaPan.contains(pan_name) && _nbColor > 0 && !_VerifPan.contains(pan_name)){
				Color(pan_name);
				_nbColor --;
				if(_nbColor == 0){
				_b_hintColor.setSelected(false);
				_b_hintColor.setEnabled(false);
				_nbColorCpt.setText("0");
				}
				else
				{
					_b_hintColor.setSelected(false);
					_b_hintColor.setEnabled(true);
					_nbColorCpt.setText(_nbColor.toString());
				}
				_msg.setVisible(false);
				
			}
		} else {
			_msg.setText("Choisissez un autre mot");
			_msg.auto_resize();
        }
		
		
	    }

	@Override
	/**
	 * Keypressed
	 * gère les evenement des touche clavier préssée
	 */
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        	verifier(_tf_saisie.getText());
        /*************** Cheat pour afficher tout les mots non trouver ***************/
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
        	_maj++;
        	if(_maj >= 24){
        		for(Txt mot: _listword_label) {
        			if(mot.getForeground().getRed() == 29 && mot.getForeground().getGreen() == 202 && mot.getForeground().getBlue() == 255)
        				mot.setForeground(new Color(254, 255, 255,255));
    			}
        	}
        	_tab = 0;	
        }else
        	_maj = 0;
        
        if (e.getKeyCode() == KeyEvent.VK_EQUALS)
        	_b_hintShuffle.setSelected(true);
        

        /*************** Cheat pour cacher tout les mots non trouver ***************/
        if (e.getKeyCode()==KeyEvent.VK_UP){
        	_tab++;
        	if(_tab >= 24){
        		for(Txt mot: _listword_label) {
        			if(mot.getForeground().getRed() == 254 && mot.getForeground().getGreen() == 255 && mot.getForeground().getBlue() == 255)
        				mot.setForeground(new Color(29, 202, 255,255));
    			}
        	}
        	_maj = 0;	
        }else
        	_tab = 0;
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
