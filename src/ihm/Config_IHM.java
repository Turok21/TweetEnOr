package ihm;

import java.awt.FontFormatException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.ToolTipManager;

import ihm.components.Bt;
import ihm.components.Tbt;
import ihm.components.Txt;
import ihm.components.composent.GRAVITY;

public class Config_IHM extends IHM_Iterface implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private Txt _title_frame, _title_dif, _title_theme;
    private Tbt _tbt_easy, _tbt_medium, _tbt_hard;
    private List<Tbt> _list_theme;
    private Bt        _b_play, _b_help;

    int    _difficulte;
    String _hastag_theme;

    static int EASY   = 15;
    static int MEDIUM = 10;
    static int HARD   = 8;

    public static void main(String[] args) {
        new Config_IHM(new JFrame("test"));
    }

    public Config_IHM(JFrame fram) {
    	
    	_difficulte = 10;

        load_fenetre_and_panel_principale("Un Tweet en Or - Config ", "fond_Tweet_en_or.jpg", fram,false);
        /*************** TITLE ***************/
        _title_frame = new Txt("Paramétrages");
        _title_frame.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 50));
        _title_frame.setSize(200, 30);
        _title_frame.setGravity(GRAVITY.CENTER);
        _title_frame.setxy(50, 5);
        _jp_principal.add(_title_frame);

        /*************** Help ***************/
        _b_help = getTooltip();
        _b_help.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 40));
        _b_help.setGravity(GRAVITY.CENTER);
        _b_help.addActionListener(this);
        _b_help.auto_resize();
        _b_help.setxy(80, 5);
        _jp_principal.add(_b_help);

        /*************** Difficulté ***************/
        _title_dif = new Txt("Difficulté :");
        _title_dif.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 30));
        _title_dif.setSize(100, 15);
        _title_dif.setGravity(GRAVITY.CENTER);
        _title_dif.setxy(20, 20);
        _jp_principal.add(_title_dif);

        _tbt_easy = new Tbt("Facile");
        _tbt_easy.setFont(arista_btn);
        _tbt_easy.addActionListener(this);
        _tbt_easy.setGravity(GRAVITY.CENTER);
        _tbt_easy.auto_resize();
        _tbt_easy.setxy(40, 20);
        _jp_principal.add(_tbt_easy);

        _tbt_medium = new Tbt("Moyen");
        _tbt_medium.setSelected(true);      // TODO : Preselection difficulté moyen
        _tbt_medium.setFont(arista_btn);
        _tbt_medium.addActionListener(this);
        _tbt_medium.setGravity(GRAVITY.CENTER);
        _tbt_medium.auto_resize();
        _tbt_medium.setxy(50, 20);
        _jp_principal.add(_tbt_medium);

        _tbt_hard = new Tbt("Difficile");
        _tbt_hard.setFont(arista_btn);
        _tbt_hard.addActionListener(this);
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
        _list_theme.add(new Tbt("Ski"));
        _list_theme.add(new Tbt("Politique"));
        _list_theme.add(new Tbt("Grec"));
        _list_theme.add(new Tbt("russie"));
        _list_theme.add(new Tbt("GOT"));
        _list_theme.add(new Tbt("Syrie"));
        _list_theme.add(new Tbt("Migrants"));
        _list_theme.add(new Tbt("Mail"));
        _list_theme.add(new Tbt("Informatique"));
        _list_theme.add(new Tbt("Microsoft"));
        _list_theme.add(new Tbt("Apple"));
        _list_theme.add(new Tbt("Playsation"));
        _list_theme.add(new Tbt("Xbox"));
        _list_theme.add(new Tbt("Réalité"));
        _list_theme.add(new Tbt("Pollution"));
        _list_theme.add(new Tbt("Aircocaine"));
        _list_theme.add(new Tbt("Volkswagen"));
        _list_theme.add(new Tbt("Pokemon"));
        _list_theme.add(new Tbt("France"));
        _list_theme.add(new Tbt("Licorne"));
        _list_theme.add(new Tbt("Fallout"));
        _list_theme.add(new Tbt("Noel"));
        _list_theme.add(new Tbt("Chine"));
        _list_theme.add(new Tbt("COP21"));
        _list_theme.add(new Tbt("NASA"));

        int tabX[] = {40, 50, 60, 70, 80};
        int tabY[] = {35, 45, 55, 65, 75};

        int x = 0, y = 0;
        for (Tbt key : _list_theme) {
            key.setGravity(GRAVITY.CENTER);
            key.setSize(100, 30);
            key.setxy(tabX[x % 5], tabY[y % 5]);
            key.addActionListener(this);
            _jp_principal.add(key);
            x++;
            if (x % 5 == 0) {
                y++;
            }
        }
        /***************  Fin Themes ***************/

        /*************** Play ***************/
        _b_play = new Bt("#Jouer !");
        _b_play.setFont(arista_light.deriveFont(Font.TRUETYPE_FONT, 35));
        _b_play.setGravity(GRAVITY.CENTER);
        _b_play.auto_resize();
        _b_play.setEnabled(false);
        _b_play.addActionListener(this);
        _b_play.setxy(50, 85);
        _jp_principal.add(_b_play);



        show_windows();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == _b_play) {
            lauchegame();
        } else if (e.getSource() == _tbt_easy) {
            _tbt_medium.setSelected(false);
            _tbt_hard.setSelected(false);
            _difficulte = Config_IHM.EASY;
        } else if (e.getSource() == _tbt_medium) {
            _tbt_easy.setSelected(false);
            _tbt_hard.setSelected(false);
            _difficulte = Config_IHM.MEDIUM;
        } else if (e.getSource() == _tbt_hard) {
            _tbt_easy.setSelected(false);
            _tbt_medium.setSelected(false);
            _difficulte = Config_IHM.HARD;
        }else {
        
            for (Tbt lab : _list_theme) {
                if (e.getSource() == lab) {
                    _hastag_theme = lab.getText();
                    for (Tbt lab2 : _list_theme) {
                        if (lab2 != lab)
                            lab2.setSelected(false);
                    }
                    _b_play.setEnabled(true);
                }
            }
        }
        


    }

    private void lauchegame() {
        try {
            new InGame_IHM(_difficulte, _hastag_theme, _fenetre);
        } catch (FontFormatException e1) {
        } catch (IOException e1) {}
    }
}

