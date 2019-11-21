package com.isaac.component;

import com.isaac.PlayActivity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class Player {

	// =============================================================================================
	// Constants
	// =============================================================================================

	public static final int COUNT = 100;
	PlayActivity playat;
	// =============================================================================================
	// Fields
	// =============================================================================================

	private ImageView image;
	private int mode;
	private Drawable[][] motion;
	
	private int count;
	
	private boolean enable;
	
	// =============================================================================================
	// Constructors
	// =============================================================================================

	public Player(ImageView image) {
		this.image = image;
		motion = new Drawable[3][13];
	}
	
	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	public ImageView getImage() {
		return image;
	}
	
	public void setImageDrawable(int type) {
		image.setImageDrawable(motion[mode][type]);
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
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Drawable[][] getMotion() {
		return motion;
	}

	public void setMotion(Drawable[][] motion) {
		this.motion = motion;
	}
	
	public void setMotion(Drawable motion, int mode, int type) {
		this.motion[mode][type] = motion;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
