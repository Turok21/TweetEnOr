package ihm.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Bt extends JButton {
	

	private int _gravity,_center_x,_center_y;
	private Dimension _screen;
	//FLAG de Gravity
	static public int CENTER=1,TOP_LEFT=2,TOP_RIGHT=3;
	
	public Bt(){
		super("button");
		contruct();
	}
	public Bt(String text){
		super(text);
		contruct();
	}
	
	private void contruct(){
		_screen = Toolkit.getDefaultToolkit().getScreenSize();
		auto_resize();
	}
	
	public void setGravity(int flag){
		_gravity = flag;
		
		if(flag < 1 && flag > 3)
			_gravity = CENTER;
		auto_resize();
	}
	
	
	
	public void settext(String txt){
		super.setText(txt);
		auto_resize();
	}
	
	public void auto_resize(){
		FontMetrics metrics = getFontMetrics(getFont()); 
	    int width = metrics.stringWidth( getText() );
	    int height = metrics.getHeight();
	    Dimension newDimension =  new Dimension(width+40,height+10);
	    setPreferredSize(newDimension);
	    setBounds(new Rectangle(getLocation(), getPreferredSize()));
	    
	    if(_gravity == CENTER){
	    	_center_x = getWidth()/2;
	    	_center_y = getHeight()/2;
	    }else if(_gravity == TOP_RIGHT){
	    	_center_x = getWidth();
	    	_center_y = 0;
		}else{
	    	_center_x = 0;
	    	_center_y = 0;
	    }
	}	    
	
	

	public void setxyin(float x,float y,int in_w,int in_h){
		setLocation((int)(in_w*(x/100))-_center_x,(int)(in_h*(y/100))-_center_y);
	}
	public void setxy(float x,float y){
		setLocation((int)(_screen.width*(x/100))-_center_x,(int)(_screen.height*(y/100))-_center_y);
	}
	public void setx(float x){
		setxy((int) x+_center_x,getLocation().y-_center_y);
	}
	public void sety(float y){
		setxy(getLocation().x-_center_x,(int) y+_center_y);
	}
	
	public void setwh(float w,float h){
		setSize(new Dimension((int) w,(int) h));
	}
	public void setw(float w){
		setwh((int) w,getHeight());
	}
	public void seth(float h){
		setwh(getWidth(),(int) h);
	}
	
}