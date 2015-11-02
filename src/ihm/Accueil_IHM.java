package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class Accueil_IHM extends JFrame implements ActionListener{

	private JTextField _tf_saisie;
	private JButton _b_feu;
	private JFrame _fenetre;

	public static void main(String[] args) {
		Accueil_IHM ci = new Accueil_IHM();
	}

	public Accueil_IHM() {
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
	    	_b_feu.setText("text");

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
		try {
			new Config_IHM(_fenetre);
		} catch (FontFormatException e1) {} catch (IOException e1) {}
	}
}
