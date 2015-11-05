package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.portable.InputStream;

import controllers.CtrlTweetEnOr;
import utils.TweetParser;
import utils.TweetWord;


public class InGame_IHM extends IHM_Iterface implements ActionListener,KeyListener{

	private JTextField _tf_saisie;
	private JButton _b_verifier;
	
	private JLabel _txt;
	private JLabel _compteur_de_point;
	private JLabel _compteur_de_vie;
	private JLabel _hashtag;
	private ArrayList<JLabel> _vie;
	
	private JFrame _fram_given;
	
	private JPanel jp_sec;
	
	private Font arista_light;
	
	private BufferedImage _image_mort,_image_vie;
	
	
	private CtrlTweetEnOr _verifier;
	private  List<TweetWord> _listword;
	private  List<JLabel> _listword_label;
	
	
	private String hasttag;
	
	private int _nb_point;
	private int _nb_vie;
	private int _nb_vie_total;
	
	
	static int HARD=8;	
	static int MEDIUM=10;	
	static int EASY=15;
	

	public static void main(String[] args) throws FontFormatException, IOException{
		new InGame_IHM(10,"test",new JFrame());
	}
	

	
	
	
	public InGame_IHM(int Difficulte,String hastag_theme,JFrame fram) throws FontFormatException, IOException{
		_vie = new ArrayList<>();
		
		_image_mort = ImageIO.read(new File("./data/images/dead_bullet.png"));
		_image_vie = ImageIO.read(new File("./data/images/vie.png"));
	    
		_fram_given = fram;
	    hasttag = hastag_theme;
	    _nb_vie = Difficulte;
		_nb_vie_total = Difficulte;
		_nb_point = 0;
		
		_listword_label = new ArrayList<JLabel>();
	    _verifier = new CtrlTweetEnOr(hastag_theme);
		
		draw(0);
		
		
		
	    
	    
	    
		
	    _fenetre.setVisible(true);

	}
	
