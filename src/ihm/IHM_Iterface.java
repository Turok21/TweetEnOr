package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class IHM_Iterface extends JFrame implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JFrame _fenetre;

	protected JPanel _jp_principal;
		
	public IHM_Iterface() {
		
	}
	
	protected JPanel load_fenetre_and_panel_principale(String title,String fond,JFrame fram){
		
		if(fram != null)
			fram.getContentPane().removeAll();
		
		_fenetre = fram;
		_fenetre.setTitle(title);
		_fenetre.addKeyListener(this);
		_fenetre.setFocusable(true);
	    
	   
		if(_fenetre.getExtendedState() != JFrame.MAXIMIZED_BOTH){
			_fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
			_fenetre.setUndecorated(true);
		}
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    _jp_principal = new JPanel();
		_jp_principal.addKeyListener(this);
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    
	    JPanel panel_fond = null;
		try {
			panel_fond = setBackgroundImage(_fenetre, new File("./data/images/"+fond));
		} catch (IOException e) {e.printStackTrace();}

		panel_fond.addKeyListener(this);
		_jp_principal.setLayout(new BorderLayout());
		_jp_principal.setOpaque(false);
	    
		_fenetre.setContentPane(panel_fond);
	    _fenetre.add(_jp_principal);
	    
	    return _jp_principal;
	}
	
	protected  JPanel setBackgroundImage(JFrame frame, final File img) throws IOException
	{
		JPanel panel = new JPanel()
		{
			private static final long serialVersionUID = 1;
			
			private BufferedImage buf = ImageIO.read(img);
			
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				g.drawImage(buf.getScaledInstance( _fenetre.getWidth(), _fenetre.getHeight(), buf.SCALE_SMOOTH), 0,0, null);
			}
		};

		frame.setContentPane(panel);
		
		return panel;
	}

	@Override
	public void keyPressed(KeyEvent e) {	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			_fenetre.dispose();
			_fenetre.setVisible(false);
			this.dispose();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
