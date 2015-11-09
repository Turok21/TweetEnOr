package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ihm.components.Bt;
import ihm.components.Tbt;
import ihm.components.composent.GRAVITY;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class IHM_Iterface extends JFrame implements KeyListener,ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JFrame _fenetre;

	protected JPanel _jp_principal;
	
	protected Dimension _screen,_screenx,_screeny;
	
	protected Bt _quit;
	
	protected Font arista_light;
	protected Font arista_btn;
	protected Font arista;
	
	private String _fond_path;
		
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
        } 
        catch(FontFormatException | IOException e)
        {
            e.printStackTrace();
            arista_light = new Font("Arial", Font.BOLD, 12);
        }
		
		
		try {
            //create the font to use. Specify the size!
            arista_btn = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista-light.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (IOException | FontFormatException e)
        {
        	 e.printStackTrace();
        	 arista_btn = new Font("Arial", Font.BOLD, 12);
        }
		try {
            //create the font to use. Specify the size!
            arista = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")).deriveFont(38f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (IOException | FontFormatException e)
        {
        	 e.printStackTrace();
        	 arista = new Font("Arial", Font.BOLD, 12);
        }
		
		
	}
	

	
	protected JPanel load_fenetre_and_panel_principale(String title,String fond,JFrame fram){
		
		if(fram != null)
			fram.getContentPane().removeAll();
		
		_fenetre = fram;
		
		_fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		_fenetre.setTitle(title);
		_fenetre.addKeyListener(this);
		_fenetre.setFocusable(true);
	    
	   
		if(_fenetre.getExtendedState() != JFrame.MAXIMIZED_BOTH){
			_fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
			_fenetre.setUndecorated(true);
		}
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    
	    _fond_path = "./data/images/"+fond;
	    
	    _jp_principal = new JPanel();
		_jp_principal.addKeyListener(this);
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    
	    _jp_principal.setPreferredSize(_screen);
	    _jp_principal.setLayout(null);
		_jp_principal.setOpaque(false);

	    _fenetre.add(_jp_principal);
	    
	    _quit = new Bt("EXIT");
	    _quit.setGravity(GRAVITY.TOP_RIGHT);
	    _quit.setxy(100, 0);
	    _quit.addActionListener(this);
	    _jp_principal.add(_quit);
	    
	    return _jp_principal;
	}
	
	protected void show_windows(){
		
		JLabel image = new JLabel(
			new ImageIcon(
				new ImageIcon( _fond_path).getImage().getScaledInstance(_screen.width, _screen.height, Image.SCALE_SMOOTH)
			)
		);
		image.setBounds(0, 0, _screen.width, _screen.height);
		_jp_principal.add(image,null);
		
		_fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			close_all();
		}
	}
	
	protected void close_all(){
		_fenetre.dispose();
		_fenetre.setVisible(false);
		this.dispose();
		System.exit(0);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static JButton getTooltip() {
	    JButton b = new JButton("?");
	    b.setToolTipText(("<html><p width=\"500\">"
	    		+ "<strong>1 - Choisir un thème d'actualité</strong> <br />"
	    		+ "<strong>2 - Trouver les dix mots en rapport avec le thème</strong>"
	    		+ "<ul>"
	    		+ "<li>Tu perds une vie si le mot n'est pas present</li>"
	    		+ "<li>Tu gagnes les points correspondant au mot s'il est present</li>"
	    		+ "</ul>"
	    		+ "<strong>Ton nombre de vie dépends du niveau de difficulté</strong>"
	    		+ "<br /><strong>/Bonne partie !</strong>"
	    		+ "</p></html>"));
	    
	    return b;

	  }
	
	
	/*
	 * Play music
	 * only wav format
	 */
	public void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener((LineListener) this);
 
            audioClip.open(audioStream);
             
            audioClip.start();

            
			while (audioClip.isRunning()) {
                // wait for the playback completes
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
             
            audioClip.close();
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
         
    }
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _quit)
			close_all();
	}
	
}



class TestImagePanel extends JPanel {
	 
	private static final long serialVersionUID = 1L;
	private Image img;
 
	public TestImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
 
	public TestImagePanel(Image img) {
		this.img = img;
	}
 
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
}
