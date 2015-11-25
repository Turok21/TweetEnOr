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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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
import utils.TweetWord;

public class Multiplayer_IHM extends IHM_Iterface implements ActionListener, KeyListener {
	
	/** */
	private static final long serialVersionUID = 1L;
	
	private Tf _tf_ip,_tf_port,_tf_pseudo;
	
	private Tbt _b_create, _b_wait_client
				,_b_joint,_b_connexion;
	
	private Txt _loader;
	
	private JFrame _fram_given;
	
	private Pa _Panel_loader,_p_joint,_p_create,_p_loader;
	
	private Shared_component _shared;//objet d'échange avec CtrlTweetEnOr pour la bar de progression du loader
	
	private Player player;//gestionnaire des sons

	private CtrlTweetEnOr _verifier;
	private List<TweetWord> _listword;	
	private String _hasttag;
	

	public static void main(String[] args) {
	     new Multiplayer_IHM("test",new JFrame("test"));
	}

	public Multiplayer_IHM(String hastag_theme,JFrame fram) {
		
		super();
		_shared = new Shared_component();
		_fram_given = fram;
	    _hasttag = hastag_theme;
		
		_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Configutation multiplayer","fond_Tweet_en_or.jpg",_fram_given,false);
		
		
		
		
		
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
		_b_create = new Tbt("créer une partie en ligne.");
		_b_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
		_b_create.setGravity(GRAVITY.TOP_LEFT);
		_b_create.setxy(10, 10);
		_b_create.auto_resize();
		_b_create.addActionListener(this);
		_jp_principal.add(_b_create);
		
		/*************** Bouton rejoidre une partie online  ***************/
		_b_joint = new Tbt("rejoidre une partie.");
		_b_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
		_b_joint.setGravity(GRAVITY.TOP_RIGHT);
		_b_joint.setxy(90, 10);
		_b_joint.auto_resize();
		_b_joint.addActionListener(this);
		_jp_principal.add(_b_joint);
		
		
		Txt hastag = new Txt("<html>Thème choisi: <font color='rgb(10,40,245)'>#"+_hasttag+"</font></html>");
		hastag.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 50));
		hastag.auto_resize();
		hastag.setxy(50,5);
		_jp_principal.add(hastag);
		
		
		
	/************************************ joint_ihm *********************************************/
		_p_joint = new Pa(null);
		_p_joint.setVisible(false);
		_p_joint.setxy(00, 20);
		_p_joint.setwh(_screen.width,(float) ( _screen.height-(_screen.height*(_p_joint.gety()/100)) ));
		_p_joint.setGravity(GRAVITY.TOP_LEFT);
		_p_joint.setOpaque(false);
		_jp_principal.add(_p_joint);
		
		
		_tf_pseudo = new Tf();
		_tf_pseudo.setGravity(GRAVITY.TOP_LEFT);
		_tf_pseudo.setxyin(10,5,_p_joint.getWidth(),_p_joint.getHeight());
		_tf_pseudo.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_pseudo.auto_resize();
		_tf_pseudo.setwh(100, 40);
		_p_joint.add(_tf_pseudo);
		Txt txt_pseudo_joint = new Txt("Pseudo : ");
		txt_pseudo_joint.setGravity(GRAVITY.TOP_RIGHT);
		txt_pseudo_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_pseudo_joint.auto_resize();
		txt_pseudo_joint.setxyin(10,5,_p_joint.getWidth(),_p_joint.getHeight());
		_p_joint.add(txt_pseudo_joint);
		
		
		_tf_ip = new Tf();
		_tf_ip.setGravity(GRAVITY.TOP_LEFT);
		_tf_ip.setxyin(10,15,_p_joint.getWidth(),_p_joint.getHeight());
		_tf_ip.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_ip.auto_resize();
		_tf_ip.setwh(300, 40);
		_p_joint.add(_tf_ip);
		Txt txt_ip_joint = new Txt("IP : ");
		txt_ip_joint.setGravity(GRAVITY.TOP_RIGHT);
		txt_ip_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_ip_joint.auto_resize();
		txt_ip_joint.setxyin(10,15,_p_joint.getWidth(),_p_joint.getHeight());
		_p_joint.add(txt_ip_joint);
		
		
		
		_tf_port = new Tf();
		_tf_port.setGravity(GRAVITY.TOP_LEFT);
		_tf_port.setxyin(10,25,_p_joint.getWidth(),_p_joint.getHeight());
		_tf_port.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_port.auto_resize();
		_tf_port.setwh(100, 40);
		_p_joint.add(_tf_port);
		Txt txt_port_joint = new Txt("Port : ");
		txt_port_joint.setGravity(GRAVITY.TOP_RIGHT);
		txt_port_joint.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_port_joint.auto_resize();
		txt_port_joint.setxyin(10,25,_p_joint.getWidth(),_p_joint.getHeight());
		_p_joint.add(txt_port_joint);
		
		
		
		_b_connexion = new Tbt("Start connexion");
		_b_connexion.setxyin(50,90,_p_joint.getWidth(),_p_joint.getHeight());
		_b_connexion.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_b_connexion.auto_resize();
		_b_connexion.addActionListener(this);
		_p_joint.add(_b_connexion);
		
		
		
	/************************************ create_ihm *********************************************/
		
		_p_create = new Pa(null);
		_p_create.setVisible(false);
		_p_create.setxy(00, 20);
		_p_create.setwh(_screen.width,(float) ( _screen.height-(_screen.height*(_p_joint.gety()/100)) ));
		_p_create.setGravity(GRAVITY.TOP_LEFT);
		_p_create.setOpaque(false);
		_jp_principal.add(_p_create);
		
		
		
		_tf_pseudo = new Tf();
		_tf_pseudo.setGravity(GRAVITY.TOP_LEFT);
		_tf_pseudo.setxyin(10,5,_p_joint.getWidth(),_p_joint.getHeight());
		_tf_pseudo.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_pseudo.auto_resize();
		_tf_pseudo.setwh(100, 40);
		_p_create.add(_tf_pseudo);
		
		Txt txt_pseudo_create = new Txt("Pseudo : ");
		txt_pseudo_create.setGravity(GRAVITY.TOP_RIGHT);
		txt_pseudo_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_pseudo_create.auto_resize();
		txt_pseudo_create.setxyin(10,5,_p_create.getWidth(),_p_create.getHeight());
		_p_create.add(txt_pseudo_create);
		
		
		
		
		
		_tf_port = new Tf();
		_tf_port.setGravity(GRAVITY.TOP_LEFT);
		_tf_port.setxyin(10,15,_p_joint.getWidth(),_p_joint.getHeight());
		_tf_port.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_tf_port.auto_resize();
		_tf_port.setwh(100, 40);
		_p_create.add(_tf_port);
		
		Txt txt_port_create = new Txt("Port : ");
		txt_port_create.setGravity(GRAVITY.TOP_RIGHT);
		txt_port_create.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		txt_port_create.auto_resize();
		txt_port_create.setxyin(10,15,_p_create.getWidth(),_p_create.getHeight());
		_p_create.add(txt_port_create);
		
		
		_b_wait_client = new Tbt("Start serveur");
		_b_wait_client.setxyin(50,90,_p_create.getWidth(),_p_create.getHeight());
		_b_wait_client.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
		_b_wait_client.auto_resize();
		_b_wait_client.addActionListener(this);
		_p_create.add(_b_wait_client);
		
		
		
		
		
		
		
		show_windows();
	}
	
	private void show_joint_ihm(){
		_p_create.setVisible(false);
		_p_joint.setVisible(true);
	}
	
	
	private void show_create_ihm(){
		_p_joint.setVisible(false);
		_p_create.setVisible(true);
		
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
        	_p_loader.setVisible(true);
        }else if( e.getSource() == _b_connexion ){
        	_p_loader.setVisible(true);
        }
		

        
	}
}
