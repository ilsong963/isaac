package com.isaac;

import com.isaac.system.BackPressCloseHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class StartActivity extends Activity implements OnClickListener {
	
	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private ImageView main, pressstart, shadow; // 게임 로고  ,  게임 시작화면
	public MediaPlayer mp;//bgm
	Animation main_anim;
	private AnimationDrawable frameAnimation;
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
		setContentView(R.layout.activity_start);
		backPressCloseHandler = new BackPressCloseHandler(this);
		media_Init();//메소드 호출
		mp.start();//bgm재생
		IntentFilter intentFilter = new IntentFilter();
		//암시적 intent를 사용하는 방법은 명시적 intent 처럼 지정된 
		//곳을 호출 하는게 아니라 intent에 (action)을 추가해서 보내고 
		//싶은 명령을 보내는 것 
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//시작화면 꺼지면 
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		init();//메소드 호출
	}

	@Override
	protected void onStart() {//bgm재생
		super.onStart();
		mp.start();//bgm시작
	}

	@Override
	protected void onPause() {//bgm멈춤
		super.onPause();
		mp.pause();//bgm멈춤
	}

	@Override
	protected void onResume() {
		super.onResume();
		media_Init();
		mp.start();
	}

	@Override
	protected void onStop() {//bgm멈춤
		super.onStop();
		mp.pause();
	}

	@Override
	protected void onRestart() {//bgm다시 시작
		super.onRestart();
		mp.start();//bgm재생
	}

	@Override
	protected void onDestroy() {//앱이 꺼졋을때
		super.onDestroy();
		mp.stop();//bgm 멈추게 함
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_pressstart://Game Start를 누르면 게임화면으로 넘어감
			Intent intent = new Intent(StartActivity.this, ChoiceActivity.class);
			startActivity(intent);
			mp.stop();//게임화면으로 넘어갈  때 시작화면 bgm멈춤
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
         System.exit(0);
    }
	
	// =============================================================================================
	// Methods
	// =============================================================================================

	public void init() {
		
		shadow = (ImageView) findViewById(R.id.start_shadow);
		
		pressstart = (ImageView) findViewById(R.id.start_pressstart);
		pressstart.setOnClickListener(this);

		  frameAnimation=(AnimationDrawable)pressstart.getDrawable();

		  frameAnimation.start();   
		  
		  
		
		main = (ImageView) findViewById(R.id.start_main);
		main_anim = AnimationUtils.loadAnimation(this,R.anim.main);
		main.setAnimation(main_anim);
	}
	

	private void media_Init(){//bgm 메소드
		mp = MediaPlayer.create(this, R.raw.a);//시작화면 bgm(파일 a.ogg)
		mp.setLooping(true);//bgm 반복되도록
	}
	
	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
