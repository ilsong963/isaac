package com.isaac.component;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class Spider {

	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private DisplayMetrics dm = new DisplayMetrics();
	private ImageView image;

	private int countx, county;
	private int plusx = (int) ((Math.random() * 9) - 4);
	private int plusy = (int) ((Math.random() * 9) - 4);

	private boolean enable;

	// =============================================================================================
	// Constructors
	// =============================================================================================

	public Spider(ImageView image) {
		this.image = image;

	}

	// =============================================================================================
	// Getter & Setter
	// =============================================================================================
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

	public int getplusX() {
		if (countx > 200) {
			countx = 0;
			rdx();
			plusx = (int) ((Math.random() * 15) - 7);
			plusx *= rdx();
		}
		countx++;
		return plusx;
	}

	public int getplusY() {
		if (county > 200) {
			county = 0;
			rdy();
			plusy = (int) ((Math.random() * 15) - 7);
			plusy *= rdy();
		}
		county++;
		return plusy;
	}

	public void setplusX(int x) {
		this.plusx = x;
	}

	public void setplusY(int y) {
		this.plusy = y;
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

	private int rdx() {
		int rdx;
		rdx = (int) ((Math.random() * 15) - 7);
		if (rdx > 0)
			rdx = 1;
		if (rdx == 0)
			rdx = 0;
		if (rdx < 0)
			rdx = -1;
		return rdx;
	}

	private int rdy() {
		int rdy;
		rdy = (int) ((Math.random() * 15) - 7);
		if (rdy > 0)
			rdy = 1;
		if (rdy == 0)
			rdy = 0;

		if (rdy < 0)
			rdy = -1;
		return rdy;
	}

	public boolean hitTest(Player player) {
		if (player.getX() < getX() + image.getWidth()
				&& player.getX() + player.getWidth() > getX()
				&& player.getY() < getY() + image.getHeight()
				&& player.getY() + player.getHeight() > getY())
			return true;
		return false;
	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}