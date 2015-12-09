package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import Sounds.Player;
import controllers.CtrlTweetEnOr;
import ihm.IHM_Iterface.LEVEL;
import ihm.components.Bt;
import ihm.components.Pa;
import ihm.components.Shared_component;
import ihm.components.Tbt;
import ihm.components.Tf;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;
import reseaux.AbstractUser;
import reseaux.DataType;
import reseaux.Server;
import twitter4j.TwitterException;
import utils.Joueur;
import utils.TweetParser;
import utils.TweetWord;

public class InGame_multi_IHM extends InGame_IHM{
	
	
	protected Joueur _j_distant;
	
	protected boolean _timer_is_running;
	boolean _go_watcher,_go_timer;
	Txt _timer;
	
	AbstractUser _au;
	
	@Override protected void load_vie_img(){}
	@Override protected void drawloader(int redraw){}
	@Override protected void load_game_data(){}
	@Override protected void redraw_vie(){}
	
	
	public static void main(String[] args) throws TwitterException, Exception{
		Shared_component shared = new Shared_component();
		shared._progressbar = new JProgressBar();
		shared.txt_line1 = new Txt(); 
		//try {
		//	new InGame_multi_IHM(new CtrlTweetEnOr("StarWars", shared),new Joueur("J1"),new Joueur("J2"),new JFrame());
		//} catch (Exception e) {};
		
	}
	
	
	 /**
	 * Constructeur 
	 * 
	 * @param fram
	 * @param j_local
	 * @param j_distant
	 * @throws IOException
	 */
	public InGame_multi_IHM(CtrlTweetEnOr cteo,Joueur j_local,Joueur j_distant,AbstractUser AU,JFrame fram) throws IOException {
		/*************** initialisation des variables ***************/
		super(LEVEL.EASY,cteo.getWord(),null);
		
		_fram_given = fram;
		
		_CTEO = cteo;
	    _hashtag = _CTEO.getWord();
	    _listword = _CTEO.getListWords();

	    
	    _au = AU;
	    
		_j_local = j_local;
		_j_distant = j_distant;


		_timer_is_running = false;
		
		
		draw_multiplayer_screen();//affiche l'ecran de jeu
		
		
		
		
		watch_connection();
		start_timer();
		

	}
	
