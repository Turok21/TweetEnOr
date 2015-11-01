package ihm;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class End_IHM extends JFrame implements ActionListener{
	
	
	private JFrame _fenetre;


	
	public static void main(String[] args) throws FontFormatException, IOException{
		End_IHM ci = new End_IHM();
	}
	
	
	
	public End_IHM() throws FontFormatException, IOException{
		

	    _fenetre = new JFrame();
		_fenetre.setTitle("Un Tweet en Or - Fin de partie ");
	    
	    _fenetre.setSize(800, 600);
	    _fenetre.setMinimumSize(new Dimension(800, 600));
	    
	    _fenetre.setLocationRelativeTo(null);
	    
	    _fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	           
	    
    
	    
	    _fenetre.setVisible(true);
	    

	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
	
        
	}


}
