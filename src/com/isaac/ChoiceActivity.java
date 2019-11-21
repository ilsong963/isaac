package com.isaac;

import com.isaac.system.BackPressCloseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ChoiceActivity extends Activity implements OnClickListener {

	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private Button char1, char2, char3;
	private ImageView view, back, random, shadow;
	private AnimationDrawable frameAnimation;
	private Animation random_anim, back_anim;
	private BackPressCloseHandler backPressCloseHandler;

	// =============================================================================================
	// Constructors
	// =============================================================================================

	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		backPressCloseHandler = new BackPressCloseHandler(this);
		init();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// 어플에 포커스가 갈때 시작된다
			frameAnimation.start();
		} else {
			// 어플에 포커스를 떠나면 종료한다
			frameAnimation.stop();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;

		switch (v.getId()) {
		case R.id.choice_ch1:
			intent = new Intent(ChoiceActivity.this, PlayActivity.class);
			intent.putExtra("character", 0);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		case R.id.choice_ch2:
			intent = new Intent(ChoiceActivity.this, PlayActivity.class);
			intent.putExtra("character", 1);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		case R.id.choice_ch3:
			intent = new Intent(ChoiceActivity.this, PlayActivity.class);
			intent.putExtra("character", 2);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		case R.id.choice_back:
			intent = new Intent(ChoiceActivity.this, StartActivity.class);
			startActivity(intent);
			break;

		case R.id.choice_random:
			intent = new Intent(ChoiceActivity.this, PlayActivity.class);
			intent.putExtra("character", (int) (Math.random() * 3));
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		case R.id.choice_fly:
			intent = new Intent(ChoiceActivity.this, MadebyActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}
	@Override
	public void onUserLeaveHint() {
         finish();
    }

	// =============================================================================================
	// Methods
	// =============================================================================================

	public void init() {
		

		shadow = (ImageView) findViewById(R.id.start_shadow);
		
		char1 = (Button) findViewById(R.id.choice_ch1);
		char1.setOnClickListener(this);

		char2 = (Button) findViewById(R.id.choice_ch2);
		char2.setOnClickListener(this);

		char3 = (Button) findViewById(R.id.choice_ch3);
		char3.setOnClickListener(this);

		view = (ImageView) findViewById(R.id.choice_fly);
		view.setOnClickListener(this);
		view.setBackgroundResource(R.drawable.fly);

		random = (ImageView) findViewById(R.id.choice_random);
		random.setOnClickListener(this);

		back = (ImageView) findViewById(R.id.choice_back);
		back.setOnClickListener(this);

		frameAnimation = (AnimationDrawable) view.getBackground();

		random_anim = AnimationUtils.loadAnimation(this, R.anim.press_start);
		random.setAnimation(random_anim);

		back_anim = AnimationUtils.loadAnimation(this, R.anim.press_start);
		back.setAnimation(back_anim);

	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}