	private void watch_connection(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				_go_watcher = true;
				while(_go_watcher){
					if(_au.newMessage()){
						_j_distant.setPoint(Integer.parseInt(_au._shared._data_hash.get(_au._shared._datatype).toString()));
						_compteur_de_point_adversaire.settext(_j_distant.getPseudo()+" : "+_j_distant.getPoint());
						if(_j_distant.getPoint() >= 100){
							_go_watcher = false;
							end_game();
						}
					}else {
						
						if(_go_watcher){
							_compteur_de_point_adversaire.settext("joueur déconnecté !");
							_j_distant.setPoint(-1);
						}
						end_game();
					}
				}
			}
		}).start();		
	}

	@Override protected void loose_vie(){
		 
		new Thread(new Runnable() {
			@Override
			public void run() {
				Player player = new Player();
				player.playDie();
			}
		}).start();
	}
	@Override protected void verifier(String mot_a_CTEO){

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
	        		add_point(motVerifie.getPonderation(), motVerifie);
	        		_mots_trouver ++;
	        		_au.sendObject(DataType.SCORE, _j_local.getPoint());
	        	}
	        	_info_player.setText(affichage);
	    	    _info_player.auto_resize();
	    	    
	        	if(_mots_trouver >= 10){
	        		end_game();
					dorepaite = false;
				}
	        	_tf_saisie.setText("");
			}
		}
		
		if(dorepaite)
			_fenetre.repaint();
		
		
	}
	
	private void start_timer(){
		
		if(!_timer_is_running){
			_timer_is_running = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					long limit = 10;
					long t0 = System.currentTimeMillis();
					long old = 0;
					_go_timer = true;
					while(((System.currentTimeMillis())-(t0)  < (limit)*1000 ) && _go_timer){
						
						long t = (System.currentTimeMillis())-(t0);
						
						long tm = (limit-(t/1000)-1);
						if(tm < 5 && old != tm){
							old = tm;
							_timer.setFont(arista.deriveFont(Font.TRUETYPE_FONT,30+((6-tm)*5)));
							_timer.setForeground(new Color((int)(125+((5-tm)*25)), (int)0, (int)0));
							_jp_principal.repaint();
						}
						
						
						_timer.settext((limit-(t/1000)-1) +"."+ ((100-(t%1000)/10)-1) );
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {}
					}
					if(_go_timer == true){
						end_game();
						_timer_is_running = false;
					}
				}
			}).start();
		}
		
	}
	
	public void end_game(){
		_go_timer = false;
		_go_watcher = false;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		_au.finalize();
		new End_IHM(_fenetre, _CTEO,_j_local,_j_distant);
	}
	
	
	
	public void draw_multiplayer_screen(){
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_Tweet_en_or.jpg",_fram_given,false);
		
	    _fenetre.addKeyListener(this);
		_jp_principal.addKeyListener(this);
		


	    
	    /*********** Timer ***********/
		 _timer = new Txt();
		 _timer.setxy(5, 10);
		 _timer.setGravity(GRAVITY.BOTTOM_LEFT);
		 _timer.setFont(arista.deriveFont(Font.TRUETYPE_FONT,30));
		 _jp_principal.add(_timer);

		 
	    /*************** _compteur_de_point ***************/
	    _compteur_de_point = new Txt(_j_local.getPseudo()+" : "+_j_local.getPoint());	 
	    _compteur_de_point.setFont(arista_light.deriveFont(32));
	    _compteur_de_point.setGravity(GRAVITY.TOP_RIGHT);
	    _compteur_de_point.setxy((float)95,(float)3);
	    _jp_principal.add(_compteur_de_point);
	    
	    /*************** _compteur_de_point ***************/ 
	    _compteur_de_point_adversaire = new Txt(_j_distant.getPseudo()+" : "+_j_distant.getPoint());	 
	    _compteur_de_point_adversaire.setFont(arista_light.deriveFont(32));
	    _compteur_de_point_adversaire.setGravity(GRAVITY.TOP_RIGHT);
	    _compteur_de_point_adversaire.setxy((float)95,(float)6);
	    _jp_principal.add(_compteur_de_point_adversaire);
	    
	    
	    
	    
	    

	    
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
	    _b_hintShuffle.setGravity(GRAVITY.CENTER_LEFT);
	    _b_hintShuffle.setwh(75, 75);
	    _b_hintShuffle.auto_resize();
	    _b_hintShuffle.setxy(10, 90);
	    _b_hintShuffle.addActionListener(this);
	    _jp_principal.add(_b_hintShuffle);
	    
	    /*************** Nombre de lettres ***************/
	    _b_hintNbLetters = new Bt("Nombre de Lettre");
	    _b_hintNbLetters.setFont(arista_light.deriveFont(Font.BOLD,15));
	    _b_hintNbLetters.setGravity(GRAVITY.CENTER_RIGHT);
	    _b_hintNbLetters.setwh(75, 75);
	    _b_hintNbLetters.auto_resize();
	    _b_hintNbLetters.setxy(90, 90);
	    _b_hintNbLetters.addActionListener(this);
	    _jp_principal.add(_b_hintNbLetters);
	    
	    
	    /*************** Décourverte de lettres aléatoire ***************/
	    _b_hintColor = new Tbt("Découverte de lettres");
	    _b_hintColor.setFont(arista_light.deriveFont(Font.BOLD,15));
	    _b_hintColor.setGravity(GRAVITY.CENTER);
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
		_nbAnaCpt.setGravity(GRAVITY.CENTER);
		_nbAnaCpt.setxy(20, 90);
		_jp_principal.add(_nbAnaCpt);
    	_nbAnaCpt.setVisible(true);

		_AnaPan = new ArrayList<String>();
		
		/********************** Cpt Couleur ****************************/
		_nbColorCpt = new Txt();
		_nbColorCpt.settext(_nbColor.toString());
		_nbColorCpt.setFont(arista_light.deriveFont(Font.BOLD, 15));
		_nbColorCpt.setForeground(new Color(0, 0, 0, 255));
		_nbColorCpt.setGravity(GRAVITY.CENTER);
		_nbColorCpt.setxy(60, 90);
		_jp_principal.add(_nbColorCpt);
		_nbColorCpt.setVisible(true);

		 _coloredPan = new ArrayList<String>();

	    
	    
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
		
		
		
		show_windows();
	}
	
	
	
	
	
	
}
