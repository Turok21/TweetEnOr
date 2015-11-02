package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Accueil_IHM extends IHM_Iterface implements ActionListener{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private JButton _b_next;
	
	public static void main(String[] args) {
		new Accueil_IHM();
	}

	
	
	public Accueil_IHM(){
		
  
	    JPanel _jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Accueil ","fond_Accueil.jpg",this);
	    
	    
	    JPanel panel5 = new JPanel();
	    panel5.setOpaque(false);
	    {
	    	_b_next = new JButton("");
	    	_b_next.addActionListener(this);
	    	_b_next.setText("next");

			GroupLayout panel5Layout = new GroupLayout(panel5);
			panel5.setLayout(panel5Layout);
			panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(panel5Layout.createSequentialGroup()
						.addContainerGap(710, Short.MAX_VALUE)
						.addComponent(_b_next)
						.addContainerGap())
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addContainerGap(497, Short.MAX_VALUE)
						.addComponent(_b_next)
						.addContainerGap())
			);
		}
		_jp_principal.add(panel5, BorderLayout.CENTER); 
	    
    
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

