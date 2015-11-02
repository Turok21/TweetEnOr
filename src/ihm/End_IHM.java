package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import controllers.CtrlTweetEnOr;
import utils.TweetWord;

public class End_IHM extends IHM_Iterface implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel _text;
	private JButton _b_again;

	
	public static void main(String[] args){
		
		CtrlTweetEnOr verifier = new CtrlTweetEnOr("test");
		ArrayList<TweetWord> listword = (ArrayList<TweetWord>) verifier.getListWords();
		new End_IHM(new JFrame(""),0,listword);
	}

	public End_IHM(JFrame fram,int fin,ArrayList<TweetWord> listword) {
		
		JPanel _jp_principal;
		if(fin == 0)
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Fail.jpg",fram);
		else
			_jp_principal = load_fenetre_and_panel_principale("Un Tweet en Or - Fin ","fond_Win.jpg",fram);
			
		JPanel jp_sec = new JPanel();
		jp_sec.setOpaque(false);
		jp_sec.setLayout(new BoxLayout(jp_sec,BoxLayout.Y_AXIS));
		_jp_principal.add(jp_sec);
	    
	    
	    JPanel panel5 = new JPanel();
	    panel5.setOpaque(false);
	    
	    
	    Box box6 = new Box(BoxLayout.X_AXIS);
	    JPanel pgl = new JPanel(new FlowLayout());
	    pgl.setBackground(new Color(0, 0, 0, 0));
	    pgl.setMaximumSize(new Dimension(900, 900));
	    
	    box6.add(Box.createGlue());
	    box6.add(pgl);
	    box6.add(Box.createGlue());
	    
		for(TweetWord word : listword){			
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
			
			 // p.setBackground(new Color(29, 202, 255,255));
			
			pgl.add(p);
			JLabel txt = new JLabel(""+word.getWord());
			txt.setFont(new Font("",Font.BOLD,24 ));
			txt.setForeground(new Color(200, 200, 200 ,255));
			jp_sec.add(txt);
			p.add(txt);
		}
		
		
	    
	    jp_sec.add(box6);
	    
	    
	    _text = new JLabel();
		_b_again = new JButton();
		_b_again.addActionListener(this);

		{

			//_text.setText(""+fin);
			_b_again.setText("Recommencer");

			GroupLayout panel5Layout = new GroupLayout(panel5);
			panel5.setLayout(panel5Layout);
			panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(panel5Layout.createSequentialGroup()
						.addGap(311, 311, 311)
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(_b_again)
							.addComponent(_text, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(349, Short.MAX_VALUE))
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(panel5Layout.createSequentialGroup()
						.addContainerGap(212, Short.MAX_VALUE)
						.addComponent(_text, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGap(29, 29, 29)
						.addComponent(_b_again, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(191, Short.MAX_VALUE))
			);
		}
		
		jp_sec.add(panel5, BorderLayout.CENTER);
		
    
	    
	    _fenetre.setVisible(true);
	    

	}

	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _b_again)
			new Config_IHM(_fenetre);
        
	}


}
