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

	private ImageView main, pressstart, shadow; // ���� �ΰ�  ,  ���� ����ȭ��
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
		media_Init();//�޼ҵ� ȣ��
		mp.start();//bgm���
		IntentFilter intentFilter = new IntentFilter();
		//�Ͻ��� intent�� ����ϴ� ����� ����� intent ó�� ������ 
		//���� ȣ�� �ϴ°� �ƴ϶� intent�� (action)�� �߰��ؼ� ������ 
		//���� ����� ������ �� 
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//����ȭ�� ������ 
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		init();//�޼ҵ� ȣ��
	}

	@Override
	protected void onStart() {//bgm���
		super.onStart();
		mp.start();//bgm����
	}

	@Override
	protected void onPause() {//bgm����
		super.onPause();
		mp.pause();//bgm����
	}

	@Override
	protected void onResume() {
		super.onResume();
		media_Init();
		mp.start();
	}

	@Override
	protected void onStop() {//bgm����
		super.onStop();
		mp.pause();
	}

	@Override
	protected void onRestart() {//bgm�ٽ� ����
		super.onRestart();
		mp.start();//bgm���
	}

	@Override
	protected void onDestroy() {//���� ��������
		super.onDestroy();
		mp.stop();//bgm ���߰� ��
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_pressstart://Game Start�� ������ ����ȭ������ �Ѿ
			Intent intent = new Intent(StartActivity.this, ChoiceActivity.class);
			startActivity(intent);
			mp.stop();//����ȭ������ �Ѿ  �� ����ȭ�� bgm����
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
	

	private void media_Init(){//bgm �޼ҵ�
		mp = MediaPlayer.create(this, R.raw.a);//����ȭ�� bgm(���� a.ogg)
		mp.setLooping(true);//bgm �ݺ��ǵ���
	}
	
	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
