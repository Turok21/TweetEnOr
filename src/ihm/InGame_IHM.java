package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.RenderingHints;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import Sounds.Player;
import controllers.CtrlTweetEnOr;
import ihm.components.Bt;
import ihm.components.Pa;
import ihm.components.Shared_component;
import ihm.components.Tf;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import utils.TweetParser;
import utils.TweetWord;


public class InGame_IHM extends IHM_Iterface implements ActionListener,KeyListener{

	private static final long serialVersionUID = 1L;

	private Tf _tf_saisie;
	
	private Bt _b_verifier;
	
	private Txt _loader;
	private Txt _info_player;
	private Txt _compteur_de_point;
	private Txt _hashtag;
	private ArrayList<Txt> _vie;
	private ArrayList<Txt> _listword_label;
	private ArrayList<Txt> _listLetters;
	
	private JFrame _fram_given;
	
	private Pa _Panel_loader;
	
	private Shared_component _shared;//objet d'échange avec CtrlTweetEnOr pour la bar de progression du loader
	
	private BufferedImage _Buffered_image_mort,_Buffered_image_vie;
	
	private Image _image_mort,_image_vie;
	
	private Player player;//gestionnaire des sons
	
	private CtrlTweetEnOr _verifier;
	
	private List<TweetWord> _listword;
	
	private String hasttag;
	
	private int _nb_point;
	private int _nb_vie;
	private int _nb_vie_total;
	private int _maj,_tab;//cheat afficher/cacher les mots
	
	

	public static void main(String[] args) throws FontFormatException, IOException{
		new InGame_IHM(LEVEL.MEDIUM,"test",new JFrame());
	}
	

	
	
