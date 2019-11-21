
package com.isaac.component;

import android.view.View;
import android.widget.ImageView;

public class Bullet {

	// =============================================================================================
	// Constants
	// =============================================================================================

	public static final int COUNT = 30;
	
	// =============================================================================================
	// Fields
	// =============================================================================================

	private static int count = 0;
	
	private ImageView image;
	private int direction;
	private boolean enable;
	
	// =============================================================================================
	// Constructors
	// =============================================================================================

	public Bullet(ImageView image) {
		this.image = image;
	}
	
	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Bullet.count = count;
	}

	public ImageView getImage() {
		return image;
	}
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public int getX() {
		return (int) image.getX();
	}
	
	public void setX(int x) {
		image.setX(x);
	}
	
	public int getY() {
		return (int) image.getY();
	}
	
	public void setY(int y) {
		image.setY(y);
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
		image.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
	}
	
	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	// =============================================================================================
	// Methods
	// =============================================================================================

	public boolean hitTest(Spider spider) {
		if(getX() < spider.getX() + spider.getWidth() && getX() + image.getWidth() > spider.getX() && getY() < spider.getY() + spider.getHeight() && getY() + image.getHeight() > spider.getY())
			return true;
		return false;
	}
	
	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================
	
}
