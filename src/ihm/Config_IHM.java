package ihm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import ihm.components.Bt;
import ihm.components.Pa;
import ihm.components.Tbt;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;

public class Config_IHM extends IHM_Iterface implements ActionListener, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private Txt _title_frame, _title_dif, _title_theme, _msgNet, _help_txte;
    private Tbt _tbt_easy, _tbt_medium, _tbt_hard;
    private List<Tbt> _list_theme;
    private Bt        _b_play,_b_play_online, _b_help, _b_hide_help;
    private boolean is_net;
    private Pa _p_help_txte;

    LEVEL    _difficulte;
    String _hastag_theme;


    private static List<String> proposedKeywords = new ArrayList<>(Arrays.asList(
			"Ski", "Politique", "Russie", "GOT", "Syrie", "Migrants", "Mail", "Regionales", "Microsoft",
			"Apple", "Playstation", "Xbox", "Pollution", "AirCocaine", "Volkswagen", "France", "Fallout",
			"Noel", "Chine", "COP21", "NASA", "DonDuSang", "birmanie", "bière", "NSA", "PRISM", "espace",
			"JamesBond", "StarWars", "escalade", "escalade"
	));



    public static void main(String[] args) {
        new Config_IHM(new JFrame("test"));
    }

    public Config_IHM(JFrame fram) {    	
    	
    	
    	_difficulte = LEVEL.MEDIUM;

        load_fenetre_and_panel_principale("Un Tweet en Or - Config ", "fond_Tweet_en_or.jpg", fram,false);
        
        is_net = true;
		/*try {
			URL url = new URL("https://twitter.com/");
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.connect();
			if(	urlConn.getResponseCode() != 200){
				is_net = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			is_net = false;
		}
*/
        /*************** TITLE ***************/
        _title_frame = new Txt("Paramétrages");
        _title_frame.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 50));
        _title_frame.setSize(200, 30);
        _title_frame.setGravity(GRAVITY.CENTER);
        _title_frame.setxy(50, 5);
        _jp_principal.add(_title_frame);

        /*************** Help ***************/
        _b_help = new Bt("?");
        _b_help.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 40));
        _b_help.setGravity(GRAVITY.CENTER);
        _b_help.addActionListener(this);
        _b_help.auto_resize();
        _b_help.setxy(80, 5);
        _jp_principal.add(_b_help);
        
        _p_help_txte = new Pa(null);
        _p_help_txte.setSize(402,502);
        _p_help_txte.setGravity(GRAVITY.CENTER);
        _p_help_txte.setxy(50, 50);
        _p_help_txte.setOpaque(true);
        
        _help_txte = new Txt(new ImageIcon("./data/images/Rules.jpg"));
        _help_txte.addMouseListener(this);
        _help_txte.setGravity(GRAVITY.TOP_LEFT);
        _help_txte.auto_resize();
        _help_txte.setwh(402,502);
        _help_txte.setxy(0,0);
        
        _p_help_txte.add(_help_txte);
        
        _b_hide_help = new Bt("Cacher");
        _b_hide_help.addActionListener(this);
        _b_hide_help.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
        _b_hide_help.setGravity(GRAVITY.CENTER);
        _b_hide_help.auto_resize();
        _b_hide_help.setxy(58, 75);
        _jp_principal.add(_b_hide_help);
         
        
        _jp_principal.add(_p_help_txte);
        show_help(false);
        /*************** Difficulté ***************/
        _title_dif = new Txt("Difficulté :");
        _title_dif.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
        _title_dif.setGravity(GRAVITY.CENTER);
        _title_dif.setxy(20, 20);
        _jp_principal.add(_title_dif);

        _tbt_easy = new Tbt("Facile");
        _tbt_easy.setFont(arista_btn);
        _tbt_easy.addActionListener(this);
        _tbt_easy.addMouseListener(this);
        _tbt_easy.setGravity(GRAVITY.CENTER);
        _tbt_easy.auto_resize();
        _tbt_easy.setxy(40, 20);
        _jp_principal.add(_tbt_easy);

        _tbt_medium = new Tbt("Moyen");
        _tbt_medium.setSelected(true);      // TODO : Preselection difficulté moyen
        _tbt_medium.setFont(arista_btn);
        _tbt_medium.addActionListener(this);
        _tbt_medium.addMouseListener(this);
        _tbt_medium.setGravity(GRAVITY.CENTER);
        _tbt_medium.auto_resize();
        _tbt_medium.setxy(50, 20);
        _jp_principal.add(_tbt_medium);

        _tbt_hard = new Tbt("Difficile");
        _tbt_hard.setFont(arista_btn);
        _tbt_hard.addActionListener(this);
        _tbt_hard.addMouseListener(this);
        _tbt_hard.setGravity(GRAVITY.CENTER);
        _tbt_hard.auto_resize();
        _tbt_hard.setxy(60, 20);
        _jp_principal.add(_tbt_hard);
        /***************  Fin difficulté ***************/

        /*************** Themes ***************/
        _title_theme = new Txt("Thème  :");
        _title_theme.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
        _title_theme.setSize(100, 15);
        _title_theme.setGravity(GRAVITY.CENTER);
        _title_theme.setxy(20, 35);
        _jp_principal.add(_title_theme);

        _list_theme = new ArrayList<>();
        for(String keyword: proposedKeywords) {
        	_list_theme.add(new Tbt(keyword));
        	if(!is_net && !(new File("./cache/"+keyword+".ser").exists())){
        		_list_theme.get(_list_theme.size()-1).setEnabled(false);
        	}
        }


        int x = 30, y = 35,i=1;
        for (Tbt key : _list_theme) {
            key.setGravity(GRAVITY.CENTER);
            key.setSize(100, 30);
            key.setxy(x, y);
            key.addActionListener(this);
            key.addMouseListener(this);
            key.auto_resize();
            _jp_principal.add(key);
            
            x+=10;
            if (i == 6) {
            	x=30;
                y+=5;
                i=0;
            }
            i++;
        }
        /***************  Fin Themes ***************/

        /*************** Play ***************/
        _b_play = new Bt("#Jouer !");
        _b_play.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 35));
        _b_play.setGravity(GRAVITY.CENTER);
        _b_play.auto_resize();
        _b_play.setEnabled(false);
        _b_play.addActionListener(this);
        _b_play.addMouseListener(this);
        _b_play.setxy(40, 85);
        _jp_principal.add(_b_play);
        
        /*************** Play Online ***************/
        _b_play_online = new Bt("#Jouer en ligne !");
        _b_play_online.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 35));
        _b_play_online.setGravity(GRAVITY.CENTER);
        _b_play_online.auto_resize();
        _b_play_online.setEnabled(false);
        _b_play_online.addActionListener(this);
        _b_play_online.addMouseListener(this);
        _b_play_online.setxy(60, 85);
        _jp_principal.add(_b_play_online);
        
        
        /*************** Message no connection ***************/
        _msgNet = new Txt("Vous n'êtes pas connecté à internet");
        _msgNet.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 20));
        _msgNet.setSize(50, 15);
        _msgNet.auto_resize();
        _msgNet.setGravity(GRAVITY.CENTER);
        _msgNet.setxy(50, 70);
        
        if(!is_net){
        	_jp_principal.add(_msgNet);
        }
        	


        show_windows();

    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == _b_play) {
            lauchegame();
        } else if (e.getSource() == _b_play_online) {
        	lauche_online_game();
        } else if (e.getSource() == _tbt_easy) {
            _tbt_medium.setSelected(false);
            _tbt_hard.setSelected(false);
            _difficulte = LEVEL.EASY;
        } else if (e.getSource() == _tbt_medium) {
            _tbt_easy.setSelected(false);
            _tbt_hard.setSelected(false);
            _difficulte = LEVEL.MEDIUM;
        } else if (e.getSource() == _tbt_hard) {
            _tbt_easy.setSelected(false);
            _tbt_medium.setSelected(false);
            _difficulte = LEVEL.HARD;
        }else if(e.getSource() == _b_help){
        	show_help(true);
        }
        else if(e.getSource() == _b_hide_help){
            show_help(false);
        }else {
            for (Tbt lab : _list_theme) {
                if (e.getSource() == lab) {
                    _hastag_theme = lab.getText();
                    for (Tbt lab2 : _list_theme) {
                        if (lab2 != lab)
                            lab2.setSelected(false);
                    }
                    _b_play.setEnabled(true);
                    if(!is_net){
                    	_b_play_online.setEnabled(false);
                    }else {
                    	_b_play_online.setEnabled(true);
                    }
                }
            }
        }
        


    }
    
    private void show_help(boolean show)
    {
    	_p_help_txte.setVisible(show);
    	_b_hide_help.setVisible(show);
    }

    private void lauchegame() {
        try {
            new InGame_IHM(_difficulte, _hastag_theme, _fenetre);
        } catch (IOException e1) {}
    }
    
    private void lauche_online_game() {
    	new Multiplayer_IHM(_hastag_theme, _fenetre);
    }

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		show_help(false);
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {}
}

