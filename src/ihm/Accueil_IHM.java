package ihm;

import ihm.components.Bt;
import ihm.components.composent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import Sounds.Player;

public class Accueil_IHM extends IHM_Iterface implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private Bt     _b_next;
    private JPanel _jp_principal;

    public static void main(String[] args) {
        new Accueil_IHM();
    }

    public Accueil_IHM() {
        super();

        _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ", "fond_Accueil.jpg", this,false);

        _b_next = new Bt("#NEXT");
        _b_next.setFont(arista_btn);
        _b_next.addActionListener(this);
        _b_next.setGravity(composent.GRAVITY.CENTER);
        _b_next.setSize(150, 50);
        _b_next.setxy(50, 75);

        _jp_principal.add(_b_next);
        
        show_windows();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == _b_next)
            lauchconfig();
    }

    private void lauchconfig() {
    	new Thread(new Runnable() {
			public void run() {
				Player player = new Player();
		    	player.playTwitter();
			}
		}).start();
    	
        new Config_IHM(_fenetre);
    }

}
