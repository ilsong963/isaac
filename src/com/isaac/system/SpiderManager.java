package com.isaac.system;

import java.util.ArrayList;

import com.isaac.R;
import com.isaac.component.Player;
import com.isaac.component.Spider;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SpiderManager {

	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private ArrayList<Spider> spiders;

	// =============================================================================================
	// Constructors
	// =============================================================================================

	public SpiderManager() {
		spiders = new ArrayList<Spider>();
	}
	
	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	public int size() {
		return spiders.size();
	}
	
	public Spider get(int index) {
		return spiders.get(index);
	}
	
	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	// =============================================================================================
	// Methods
	// =============================================================================================
	
	public void step(DisplayMetrics dm) {
		for (int i = 0; i < spiders.size(); i++) {
			if (spiders.get(i).isEnable()) {
				if (spiders.get(i).getX() + spiders.get(i).getplusX() > 1050.0 / 1280.0 * dm.widthPixels) {
					spiders.get(i).setplusX(spiders.get(i).getplusX() * -1);
				}
				if (spiders.get(i).getX() + spiders.get(i).getplusX() < 180.0 / 1280.0 * dm.widthPixels) {
					spiders.get(i).setplusX(spiders.get(i).getplusX() * -1);
				}
				if (spiders.get(i).getY() + spiders.get(i).getplusY() > 520.0 / 720.0 * dm.heightPixels) {
					spiders.get(i).setplusY(spiders.get(i).getplusY() * -1);
				}
				if (spiders.get(i).getY() + spiders.get(i).getplusY() < 80.0 / 720.0 * dm.heightPixels) {
					spiders.get(i).setplusY(spiders.get(i).getplusY() * -1);
				}
				spiders.get(i).setX(spiders.get(i).getX() + spiders.get(i).getplusX());
				spiders.get(i).setY(spiders.get(i).getY() + spiders.get(i).getplusY());
			}
		}
	}
	
	public void respawn(Player player) {
		for (int i = 0; i < spiders.size(); i++) {
			int x = (int) (Math.random() * 875) + 170;
			int y = (int) (Math.random() * 480) + 100;
					
			spiders.get(i).setX(x);
			spiders.get(i).setY(y);
			spiders.get(i).setEnable(true);

			if (x > player.getX() - spiders.get(i).getWidth() && x < player.getX() + player.getWidth()) {
				i--;
			} else if (y > player.getY() - spiders.get(i).getHeight() && y < player.getY() + player.getHeight()) {
				i--;
			}
		}
	}
	
	public void addSpider(Context context, FrameLayout frameLayout, RelativeLayout.LayoutParams layoutParams) {
		ImageView imageView = new ImageView(context);
		int imageNumber = (int) (Math.random() * 5);
		Spider spider;
		
		try {
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.class.getField("play_spider" + imageNumber).getInt(null)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spider = new Spider(imageView);
		spider.setEnable(false);
		spiders.add(spider);
		frameLayout.addView(imageView, layoutParams);
	}
	
	public int spiderAlive() {
		int count = 0;
		
		for (int i = 0; i < spiders.size(); i++) {
			if (spiders.get(i).isEnable())
				count++;
		}
		
		return count;
	}
	
	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}