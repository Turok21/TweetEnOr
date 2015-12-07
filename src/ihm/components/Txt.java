package ihm.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ihm.components.composent.GRAVITY;


/**
 * surdéfinition du JLabel
 * voir composent.java pour les commentaires
 * @author Guilhem Eyraud
 *
 */
public class Txt extends JLabel {
	
	private int _center_x,_center_y;
	private GRAVITY _gravity = GRAVITY.CENTER;
	private float _Px,_Py,_Ph,_Pw,_in_h,_in_w;
	private Dimension _screen;

	
			
	public Txt(String text){
		super(text);
		contruct();
		
	}
	
	public Txt(ImageIcon im){
		super(im);
		contruct();
		
		setwh(im.getIconWidth(),im.getIconHeight());

		_gravity = GRAVITY.CENTER;
		_screen = Toolkit.getDefaultToolkit().getScreenSize();
		
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

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if(_screen != null){
			auto_resize();
			setxy(_Px,_Py);
		}
	}
	
	public void auto_resize(){
		FontMetrics metrics = getFontMetrics(getFont()); 
		if(getText() != null){
		    int width = metrics.stringWidth( getText().replaceAll("\\<[^>]*>","") );
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
	    }else if(_gravity == GRAVITY.CENTER_LEFT){
	    	_center_x = 0;
	    	_center_y = getHeight()/2;
	    }else if(_gravity == GRAVITY.CENTER_RIGHT){
	    	_center_x = getWidth();
	    	_center_y = getHeight()/2;
	    }
	    
	    else if(_gravity == GRAVITY.TOP_CENTER){
	    	_center_x = getWidth()/2;
	    	_center_y = 0;
	    }else if(_gravity == GRAVITY.TOP_RIGHT){
	    	_center_x = getWidth();
	    	_center_y = 0;
	    }else if(_gravity == GRAVITY.TOP_LEFT){
	    	_center_x = 0;
	    	_center_y = 0;
	    }
	    
	    else if(_gravity == GRAVITY.BOTTOM_CENTER){
	    	_center_x = getWidth()/2;
	    	_center_y = getHeight();
	    }else if(_gravity == GRAVITY.BOTTOM_RIGHT){
	    	_center_x = getWidth();
	    	_center_y = getHeight();
	    }else if(_gravity == GRAVITY.BOTTOM_LEFT){
	    	_center_x = 0;
	    	_center_y = getHeight();
	    }
		
		setLocation((int)(_screen.width*(_Px/100))-_center_x,(int)(_screen.height*(_Py/100))-_center_y);

	}
	
	


	

	public void setxyin(float x,float y,int in_w,int in_h){
		_in_h = in_h;
		_in_w = in_w;
		_Px=x;
		_Py=y;
		apply_gravity();
		setLocation((int)(in_w*(x/100))-_center_x,(int)(in_h*(y/100))-_center_y);
	}
	public void setxyin(float x,float y,Component comp){
		setxyin(x, y, comp.getWidth(), comp.getHeight());
	}
	public void setxy(float x,float y){
		_in_h = _screen.height;
		_in_w = _screen.width;
		_Px=x;
		_Py=y;
		apply_gravity();
		setLocation((int)(_in_w*(x/100))-_center_x,(int)(_in_h*(y/100))-_center_y);
	}
	public void setx(float x){
		setxy( x,_Py);
	}
	public void sety(float y){
		setxy(_Px,y);
	}
	
	
	
	
	public void setwh(float w,float h){
		_Ph=h;
		_Pw=w;
		setSize(new Dimension((int) w,(int) h));
		apply_gravity();
	}
	public void setw(float w){
		setwh((int) w,getHeight());
		apply_gravity();
	}
	public void seth(float h){
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