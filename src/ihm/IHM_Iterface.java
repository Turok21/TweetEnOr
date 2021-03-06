package ihm;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import ihm.components.Bt;
import ihm.components.composent.GRAVITY;

/**
 * class servant de base a toute l'interface
 * @author Guilhem Eyraud
 *
 */
public class IHM_Iterface extends JFrame implements KeyListener, ActionListener, MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Enumeration des niveau de difficulter avec leur paramètre
     * @author Guilhem Eyraud
     *
     */
    public enum LEVEL {
		  EASY(15),
		  MEDIUM(10),
		  HARD(8);  
    	  private int value = 10;
    	  LEVEL(int val){
    	    this.value = val;
    	  }
    	  
    	  public int getvalue(){
    		  return value;
    	  }

	};

    protected JFrame _fenetre;

    protected JPanel _jp_principal;

    protected Dimension _screen, _screenx, _screeny;

    protected Bt _quit;

    protected Font arista_light;
    protected Font arista_btn;
    protected Font arista;

    private String _fond_path;

    /**
     * Constructeur
     * définie les variable de bases et surtout les font utiliser partous pour les texts !
     */
    public IHM_Iterface() {

        _screen = Toolkit.getDefaultToolkit().getScreenSize();
        _screenx = new Dimension(_screen.width, 0);
        _screeny = new Dimension(0, _screen.width);

        try {
            //create the font to use. Specify the size!
            arista_light = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista-light.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            arista_light = new Font("Arial", Font.BOLD, 12);
        }

        try {
            //create the font to use. Specify the size!
            arista_btn = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista-light.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            arista_btn = new Font("Arial", Font.BOLD, 12);
        }
        try {
            //create the font to use. Specify the size!
            arista = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")).deriveFont(38f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            arista = new Font("Arial", Font.BOLD, 12);
        }
		
		
	}
	


    /**
     * load_fenetre_and_panel_principale : 
     * crée( ou recrée si nécésaire) la fenetre et le panel principale d'affichage ,
     *  puis prépare les information pour charger l'image de fond
     * @param title
     * @param fond
     * @param fram
     * @param donotremove
     * @return
     */
    protected JPanel load_fenetre_and_panel_principale(String title, String fond, JFrame fram, boolean donotremove) {

        if (fram != null && !donotremove)
            fram.getContentPane().removeAll();

        _fenetre = fram;

        _fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _fenetre.setTitle(title);
        _fenetre.addKeyListener(this);
        _fenetre.setFocusable(true);

        GraphicsDevice gd =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (_fenetre.getExtendedState() != JFrame.MAXIMIZED_BOTH) {

           // _fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
           // _fenetre.setUndecorated(true);
            gd.setFullScreenWindow(_fenetre);
        }

        _fenetre.setLocationRelativeTo(null);

        _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        _fond_path = "./data/images/" + fond;

        _jp_principal = new JPanel();
        _jp_principal.addKeyListener(this);
        _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));

        _jp_principal.setPreferredSize(_screen);
        _jp_principal.setLayout(null);

        _jp_principal.setOpaque(false);
        
        _jp_principal.addMouseListener(this);

        _fenetre.add(_jp_principal);

        _quit = new Bt("EXIT");
        _quit.setGravity(GRAVITY.TOP_RIGHT);
        _quit.setxy(99, 1);
        _quit.addActionListener(this);
        _jp_principal.add(_quit);

        return _jp_principal;
    }
    /**
     * charge l'image darrière plan et l'affiche puis rend visible toutes l'interface doit etre appeler en dernier!
     * show_windows
     */
    protected void show_windows() {

        JLabel image = new JLabel(
                new ImageIcon(
                        new ImageIcon(_fond_path).getImage().getScaledInstance(_screen.width, _screen.height, Image.SCALE_SMOOTH)
                )
        );
        image.setBounds(0, 0, _screen.width, _screen.height);
        _jp_principal.add(image, null);

        _fenetre.getContentPane().setVisible(true);
        _fenetre.setVisible(true);
        _fenetre.getContentPane().repaint();
    }

    

    /**
     * quitte l'application et efface tout.
     */
    protected void close_all() {
        _fenetre.dispose();
        _fenetre.setVisible(false);
        this.dispose();
        System.exit(0);
    }

    /**
     * getTooltip 
     * 
     * @return un boutton contenant le tootip avec les regles du jeu
     */
    public static Bt getTooltip() {
    	ToolTipManager.sharedInstance().setInitialDelay(0);
        Bt b = new Bt("?");
        b.setToolTipText(("<html><p width=\"500\">"
                + "<strong>1 - Commence par choisir un thème d'actualité</strong> <br />"
                + "<strong>2 - Trouve les dix mots en rapport avec le thème</strong>"
                + "<ul>"
                + "<li>Tu perds une vie si le mot n'est pas présent</li>"
                + "<li>Tu gagnes les points correspondant au mot s'il est présent</li>"
                + "</ul>"
                + "<strong>Ton nombre de vie dépends du niveau de difficulté</strong>"
                + "<ul>"
                + "<li>EASY   : 15</li>"
                + "<li>MEDIUM : 10</li>"
                + "<li>HARD   :  8</li>"
                + "</ul>"
                + "<br /><strong>Bonne partie !</strong>"
                + "</p></html>"));

        return b;

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _quit)
            close_all();
    }
    @Override
    public void keyTyped(KeyEvent arg0) {}
    @Override
    public void keyPressed(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            close_all();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            close_all();
        }
    }



	@Override
	public void mouseClicked(MouseEvent e) {
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



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}


