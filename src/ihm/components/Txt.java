package ihm.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ihm.components.composent.GRAVITY;

public class Txt extends JLabel {
	
	private int _center_x,_center_y;
	private GRAVITY _gravity = GRAVITY.CENTER;
	private float _Px,_Py,_Ph,_Pw;
	private Dimension _screen;

	
			
	public Txt(String text){
		super(text);
		contruct();
		
	}
	
	public Txt(ImageIcon im){
		super(im);
		contruct();
		
		setwh(im.getIconWidth(),im.getIconHeight());
		
	}
	
	public Txt(){
		super();
		contruct();
	}

	
	private void contruct(){
		_Px = 0;
		_Py = 0;
		_Ph = 0;
		_Pw = 0;
		
		_gravity = GRAVITY.CENTER;
		_screen = Toolkit.getDefaultToolkit().getScreenSize();
		auto_resize();
	}
	
	public void setGravity(GRAVITY flag){
		_gravity = flag;
		auto_resize();
	}
	
	
	
	public void settext(String txt){
		super.setText(txt);
		
		auto_resize();
	}
	
	public void auto_resize(){
		FontMetrics metrics = getFontMetrics(getFont()); 
		if(getText() != null){
		    int width = metrics.stringWidth( getText() );
		    int height = metrics.getHeight();
		    Dimension newDimension =  new Dimension(width+0,height+10);
		    setPreferredSize(newDimension);
		    setBounds(new Rectangle(getLocation(), newDimension));
		    apply_gravity();
		}
	}
	
	private void apply_gravity(){
		
		if(_gravity == GRAVITY.CENTER){
	    	_center_x = getWidth()/2;
	    	_center_y = getHeight()/2;
	    }else if(_gravity == GRAVITY.TOP_RIGHT){
	    	_center_x = getWidth();
	    	_center_y = 0;
	    }else if(_gravity == GRAVITY.TOP_LEFT){
	    	_center_x = 0;
	    	_center_y = 0;
	    }
		
		setLocation((int)(_screen.width*(_Px/100))-_center_x,(int)(_screen.height*(_Py/100))-_center_y);
	}
	
	

	public void setxyin(float x,float y,int in_w,int in_h){
		_Px=x;
		_Py=y;
		apply_gravity();
		setLocation((int)(in_w*(x/100))-_center_x,(int)(in_h*(y/100))-_center_y);
	}
	public void setxy(float x,float y){
		_Px=x;
		_Py=y;
		apply_gravity();
		setLocation((int)(_screen.width*(x/100))-_center_x,(int)(_screen.height*(y/100))-_center_y);
	}
	public void setx(float x){
		_Px=x;
		apply_gravity();
		setxy((int) x+_center_x,getLocation().y-_center_y);
	}
	public void sety(float y){
		_Py=y;
		apply_gravity();
		setxy(getLocation().x-_center_x,(int) y+_center_y);
	}
	
	public void setwh(float w,float h){
		_Ph=h;
		_Pw=w;
		setSize(new Dimension((int) w,(int) h));
		apply_gravity();
	}
	public void setw(float w){
		_Pw=w;
		setwh((int) w,getHeight());
		apply_gravity();
	}
	public void seth(float h){
		_Ph=h;
		setwh(getWidth(),(int) h);
		apply_gravity();
	}
	
	
	public float getx(){
		return _Px;
	}
	public float gety(){
		return _Py;
	}
	public float geth(){
		return _Ph;
	}
	public float getw(){
		return _Pw;
	}
}