package com.isaac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MadebyActivity extends Activity implements OnClickListener {

	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

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
		setContentView(R.layout.activity_madeby);
		init();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(MadebyActivity.this, ChoiceActivity.class);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	@Override
	public void onUserLeaveHint() {
		System.exit(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Layout_madeby:
			Intent intent = new Intent(MadebyActivity.this,
					ChoiceActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	// =============================================================================================
	// Methods
	// =============================================================================================

	public void init() {
		LinearLayout madeby = (LinearLayout) findViewById(R.id.Layout_madeby);
		madeby.setOnClickListener(this);
	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
