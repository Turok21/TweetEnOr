package ihm;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Accueil_IHM extends IHM_Iterface implements ActionListener,KeyListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



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
	    _fenetre.addKeyListener(this);
	    
	    JButton b1 = new JButton("v,cxbvjcsbn,vbcxnc");
		b1.setBounds(300,100,500,500);
		//_jp_principal.add(b1);
		
		
		_b_next = new JButton("next");
	    _b_next.setFont(arista_light.deriveFont(55));
	    _b_next.addActionListener(this);
	    _b_next.setBounds(300,100,500,500);
	    
	    Box box_button = new Box(BoxLayout.X_AXIS);
	    box_button.setPreferredSize(new Dimension((int) ((Toolkit.getDefaultToolkit().getScreenSize().height * 0.9)-50)
	    									   , ((int) (Toolkit.getDefaultToolkit().getScreenSize().height))));
		box_button.add(_b_next);
		_jp_principal.add( box_button);
		
	   /* _b_next.setBounds((int) ((Toolkit.getDefaultToolkit().getScreenSize().height * 0.9)-50)
	    		, ((int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2)-75)
	    		,150, 75);
	    */
	   // _jp_principal.add(_b_next);
	    
	    
		_jp_principal.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());


	    
	    
	   
	   /*
	    _box_spacer_dif = new Box(BoxLayout.Y_AXIS);
		_box_spacer_dif.setMaximumSize(new Dimension(9999, 200));
		_box_spacer_dif.setMinimumSize(new Dimension(_fenetre.getSize().width, _fenetre.getSize().height));
		_spacer = Box.createVerticalStrut(Toolkit.getDefaultToolkit().getScreenSize().height-_b_next.getHeight()-150);
		_box_spacer_dif.add(_spacer);
		System.out.println(""+_fenetre.getHeight());
		_box_spacer_dif.add(_b_next);
		_jp_principal.add(_box_spacer_dif);
		*/
			


    
		_fenetre.getContentPane().setVisible(true);
	    _fenetre.setVisible(true);
	}


	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_next)
			lauchconfig();
	}

	private void lauchconfig(){
		new Config_IHM(_fenetre);
	}


	


	

}

