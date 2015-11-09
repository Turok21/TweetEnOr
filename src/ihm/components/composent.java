package ihm.components;

import ihm.components.composent.GRAVITY;

public interface composent {
	
	public enum GRAVITY {
		  TOP_LEFT,
		  TOP_RIGHT,
		  CENTER;  
	};
	
	void setGravity(GRAVITY flag);
	void auto_resize();

	public void setxyin(float x,float y,int in_w,int in_h);
	public void setxy(float x,float y);
	public void setx(float x);
	public void sety(float y);
	
	public void setwh(float w,float h);
	public void setw(float w);
	public void seth(float h);
	
	
	public float getx();
	public float gety();
	public float geth();
	public float getw();

}