	/**
	 * Constructeur 
	 * 
	 * @param Difficulte
	 * @param hastag_theme
	 * @param fram
	 * @throws IOException
	 */
	public InGame_IHM(LEVEL Difficulte,String hastag_theme,JFrame fram) throws IOException {
		/*************** initialisation des variables ***************/
		super();
		_shared = new Shared_component();
		_maj = 0;
		_tab = 0;
		_vie = new ArrayList<>();
		_fram_given = fram;
	    hasttag = hastag_theme;
	    _nb_vie = Difficulte.getvalue();
		_nb_vie_total = Difficulte.getvalue();
		_nb_point = 0;
		
		/*************** chargement des images pour les vies ***************/
		_Buffered_image_mort = ImageIO.read(new File("./data/images/dead_bullet.png"));
		_Buffered_image_vie = ImageIO.read(new File("./data/images/vie.png"));
		_image_mort = _Buffered_image_mort.getScaledInstance(45, 94, Image.SCALE_SMOOTH);
		_image_vie = _Buffered_image_vie.getScaledInstance(45, 94, Image.SCALE_SMOOTH);

		
		
		drawloader(0);//gestion de l'affichage du loader lors du chargement des tweets
		/*************** chargement des données de jeu(twwets) dans un thread à pars pour ne pas freezer le loader ***************/
		new Thread(new Runnable() {
			@Override
			public void run() {
				_listword_label = new ArrayList<Txt>();

				_listLetters = new ArrayList<Txt>();

			    try {
					_verifier = new CtrlTweetEnOr(hasttag,_shared);
				} catch (Exception e) {
					if(e instanceof IllegalStateException)
					{
						System.out.println("sdffffffffff");
					}
					e.printStackTrace();
				}

				_listword = _verifier.getListWords();
				
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
	public void drawloader(int redraw){
		
		
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
        
        
        
        /*************** Panel_loader contient tout les autre composents du loader ***************/
        _Panel_loader = new Pa(null);
        _Panel_loader.setSize(_screen);
        _Panel_loader.setBackground(new Color(40, 170, 225));
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
	public void draw_play_screen(int redraw){
		
		
		if(redraw == 1){
			_fenetre.remove(_jp_principal);
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fenetre,false);
		}else{
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fram_given,true);
		}
		
		
		
	    _fenetre.addKeyListener(this);
		_jp_principal.addKeyListener(this);
		
		
		 /*************** _tf_saisie ***************/
	    _tf_saisie = new Tf(50);
	    _tf_saisie.setVisible(true);
	    _tf_saisie.setwh((float)(_screen.width * 0.5), (float)50);
	    _tf_saisie.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,30));
	    _tf_saisie.addKeyListener(this);
	    _tf_saisie.setxy(50, 45);
	    _tf_saisie.setFocusable(true);
	    _jp_principal.add(_tf_saisie);

	    
	  
	    /*************** _compteur_de_point ***************/
	    _compteur_de_point = new Txt("Points :"+_nb_point);	 
	    _compteur_de_point.setFont(arista_light.deriveFont(32));
	    _compteur_de_point.setGravity(GRAVITY.TOP_RIGHT);
	    _compteur_de_point.setxy((float)98.5,(float)3);
	    _jp_principal.add(_compteur_de_point);
	    
	    
	    /*************** _vie  ***************/
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
	    

	    /*************** _hashtag ***************/
	    _hashtag = new Txt("#"+hasttag);
	    _hashtag.setForeground(Color.blue);
	    _hashtag.setFont(arista_light.deriveFont(Font.BOLD,72));
	    _hashtag.auto_resize();
	    _hashtag.setxy(50, 33);
	    _jp_principal.add(_hashtag);

	
	    /*************** _info_player déchange avec l'utilisateur ***************/
	    _info_player = new Txt("Entrez un mot en rapport avec ce hashtag !");
	    _info_player.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,35));
	    _info_player.auto_resize();
	    _info_player.setxy(50, 56);
	    _jp_principal.add(_info_player);
	    
	    
	    /*************** _b_verifier ***************/
	    _b_verifier = new Bt("vérifier");
	    _b_verifier.setFont(arista_light.deriveFont(Font.BOLD,28));
	    _b_verifier.setGravity(GRAVITY.CENTER);
	    _b_verifier.setwh(150, 75);
	    _b_verifier.auto_resize();
	    _b_verifier.setxy(50, 52);
		_b_verifier.addActionListener(this);
	    _jp_principal.add(_b_verifier);
	    
	 
	
	    
	    /*************** Gestion de l'affichage des mots à trouver ***************/
	    List<Pa> words = new ArrayList<Pa>();
	    List<Pa> ALletters = new ArrayList<Pa>();
		float wline=0,hline=0,wline2=0,hline2=0, wline3=0,hline3=0, wline4=0,hline4=0;

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
			       graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			       
			       
			       
			     }
			  };
		
		  p.setBounds(10,10,100,30);
	      p.setOpaque(false);
	      p.setBackground(new Color(29, 202, 255,255));
	      
			Txt txt = new Txt(""+word.getWord());
			txt.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,45));
			txt.setForeground(new Color(29, 202, 255,255));
			txt.setGravity(GRAVITY.CENTER);		
			_listword_label.add(txt);

			p.add(txt);
			p.setwh(txt.getWidth()+15,txt.getHeight()+5);
			System.out.println("taille "+txt.getWidth() +", " + txt.getHeight() );
			txt.setxyin(50,50,p.getWidth(),p.getHeight());
			
			
			words.add(p);
			
