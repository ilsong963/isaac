package com.isaac.system;

import com.isaac.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Audio {

	// =============================================================================================
	// Constants
	// =============================================================================================

	public static final int BGM1 = 0;
	public static final int BGM2 = 1;
	
	// =============================================================================================
	// Fields
	// =============================================================================================

	public static Audio instance = null;

	private MediaPlayer mp;
	private SoundPool pool;
	private int sd1, sd2, ah1, ah2, ah3;
	
	// =============================================================================================
	// Constructors
	// =============================================================================================

	private Audio() {
	}
	
	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	public static Audio getInstance() {
		if (instance == null)
			instance = new Audio();
		return instance;
	}
	
	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	// =============================================================================================
	// Methods
	// =============================================================================================

	public void init(Context context) {
		mp = MediaPlayer.create(context, R.raw.b);
		pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		
		sd1 = pool.load(context, R.raw.c, 1);
		sd2 = pool.load(context, R.raw.e, 1);
		ah1 = pool.load(context, R.raw.ah1, 1);
		ah2 = pool.load(context, R.raw.ah2, 1);
		ah3 = pool.load(context, R.raw.ah3, 1);

		mp.setLooping(true);
		mp.start();
	}
	
	public void onPause() {
	      mp.pause();
	      // TODO Auto-generated method stub
	      
	   }
	
	public void onResume(){
		mp.start();
		
	}
	
	public void play(int index) {
		switch (index) {
		case BGM1:
			pool.play(sd1, 1, 1, 0, 0, 1);
			break;
			
		case BGM2:
			pool.play(sd2, 1, 1, 0, 0, 1);
			break;
		
		case 2:
			pool.play(ah1, 1, 1, 0, 0, 1);
			break;
			
		case 3:
			pool.play(ah2, 1, 1, 0, 0, 1);
			break;
			
		case 4:
			pool.play(ah3, 1, 1, 0, 0, 1);
			break;
		}
	}
	
	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
