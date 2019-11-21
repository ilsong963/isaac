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
		
		//DB���� ����
		highscore.setText(text);
		Integer.parseInt(highscore.getText().toString());
		
		DatabaseManager.getInstance().initial(this, "isaac");//�����ͺ��̽� ���̺� ����
		
		score = new ArrayList<Integer>();//score��� Arraylist�� ����
		DatabaseManager.getInstance().load(score);//score list�� �ε���
		
		score.add(Integer.parseInt(highscore.getText().toString()));
		//highscore�� ���ڿ��� ��ȯ���Ѽ� score��� ����Ʈ�� ������
		
		Integer[] hs = score.toArray(new Integer[score.size()]);
		//score list�� ������ �� �ִ� �迭 ����
		
		Arrays.sort(hs);//�������� �迭
		List<Integer> list = Arrays.asList(hs);//�迭�� ����Ʈ�� �ٲ���
		Collections.reverse(list);//����Ʈ�� ������Ŵ(��������)
		hs = list.toArray(new Integer[list.size()]);//����Ʈ�� �迭�� ������
		score.clear();
		
		score.addAll(new ArrayList<Integer>(Arrays.asList(hs)));//list�̹Ƿ� addAll
		DatabaseManager.getInstance().save(score);//score���� ��� ������	
		highscore.setText(score.get(0).toString());//score�ȿ� 0��° �ε����� �ִ� ���� �ҷ���
		//DB��
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