/***Nombre de lettres***/
			
			Pa lettres = new Pa(null) {
			     
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
			       graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			       
			       
			       
			     }
			  };
			  lettres.setBounds(10,10,100,30);
			  lettres.setOpaque(false);
			  lettres.setBackground(new Color(255, 255, 255,255));
			Txt nbLetters = new Txt(""+ word.getWord().length() + " - " +"nb");
			nbLetters.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
			nbLetters.setForeground(new Color(0, 0, 0,255));
			nbLetters.setGravity(GRAVITY.CENTER);		
			_listLetters.add(nbLetters);
			
			System.out.println(nbLetters.getText());
			 
			lettres.add(nbLetters);
			lettres.setwh(nbLetters.getWidth()+5,nbLetters.getHeight()+5);
			System.out.println("taille "+lettres.getWidth() +", " + lettres.getHeight() );
			nbLetters.setxyin(50,50,lettres.getWidth(),lettres.getHeight());
			
			ALletters.add(lettres);
			
			
			if(i < 5){
				wline += p.getWidth()+15;
				hline = p.getHeight()+15+lettres.getHeight();
			}else {
				wline2 += p.getWidth()+15;
				hline2 = p.getHeight()+45+lettres.getHeight();
			}
			  
			  
		
			
			if(i < 5){
				wline3 += p.getWidth()+15;
				hline3 = lettres.getHeight()+10+p.getHeight();
			}else {
				wline4 += p.getWidth()+15;
				hline4 = lettres.getHeight()+45+p.getHeight();
			}
			
			i++;
		}
		
		
		//Placement des mots
		float x_decalage = 0,y_de=70;
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
	    		//y_de += hline3 +15;
	    		System.out.println("mots" +y_de + " nb word" + words.size());
	    	}
			
			i++;
		}
		
		//Placement du nb de lettres
		x_decalage = 0;
		y_de=78;
		i=1;
		pline = new Pa(null);
		pline.setwh(wline, hline3);
		pline.setGravity(GRAVITY.CENTER);
		for(Pa plettre : ALletters){
			
			plettre.setGravity(GRAVITY.TOP_LEFT);
			plettre.setxyin(x_decalage,0,pline.getWidth(),pline.getHeight());
	    	x_decalage += (((float)words.get(i-1).getWidth()+15)/(float)pline.getWidth())*100;
	    	
			pline.add(plettre);
			
			if(i == 5 || i == 10){
	    		pline.setxy(50,y_de);
	    		pline.setOpaque(false);
	    		_jp_principal.add(pline);
	    		
	    		pline = new Pa(null);
	    		pline.setwh(wline, hline3);
	    		pline.setGravity(GRAVITY.CENTER);
	    		x_decalage = 0;
	    		y_de += (((float)hline3+15)/(float)_screen.getHeight())*100;
	    		System.out.println("lettre" + y_de);
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
		 
		 if(_nb_vie == 0)
			 new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword,hasttag,_nb_point);
	}
	/**
	 * vérifier les mots tapé par l'utilisateur
	 * @param mot_a_verifier
	 */
	public void verifier(String mot_a_verifier){

		boolean dorepaite = true;
		player = new Player();
		mot_a_verifier = mot_a_verifier.trim();
		if(mot_a_verifier.isEmpty()){
			_info_player.setText("Veuillez saisir un (ou plusieurs) mot !");
		    _info_player.auto_resize();
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
	        	}else if(motVerifie.getPonderation() == -2){
	        		player.playBadAnswer();
	        		affichage += "Le mot " + mot + " a déja été proposé ! ";
	        	}else if(motVerifie.getPonderation() == -3){
	        		player.playBadAnswer();
	        		affichage += "Le mot " + mot  + " a déja été proposé et correspond à " + motVerifie.getWord() + " ! ";
	        	}else if(motVerifie.getPonderation() > 0){
	        		affichage += "Le mot " + motVerifie.getWord() + " est correct (" + motVerifie.getPonderation() + " points) !";
	        		add_point(motVerifie.getPonderation(), motVerifie);
	        	}
	        	_info_player.setText(affichage);
	    	    _info_player.auto_resize();
	    	    
	        	if(_nb_point >= 100){
					new End_IHM(_fenetre, 1,(ArrayList<TweetWord>)_listword,hasttag,_nb_point);
					dorepaite = false;
				}
				if(_nb_vie == 0){
					new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword,hasttag,_nb_point);
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
	private void loose_vie(){
	 
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
		_compteur_de_point.auto_resize();
	}
	

	@Override
	/**
	 * gère le click sur un bouton
	 */
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        if( e.getSource() == _b_verifier)
        	verifier( _tf_saisie.getText());
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
        if (e.getKeyCode()==KeyEvent.VK_CAPS_LOCK){
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
}
