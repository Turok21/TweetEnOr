package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;
import javax.xml.soap.Text;

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
import reseaux.Client;
import reseaux.Server;
import utils.KeyWord;
import utils.TweetWord;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Multiplayer_IHM extends IHM_Iterface implements ActionListener, KeyListener {
	
	/** */
	private static final long serialVersionUID = 1L;
	
	private Tf _tf_ip,_tf_port_joint,_tf_pseudo_joint, _tf_port_creat, _tf_pseudo_creat;
	
	private Tbt _b_create, _b_wait_client
				,_b_joint,_b_connexion;
	
	private Txt _loader, _progression;
	
	private JFrame _fram_given;
	
	private Pa _Panel_loader,_p_joint,_p_create,_p_loader;
	
	private Shared_component _shared;//objet d'échange avec CtrlTweetEnOr pour la bar de progression du loader
	
	private Player player;//gestionnaire des sons

	private CtrlTweetEnOr _verifier;
	private List<TweetWord> _listword;	
	private String _hashtag;
	private String pseudo;
	private int _porNumber;
	private Bt _b_again; 

	public static void main(String[] args) {
	     new Multiplayer_IHM("test",new JFrame("test"));
	}

	public Multiplayer_IHM(String hastag_theme,JFrame fram) {
		
		super();
		_shared = new Shared_component();
		_fram_given = fram;
	    _hashtag = hastag_theme;
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Configutation multiplayer","fond_reseau.jpg",_fram_given,false);
		
		
		
		/*************Progression **************/
		_progression = new Txt("Progression");
		_progression.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,24));
		_progression.setForeground(Color.BLACK);
		_progression.setGravity(GRAVITY.CENTER_LEFT);
		_progression.setxy(70, 65);
		_jp_principal.add(_progression);
		_progression.setVisible(false);
		
		/*************** Loader ***************/
		_loader = new Txt(new ImageIcon("./data/images/loader_ponte.gif"));
		_loader.setGravity(GRAVITY.CENTER);
		final int width = _loader.getWidth() + 10 ;
        	final int height = _loader.getHeight() + 10 ;
		_loader.setxyin(50,50,width,height);
		
		_p_loader = new Pa(null) {    
			protected void paintComponent(Graphics g) {
		    	super.paintComponent(g);
		    	Dimension arcs = new Dimension(15,15);
		    	Graphics2D graphics = (Graphics2D) g;
		    	graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    	graphics.setColor(getBackground());
		    	graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
		    	graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			}
		};
		_p_loader.setBackground(new Color(255, 255, 255, 255));
		_p_loader.setSize(width,height);
		_p_loader.setxy(75,50);
		_p_loader.setOpaque(false);  
		
		_p_loader.add(_loader);
		_p_loader.setVisible(false);
		_jp_principal.add(_p_loader);

		
		/*************** Bouton crée une partie online ***************/
		_b_create = new Tbt("Créer une partie en ligne.");
		_b_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
		_b_create.setGravity(GRAVITY.TOP_LEFT);
		_b_create.setxy(10, 10);
		_b_create.auto_resize();
		_b_create.addActionListener(this);
		_jp_principal.add(_b_create);
		
		/*************** Bouton rejoidre une partie online  ***************/
		_b_joint = new Tbt("Rejoidre une partie.");
		_b_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
		_b_joint.setGravity(GRAVITY.TOP_RIGHT);
		_b_joint.setxy(90, 10);
		_b_joint.auto_resize();
		_b_joint.addActionListener(this);
		_jp_principal.add(_b_joint);
		
		
		Txt hastag = new Txt("");
		hastag.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 40));
		hastag.settext("<html>Thème choisi: <font color='rgb(10,40,245)'>#"+_hashtag+"</font></html>");
		hastag.setxy(50,5);
		_jp_principal.add(hastag);
		
		
		
	/************************************ joint_ihm *********************************************/
		_p_joint = new Pa(null);
		_p_joint.setVisible(false);
		_p_joint.setxy(0, 20);
		_p_joint.setwh(_screen.width,(float) ( _screen.height-(_screen.height*(_p_joint.gety()/100)) ));
		_p_joint.setGravity(GRAVITY.TOP_LEFT);
		_p_joint.setOpaque(false);
		_jp_principal.add(_p_joint);
		
		
		_tf_pseudo_joint = new Tf();
		_tf_pseudo_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_pseudo_joint.setGravity(GRAVITY.CENTER_LEFT);
		_tf_pseudo_joint.auto_resize();
		_tf_pseudo_joint.setwh(100, 40);
		_tf_pseudo_joint.setxyin(10,5,_p_joint);		
		_p_joint.add(_tf_pseudo_joint);
		Txt txt_pseudo_joint = new Txt("Pseudo : ");
		txt_pseudo_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_pseudo_joint.setGravity(GRAVITY.CENTER_RIGHT);
		txt_pseudo_joint.auto_resize();
		txt_pseudo_joint.setxyin(10,5,_p_joint);
		_p_joint.add(txt_pseudo_joint);
		
		
		_tf_ip = new Tf();
		_tf_ip.setGravity(GRAVITY.CENTER_LEFT);
		_tf_ip.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_ip.setxyin(10,15,_p_joint);	
		_tf_ip.auto_resize();
		_tf_ip.setwh(300, 40);
		_p_joint.add(_tf_ip);
		Txt txt_ip_joint = new Txt("IP : ");
		txt_ip_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_ip_joint.setGravity(GRAVITY.CENTER_RIGHT);
		txt_ip_joint.setxyin(10,15,_p_joint);
		txt_ip_joint.auto_resize();
		_p_joint.add(txt_ip_joint);
		
		
		_tf_port_joint = new Tf();
		_tf_port_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_port_joint.setGravity(GRAVITY.CENTER_LEFT);
		_tf_port_joint.setxyin(10,25,_p_joint);
		_tf_port_joint.auto_resize();
		_tf_port_joint.setwh(100, 40);
		_p_joint.add(_tf_port_joint);
		Txt txt_port_joint = new Txt("Port : ");
		txt_port_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_port_joint.setGravity(GRAVITY.CENTER_RIGHT);
		txt_port_joint.setxyin(10,25,_p_joint);
		txt_port_joint.auto_resize();
		_p_joint.add(txt_port_joint);
			
		
		_b_connexion = new Tbt("Start connexion");
		_b_connexion.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_b_connexion.auto_resize();
		_b_connexion.setxyin(50,90,_p_joint);
		_b_connexion.addActionListener(this);
		_p_joint.add(_b_connexion);
		
		
	/************************************ create_ihm *********************************************/
		
		_p_create = new Pa(null);
		_p_create.setVisible(false);
		_p_create.setxy(0, 20);
		_p_create.setwh(_screen.width,(float) ( _screen.height-(_screen.height*(_p_joint.gety()/100)) ));
		_p_create.setGravity(GRAVITY.TOP_LEFT);
		_p_create.setOpaque(false);
		_jp_principal.add(_p_create);
		
		_tf_pseudo_creat = new Tf();
		_tf_pseudo_creat.setGravity(GRAVITY.CENTER_LEFT);
		_tf_pseudo_creat.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_pseudo_creat.auto_resize();
		_tf_pseudo_creat.setwh(100, 40);
		_tf_pseudo_creat.setxyin(10,5,_p_create);
		_p_create.add(_tf_pseudo_creat);
		
		Txt txt_pseudo_create = new Txt("Pseudo : ");
		txt_pseudo_create.setGravity(GRAVITY.CENTER_RIGHT);
		txt_pseudo_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_pseudo_create.auto_resize();
		txt_pseudo_create.setOpaque(false);
		txt_pseudo_create.setxyin(10,5,_p_create);
		_p_create.add(txt_pseudo_create);
		
		_tf_port_creat = new Tf();
		_tf_port_creat.setGravity(GRAVITY.CENTER_LEFT);
		_tf_port_creat.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_port_creat.setxyin(10,15,_p_create);
		_tf_port_creat.auto_resize();
		_tf_port_creat.setwh(100, 40);
		_p_create.add(_tf_port_creat);
		
		Txt txt_port_create = new Txt("Port : ");
		txt_port_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_port_create.setGravity(GRAVITY.CENTER_RIGHT);
		txt_port_create.setxyin(10,15,_p_create);
		txt_port_create.auto_resize();
		_p_create.add(txt_port_create);
			
		_b_wait_client = new Tbt("Start serveur");
		_b_wait_client.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_b_wait_client.auto_resize();
		_b_wait_client.setxyin(50,90,_p_create);
		_b_wait_client.addActionListener(this);
		_p_create.add(_b_wait_client);
		
		
		/*************** chargement et paramétrage du loader twitter  ***************/
        _loader = new Txt(new ImageIcon("./data/images/Loader_twitter.gif"));
        _loader.setxy(50, 35);
        _loader.setOpaque(false);
		_jp_principal.add(_loader); 
		
		/*************** text d'informations sous la bar de progression ***************/
        _shared.txt_line1 = new Txt();
        _shared.txt_line1.setGravity(GRAVITY.CENTER);
        _shared.txt_line1.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,24));
        _shared.txt_line1.setForeground(Color.white);
        _shared.txt_line1.settext("chargement des info");
        _shared.txt_line1.setxy(50, 64);
		_jp_principal.add(_shared.txt_line1);
        
       
        /*************** ProgressBar ***************/
        _shared._progressbar = new JProgressBar();
        _shared._progressbar.setSize(500,30);
        _shared._progressbar.setForeground(new Color(29, 202, 255,255));
        _shared._progressbar.setLocation((int)((_screen.width/2)-(_shared._progressbar.getSize().width*0.5))
        		, (int)((_screen.height*0.6)-(_shared._progressbar.getSize().height/2)));
		_jp_principal.add(_shared._progressbar);
		
		_jp_principal.repaint();
		show_loadin_global_info(true);
	
		
		
		_b_again = new Bt();
		_b_again.addActionListener(this);
		_b_again.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT,20));
		_b_again.setText("Ecran de Configuration");
		_b_again.setGravity(GRAVITY.CENTER);
		_b_again.setxy(10,90);
		_jp_principal.add(_b_again);
        
		
		show_windows();
	}
	
	void show_loadin_global_info(boolean show)
	{
        _loader.setVisible(show);
        _shared.txt_line1.setVisible(show);
        _shared._progressbar.setVisible(show);
	}
	
	private void show_joint_ihm(){
		_p_create.setVisible(false);
		_p_joint.setVisible(true);
	}
	
	
	private void show_create_ihm(){
		_p_joint.setVisible(false);
		_p_create.setVisible(true);
		
	}
	
	/**
	 * Controle des parametre de création d'une partie 
	 */
	private boolean control_creat()
	{    	
		if(port_creat_control() && pseudo_creat_controle())
		{
    		_p_loader.setVisible(true);
    		_progression.setVisible(true);
    		_progression.setText("Progression");
    		_b_joint.setEnabled(false);
    		_b_wait_client.setText("Arreter");
    		_b_again.setVisible(false);
    		
    		return true;
		}
		else
		{
			_b_wait_client.setSelected(false);
			return false;
		}
	}
	
	private boolean join_control()
	{	
		if(pseudo_joint_controle() && port_joint_control() && ip_controle())
		{		
			_p_loader.setVisible(true);
    		_progression.setVisible(true);
    		_progression.setText("Progression");
    		_b_create.setEnabled(false);
    		_b_connexion.setText("Arreter");
    		_b_again.setVisible(false);
			return true;
		}else{
			_b_connexion.setSelected(false);
			return false;
		}
	}
	
	private boolean pseudo_creat_controle()
	{
		Pattern rx_pseudo = Pattern.compile("^[0-9]+[aA-zZ]|^[aA-zZ]|[0-9]");
    	Matcher rx_status = rx_pseudo.matcher(_tf_pseudo_creat.getText());
    	if(rx_status.find()){
    		_tf_pseudo_creat.setBackground(Color.WHITE);
    		_tf_pseudo_creat.setEditable(false);
    		return true;
    	}
    	else{
    		_tf_pseudo_creat.setBackground(Color.RED);
    		_tf_pseudo_creat.setToolTipText("Nom d'utilisateur attandu");
    		return false;
    	}
	}
	
	private boolean pseudo_joint_controle()
	{
		Pattern rx_pseudo = Pattern.compile("^[0-9]+[aA-zZ]|^[aA-zZ]|[0-9]");
    	Matcher rx_status = rx_pseudo.matcher(_tf_pseudo_joint.getText());
    	if(rx_status.find()){
    		_tf_pseudo_joint.setEditable(false);
    		_tf_pseudo_joint.setBackground(Color.WHITE);
    		return true;
    	}
    	else{
    		_tf_pseudo_joint.setBackground(Color.RED);
    		_tf_pseudo_joint.setToolTipText("Nom d'utilisateur attandu");
    		return false;
    	}
	}
	
	private boolean ip_controle()
	{
		Pattern rx_pseudo = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}");
    	Matcher rx_status = rx_pseudo.matcher(_tf_ip.getText());
    	if(rx_status.find()){
    		_tf_ip.setEditable(false);
    		_tf_ip.setBackground(Color.WHITE);
    		return true;
    	}
    	else{
    		_tf_ip.setBackground(Color.RED);
    		_tf_ip.setToolTipText("L'adresse IP n'est pas valide");
    		return false;
    	}
	}
	
	
	private boolean port_creat_control()
	{
		Pattern rx_port_number = Pattern.compile("^[0-9]+$");
    	Matcher rx_status = rx_port_number.matcher(_tf_port_creat.getText());
    	if(rx_status.find()){
    		if(Integer.parseInt(_tf_port_creat.getText()) > 1024)
    		{
	        	_tf_port_creat.setEditable(false);
	        	_tf_port_creat.setBackground(Color.WHITE);
	        	return true;
    		}else{
    			_tf_port_creat.setBackground(Color.red);
    			_tf_port_creat.setToolTipText("Le numero de port doit être superieure à 1024");
    			return false;
    		}
    		
    	}else{
    		_tf_port_creat.setBackground(Color.red);
			_tf_port_creat.setToolTipText("Un nombre entier est attandu !");
			return false;
    	}
	}
	
	private boolean port_joint_control()
	{
		Pattern rx_port_number = Pattern.compile("^[0-9]+$");
    	Matcher rx_status = rx_port_number.matcher(_tf_port_joint.getText());
    	if(rx_status.find()){
    		if(Integer.parseInt(_tf_port_joint.getText()) > 1024)
    		{
    			_tf_port_joint.setEditable(false);
    			_tf_port_joint.setBackground(Color.WHITE);
	        	return true;
    		}else{
    			_tf_port_joint.setBackground(Color.red);
    			_tf_port_joint.setToolTipText("Le numero de port doit être superieure à 1024");
    			return false;
    		}
    		
    	}else{
    		_tf_port_joint.setBackground(Color.red);
    		_tf_port_joint.setToolTipText("Un nombre entier est attandu !");
			return false;
    	}
	}
	
	private void cancel_create() {
		_p_loader.setVisible(false);
		_b_wait_client.setText("Start serveur");
		_b_create.setEnabled(true);
		_tf_port_creat.setEditable(true);
		_tf_pseudo_creat.setEditable(true);
		_b_joint.setEnabled(true);
		_progression.setVisible(false);
		_b_again.setVisible(true);
	}
	
	private void cancel_joint() {
		_p_loader.setVisible(false);
		_b_connexion.setText("Start connexion");
		_b_joint.setEnabled(true);
		_b_create.setEnabled(true);
		_progression.setVisible(false);
		_tf_ip.setEditable(true);
		_tf_port_joint.setEditable(true);
		_tf_pseudo_joint.setEditable(true);
		_b_again.setVisible(true);
	}

	@Override
	/**
	 * gère le click sur un bouton
	 */
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        
		if( e.getSource() == _b_joint ){
			if(!_b_joint.isSelected()){
	        	_b_joint.setSelected(true);
			}else{
				_b_create.setSelected(false);
				show_joint_ihm();
			}
        }else if( e.getSource() == _b_create ){
			if(!_b_create.isSelected()){
				_b_create.setSelected(true);
	        }else{
	        	_b_joint.setSelected(false);
	        	show_create_ihm();
			}
        }else if( e.getSource() == _b_wait_client ){
        	if(_b_wait_client.isSelected())
        	{
        		if(control_creat()){
        			new Thread(new Runnable() {
        				@Override
        				public void run() {
        					Server se = new Server(Integer.parseInt(_tf_port_creat.getText()),_shared);
        					if(!se.create_server()){
	    						cancel_joint();
	    						_progression.setVisible(true);
	    						_progression.settext("echec de création du serveur...");
	    					}else{
	    						_progression.settext("Serveur créer, en attente de connexion ...");
	    						if(se.wait_client()){
	    							_progression.settext("client connecté");
	    							 
	    							//se.initData(_tf_pseudo_creat.getText(), _hashtag);
	    							
	    							try {
										CtrlTweetEnOr cteo= new CtrlTweetEnOr(_hashtag,_shared);
									} catch (Exception e) {
										e.printStackTrace();
									}
	    							
	    							//KeyWord key = new KeyWord("bière", ll);
	    					        //srv.server_sendKeyword(key);
	    							
	    						}else{
	    							_progression.settext("connexion client en echec...");
	    						}
	    						
	    					}
        				}
        			}).start();
        			
        		}
        		
        	}else{
        		cancel_create();
        	}
        }else if( e.getSource() == _b_connexion ){
        	if(_b_connexion.isSelected())
        	{
        		
        		if(join_control()){
        			
	        		new Thread(new Runnable() {
	    				@Override
	    				public void run() {
	    					Client cl = new Client(_tf_ip.getText(),Integer.parseInt(_tf_port_joint.getText()),_shared);    				
	    					if(!cl.connect()){
	    						cancel_joint();
	    						_progression.settext("echec de connexion... aucun serveur en ecoute");
	    						_shared.txt_line1.settext("echec de connexion... aucun serveur en ecoute");
	    					}else{
	    						cl.initData(_tf_pseudo_joint.getText(), _hashtag);
	    						
	    						//loadin_global_info("");
	    					}
	    				}
	    			}).start();
        		}
        	}else{
        		cancel_joint();
        	}
        }else if(e.getSource() == _b_again){
			new Config_IHM(_fenetre);
        }
        
	}
}
