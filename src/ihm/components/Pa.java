package ihm.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ihm.components.composent.GRAVITY;

public class Pa extends JPanel implements composent{
	

	private int _center_x,_center_y;
	private GRAVITY _gravity;
	private float _Px,_Py,_Ph,_Pw;
	private Dimension _screen;

	public Pa(){
		super();
		contruct();
	}
	public Pa(LayoutManager LM){
		super(LM);
		contruct();
	}
	
	private void contruct(){
		_gravity = GRAVITY.CENTER;
		_screen = Toolkit.getDefaultToolkit().getScreenSize();
		auto_resize();
	}
	
	public void setGravity(GRAVITY flag){
		_gravity = flag;
		auto_resize();
	}
	
	
	public void auto_resize(){
	    
	    apply_gravity();
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
		apply_gravity();
		setSize(new Dimension((int) w,(int) h));
	}
	public void setw(float w){
		_Pw=w;
		apply_gravity();
		setwh((int) w,getHeight());
	}
	public void seth(float h){
		_Ph=h;
		apply_gravity();
		setwh(getWidth(),(int) h);
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