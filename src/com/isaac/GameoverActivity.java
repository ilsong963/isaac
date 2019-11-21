package com.isaac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.isaac.db.DatabaseManager;
import com.isaac.system.BackPressCloseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GameoverActivity  extends Activity implements OnClickListener{
	
	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private TextView result_gameover;
	private TextView replay;
	private TextView back;
	private TextView highscore;
	
	private ArrayList<Integer> score;
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
		setContentView(R.layout.activity_gameover);
		backPressCloseHandler = new BackPressCloseHandler(this);
		result_gameover = (TextView) findViewById(R.id.tv_result_gameover);
		replay = (TextView) findViewById(R.id.tv_replay);
		back = (TextView) findViewById(R.id.backtomain);
		highscore = (TextView) findViewById(R.id.highscore);
		
		replay.setOnClickListener(this);
		back.setOnClickListener(this);

		result_gameover.setTypeface(Typeface.createFromAsset(getAssets(),"210fonts.ttf"));
		highscore.setTypeface(Typeface.createFromAsset(getAssets(),"210fonts.ttf"));
		Intent intent = getIntent();
		String text = intent.getStringExtra("text");
		result_gameover.setText(text);
		
		//DB수정 시작
		highscore.setText(text);
		Integer.parseInt(highscore.getText().toString());
		
		DatabaseManager.getInstance().initial(this, "isaac");//데이터베이스 테이블 생성
		
		score = new ArrayList<Integer>();//score라는 Arraylist를 만듦
		DatabaseManager.getInstance().load(score);//score list를 로드함
		
		score.add(Integer.parseInt(highscore.getText().toString()));
		//highscore를 문자열로 변환시켜서 score라는 리스트에 저장함
		
		Integer[] hs = score.toArray(new Integer[score.size()]);
		//score list를 저장할 수 있는 배열 생성
		
		Arrays.sort(hs);//오름차순 배열
		List<Integer> list = Arrays.asList(hs);//배열울 리스트로 바꿔줌
		Collections.reverse(list);//리스트를 역순시킴(내림차순)
		hs = list.toArray(new Integer[list.size()]);//리스트를 배열에 복사함
		score.clear();
		
		score.addAll(new ArrayList<Integer>(Arrays.asList(hs)));//list이므로 addAll
		DatabaseManager.getInstance().save(score);//score들을 모두 저장함	
		highscore.setText(score.get(0).toString());//score안에 0번째 인덱스에 있는 값을 불러옴
		//DB끝
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tv_replay:
			Intent intent = new Intent(GameoverActivity.this, ChoiceActivity.class);
			startActivity(intent);
			System.exit(0);
			break;

		case R.id.backtomain:
			Intent intent1 = new Intent(GameoverActivity.this, StartActivity.class);
			startActivity(intent1);
			System.exit(0);
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

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}