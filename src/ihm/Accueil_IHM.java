package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Accueil_IHM extends JFrame implements ActionListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JButton _b_feu;

	
	
	private JFrame _fenetre;
	

	
	public static void main(String[] args) {
		new Accueil_IHM();
	}
	
	
	
	public Accueil_IHM(){
		

	    _fenetre = new JFrame();
		_fenetre.setTitle("Un Tweet en Or - Accueil ");
	    
	    _fenetre.setSize(800, 600);
	    _fenetre.setMinimumSize(new Dimension(800, 600));
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    
	    JPanel _jp_principal = new JPanel();
	    _jp_principal.setBackground(Color.ORANGE);
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    

		_jp_principal.setLayout(new BorderLayout());
	    
	    _fenetre.add(_jp_principal);
	    
	    JPanel panel5 = new JPanel();
	    {
	    	_b_feu = new JButton("");
	    	_b_feu.addActionListener(this);
	    	_b_feu.setText("next");

			GroupLayout panel5Layout = new GroupLayout(panel5);
			panel5.setLayout(panel5Layout);
			panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(panel5Layout.createSequentialGroup()
						.addContainerGap(710, Short.MAX_VALUE)
						.addComponent(_b_feu)
						.addContainerGap())
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addContainerGap(497, Short.MAX_VALUE)
						.addComponent(_b_feu)
						.addContainerGap())
			);
		}
		_jp_principal.add(panel5, BorderLayout.CENTER);
	    
	    
    
	    
	    _fenetre.setVisible(true);
	    

	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_feu)
			lauchconfig();
	}
	
	
	private void lauchconfig(){
		new Config_IHM(_fenetre);
	}


}