	public void draw(int redraw){
		
		if(redraw == 1){
			_fenetre.remove(_jp_principal);
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_inGame.jpg",_fenetre);
		}else{
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Jeu ","fond_inGame.jpg",_fram_given);
		}
	    _fenetre.addKeyListener(this);
		jp_sec = new JPanel();
		jp_sec.setOpaque(false);
		jp_sec.setLayout(new BoxLayout(jp_sec,BoxLayout.Y_AXIS));
		
		_jp_principal.add(jp_sec);
		
		
	    _tf_saisie = new JTextField(50);
	    _tf_saisie.setVisible(true);
	    
	    
	    Box spacer0 = new Box(BoxLayout.X_AXIS);
	    spacer0.setPreferredSize(new Dimension(40, 20));
	    jp_sec.add(spacer0);
	    
	    Box box = new Box(BoxLayout.X_AXIS);
	    box.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 50));

	    
	    try {
            //create the font to use. Specify the size!
            arista_light = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista-light.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/arista.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
            Font font = new Font("Arial", Font.BOLD, 12);
        }
	    _compteur_de_point = new JLabel("Points :"+_nb_point);	 
	    _compteur_de_point.setFont(arista_light.deriveFont(32));
	    _compteur_de_vie = new JLabel("vie :"+_nb_vie);
	    
	    box.add(Box.createRigidArea(new Dimension(20,58)));
	    
	    Box vie_box = new Box(BoxLayout.X_AXIS);
	   
	    for(int i = 0;i<_nb_vie_total;i++){
	    	BufferedImage myPicture = null;
	    	if(i < _nb_vie){
	    		myPicture = _image_vie;
	    	}else{
	    		myPicture = _image_mort;	    		    		
	    	}
	    	_vie.add(new JLabel(new ImageIcon(myPicture.getScaledInstance(45, 55, Image.SCALE_SMOOTH))));
	    	vie_box.add(_vie.get(_vie.size()-1));
	    }
	    box.add(vie_box);
	    box.add(Box.createGlue());
	    box.add(_compteur_de_point);
	    box.add(Box.createRigidArea(new Dimension(20,58)));

	    jp_sec.add(box);
	    
	    
	    
	    Box spacer1 = new Box(BoxLayout.X_AXIS);
	    spacer1.setPreferredSize(new Dimension(40, 200));
	    jp_sec.add(spacer1);
	    
	    
  
	    Box box2 = new Box(BoxLayout.X_AXIS);
	    box2.setMaximumSize(new Dimension(9999, 50));
	    box2.setMinimumSize(new Dimension(_fenetre.getSize().width, 50));

	    
	    _hashtag = new JLabel("#"+hasttag);
	    _hashtag.setForeground(Color.blue);
	   _hashtag.setFont(arista_light.deriveFont(Font.BOLD,72));
	   

	    box2.add(Box.createGlue());
	    box2.add(_hashtag);
	    box2.add(Box.createGlue());
	    
	    jp_sec.add(box2);
	    
	    Box spacer3 = new Box(BoxLayout.X_AXIS);
	    spacer3.setPreferredSize(new Dimension(40, 90));
	    jp_sec.add(spacer3);
	    
	    
	    

	    Box box3 = new Box(BoxLayout.X_AXIS);
	    box3.setMaximumSize(new Dimension(9999, 40));
	    box3.setMinimumSize(new Dimension(_fenetre.getSize().width, 40));
	    _tf_saisie.setMaximumSize(new Dimension(300,40));
	    _tf_saisie.setFont(new Font("",Font.TYPE1_FONT,20 ));
	    _tf_saisie.addKeyListener(this);
	    
	    box3.add(Box.createRigidArea(new Dimension(20,40)));
	    box3.add(Box.createGlue());
	    box3.add(_tf_saisie);
	    box3.add(Box.createGlue());
	    box3.add(Box.createRigidArea(new Dimension(20,40)));
	    

	    jp_sec.add(box3);
	    
	    

	    
	    Box box4 = new Box(BoxLayout.X_AXIS);
	    box4.setMaximumSize(new Dimension(9999, 0));
	    box4.setMinimumSize(new Dimension(_fenetre.getSize().width, 0));
	    
	    _txt = new JLabel("");
	    
	    box4.add(Box.createGlue());
	    box4.add(_txt);
	    box4.add(Box.createGlue());

	    jp_sec.add(box4);
	    
	    
	    
	    Box spacer5 = new Box(BoxLayout.X_AXIS);
	    spacer5.setPreferredSize(new Dimension(40, 20));
	    jp_sec.add(spacer5);
	    
	    

	    Box box5 = new Box(BoxLayout.X_AXIS);
	    box5.setPreferredSize(new Dimension(40, 75));
	    
	    
	    _b_verifier = new JButton("vérifier");
	    _b_verifier.setFont(arista_light.deriveFont(Font.BOLD,28));
	    _b_verifier.setPreferredSize(new Dimension(150, 75));
		_b_verifier.addActionListener(this);
	    
	    box5.add(Box.createGlue());
	    box5.add(_b_verifier);
	    box5.add(Box.createGlue());

	    jp_sec.add(box5);
	    

	    
	    Box spacer2 = new Box(BoxLayout.X_AXIS);
	    spacer2.setPreferredSize(new Dimension(10, 100));
	    jp_sec.add(spacer2);
	    
	    
	    
	    _listword = _verifier.getListWords();
	    System.out.println(_listword.size());
	    
	    Box box6 = new Box(BoxLayout.X_AXIS);
	    JPanel pgl = new JPanel(new FlowLayout());
	    pgl.setBackground(new Color(0, 0, 0, 0));
	    pgl.setMaximumSize(new Dimension(900, 900));
	    
	    box6.add(Box.createGlue());
	    box6.add(pgl);
	    box6.add(Box.createGlue());
	    
	    int i = 0;
		for(TweetWord word : _listword){
			i++;
	    	if(i == 6){
	    		i=0;
	    		jp_sec.add(box6);
	    		
	    		box6 = new Box(BoxLayout.X_AXIS);
	    	    pgl = new JPanel(new FlowLayout());
	    	    pgl.setBackground(new Color(0, 0, 0, 0));
	    	    pgl.setMaximumSize(new Dimension(900, 900));
	    	    
	    	    box6.add(Box.createGlue());
	    	    box6.add(pgl);
	    	    box6.add(Box.createGlue());
	    		
	    	}
	    	
			JPanel p = new JPanel() {
			     /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			     protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Dimension arcs = new Dimension(15,15);
			        int width = getWidth();
			        int height = getHeight();
			        Graphics2D graphics = (Graphics2D) g;
			        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			        graphics.setColor(getBackground());
			        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
			        graphics.setColor(getForeground());
			       // graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
			     }
			  };
			  p.setBounds(10,10,100,30);
			  p.setOpaque(false);
			
			  p.setBackground(new Color(29, 202, 255,255));
			
			pgl.add(p);
			JLabel txt = new JLabel(""+word.getWord());
			txt.setFont(new Font("",Font.BOLD,40 ));
			txt.setForeground(new Color(29, 202, 255,255));
			_listword_label.add(txt);
			p.add(txt);
		}
		if(i != 0)
			jp_sec.add(box6);
	
		
		_fenetre.repaint();
		
	}
	
	
	
	private void redraw_vie(){
		 for(int i = 0;i<_nb_vie_total;i++){
		    	BufferedImage myPicture = null;
		    	if(i < _nb_vie){
		    		myPicture = _image_vie;
		    	}else{
		    		myPicture = _image_mort;	    		    		
		    	}
		    	_vie.get(i).setIcon(new ImageIcon(myPicture.getScaledInstance(45, 55, Image.SCALE_SMOOTH)));
		    }
		 _fenetre.repaint();
	}
	
	
	
	
	public void verifier(String mots_a_verifier){
		mots_a_verifier = TweetParser.cleanWord(mots_a_verifier);
		if(!mots_a_verifier.isEmpty()){
        	_txt.setText(_tf_saisie.getText());
        	TweetWord mots = _verifier.isMotValid(mots_a_verifier);
        	
        	if(mots.getPonderation() == -1){
        		_txt.setText("Mot incorrect !");
        		loose_vie();
        	}else if(mots.getPonderation() == -2)
        		_txt.setText("Mot déja rentré !");
        	else if(mots.getPonderation() == -3)
        		_txt.setText("Mot déja rentré et correspond à "+mots.getWord()+" !");
        	else if(mots.getPonderation() > 0){
        		_txt.setText("Mot "+mots.getWord()+" correct ! Plus "+mots.getPonderation()+" points.");
        		add_point(mots.getPonderation(),mots);
        		
        	}
        	_tf_saisie.setText("");
    	}else{
    		_txt.setText("Veuillez entrer un mot valide !");
    	}
		
		if(_nb_point == 100){
			new End_IHM(_fenetre, 1,(ArrayList<TweetWord>)_listword);
		}
		if(_nb_vie == 0){
			new End_IHM(_fenetre, 0,(ArrayList<TweetWord>)_listword);
		}
		_fenetre.repaint();
		
	}
	
	
	private void loose_vie(){
		if(_nb_vie != 0){
			_nb_vie--;
			redraw_vie();
		}
	}
	private void add_point(int nb_point,TweetWord mots){
		
		for(JLabel label : _listword_label){
			if(label.getText().compareTo(mots.getWord()) == 0){
				label.setForeground(new Color(255,255,255));
				_fenetre.repaint();
				break;
			}
		}
		_nb_point += nb_point;
		_compteur_de_point.setText("Points "+_nb_point);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
        if( e.getSource() == _b_verifier)
        	verifier( _tf_saisie.getText());
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        	verifier(_tf_saisie.getText());
    }
	
	
}
