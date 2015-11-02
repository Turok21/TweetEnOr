package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	    
	  
	    JPanel panel2 = new JPanel();
	    JPanel panel5 = new JPanel();
	    _b_next = new JButton();	

	  //======== panel5 ========
	  		{
	  			panel5.setMaximumSize(new Dimension(1920, 1080));

	  			//======== panel2 ========
	  			{

	  				//---- _b_next ----
	  				_b_next.setText("next");

	  				GroupLayout panel2Layout = new GroupLayout(panel2);
	  				panel2.setLayout(panel2Layout);
	  				panel2Layout.setHorizontalGroup(
	  					panel2Layout.createParallelGroup()
	  						.addGroup(panel2Layout.createSequentialGroup()
	  							.addContainerGap()
	  							.addComponent(_b_next, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
	  							.addContainerGap())
	  				);
	  				panel2Layout.setVerticalGroup(
	  					panel2Layout.createParallelGroup()
	  						.addGroup(panel2Layout.createSequentialGroup()
	  							.addContainerGap()
	  							.addComponent(_b_next, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
	  							.addContainerGap())
	  				);
	  			}

	  			GroupLayout panel5Layout = new GroupLayout(panel5);
	  			panel5.setLayout(panel5Layout);
	  			panel5Layout.setHorizontalGroup(
	  				panel5Layout.createParallelGroup()
	  					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
	  						.addGap(0, 672, Short.MAX_VALUE)
	  						.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	  			);
	  			panel5Layout.setVerticalGroup(
	  				panel5Layout.createParallelGroup()
	  					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
	  						.addContainerGap(816, Short.MAX_VALUE)
	  						.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	  						.addGap(0, 0, 0))
	  			);
	  		}
	  		add(panel5, BorderLayout.CENTER);

	    
    
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

