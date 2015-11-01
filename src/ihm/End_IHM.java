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

public class End_IHM extends JFrame implements ActionListener{
	
	
	private JFrame _fenetre;


	
	public static void main(String[] args){
		End_IHM ci = new End_IHM(new JFrame(""),1);
	}
	
	
	
	public End_IHM(JFrame fram,int fin) {
		

		_fenetre = fram;
	    _fenetre.getContentPane().removeAll();
		_fenetre.setTitle("Un Tweet en Or - Fin de partie ");
	    
	    _fenetre.setSize(800, 600);
	    _fenetre.setMinimumSize(new Dimension(800, 600));
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    JPanel _jp_principal = new JPanel();
	    _jp_principal.setBackground(Color.ORANGE);
	    _jp_principal.setLayout(new BoxLayout(_jp_principal, BoxLayout.Y_AXIS));
	    _fenetre.add(_jp_principal);
	    
	    
	    JPanel panel5 = new JPanel();
		JLabel text = new JLabel(""+fin);

		{

			GroupLayout panel5Layout = new GroupLayout(panel5);
			panel5.setLayout(panel5Layout);
			panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addContainerGap(371, Short.MAX_VALUE)
						.addComponent(text, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(345, Short.MAX_VALUE))
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(panel5Layout.createSequentialGroup()
						.addContainerGap(216, Short.MAX_VALUE)
						.addComponent(text, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(254, Short.MAX_VALUE))
			);
		}
		
		_jp_principal.add(panel5, BorderLayout.CENTER);
		
    
	    
	    _fenetre.setVisible(true);
	    

	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
	
        
	}


}
