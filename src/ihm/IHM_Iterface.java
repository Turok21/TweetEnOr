package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class IHM_Iterface extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JFrame _fenetre;
	
	public IHM_Iterface() {
		
	}
	
	protected JPanel load_fenetre_and_panel_principale(String title,String fond,JFrame fram){
		
		
		fram.getContentPane().removeAll();
		
		_fenetre = fram;
		_fenetre.setTitle(title);
	    
	    //_fenetre.setSize(800, 600);
	    _fenetre.setMinimumSize(new Dimension(1000, 700));
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		 JPanel _jp_principal = new JPanel();
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    
	    JPanel panel_fond = null;
		try {
			panel_fond = setBackgroundImage(_fenetre, new File("./data/images/"+fond));
		} catch (IOException e) {e.printStackTrace();}
	  /*  
	    ImageIcon ic = new ImageIcon("./data/images/"+fond);
	    Image image = ic.getImage();
	   
	    JPanel panel_fond = new TestImagePanel( image.getScaledInstance(_fenetre.getWidth(), _fenetre.getHeight(), Image.SCALE_SMOOTH));
	    panel_fond.setOpaque(true);
		*/
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
