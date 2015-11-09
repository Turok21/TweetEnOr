package ihm;

import ihm.components.Bt;
import ihm.components.composent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

<<<<<<< HEAD
public class Accueil_IHM extends IHM_Iterface implements ActionListener, KeyListener {
=======
import Sounds.Player;

public class Accueil_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Player player;

	private JButton _b_next;
	private Component _spacer;
	private Box _box_spacer_dif;
	private JPanel _jp_principal;
	
	
	
	public static void main(String[] args) {
		new Accueil_IHM();
	}

	
	
	public Accueil_IHM(){
		super();

	    _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ","fond_Accueil.jpg",this);
	    _jp_principal.setPreferredSize(_screen);
	    _fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
	    _jp_principal.setLayout(null);
	    
 
	    
	    _b_next = new JButton("#NEXT");
	    _b_next.setFont(arista_light.deriveFont(55));
	    _b_next.addActionListener(this);
	    _b_next.setSize(150,50);
	    _b_next.setLocation((int) ((_screen.width/2)-(_b_next.getWidth()/2))
	    				   ,(int) (_screen.height-_b_next.getHeight()-(int)(_screen.height*0.1)));
	
		_jp_principal.add( _b_next);
		
		
	
		JLabel image = new JLabel(
			new ImageIcon(
				new ImageIcon( "./data/images/fond_Accueil.jpg").getImage().getScaledInstance(_screen.width, _screen.height, Image.SCALE_SMOOTH)
			)
		);
		image.setBounds(0, 0, _screen.width, _screen.height);
		_jp_principal.add(image,null);
		
		
		
		
	    
		
>>>>>>> refs/remotes/origin/master

    private static final long serialVersionUID = 1L;

    private Bt     _b_next;
    private JPanel _jp_principal;

    public static void main(String[] args) {
        new Accueil_IHM();
    }

    public Accueil_IHM() {
        super();

<<<<<<< HEAD
        _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ", "fond_Accueil.jpg", this,false);

        _b_next = new Bt("#NEXT");
        _b_next.setFont(arista_btn);
        _b_next.addActionListener(this);
        _b_next.setGravity(composent.GRAVITY.CENTER);
        _b_next.setSize(150, 50);
        _b_next.setxy(50, 75);
=======
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_next)
			player = new Player();
			player.playTwitter();
			lauchconfig();
	}
>>>>>>> refs/remotes/origin/master

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
        new Config_IHM(_fenetre);
    }

}

