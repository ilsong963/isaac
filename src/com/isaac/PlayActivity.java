package com.isaac;

import com.isaac.component.Bullet;
import com.isaac.component.Player;
import com.isaac.component.Spider;
import com.isaac.system.Audio;
import com.isaac.system.BackPressCloseHandler;
import com.isaac.system.GameThread;
import com.isaac.system.JoyStick;
import com.isaac.system.SpiderManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayActivity extends Activity {

	// =============================================================================================
	// Constants
	// =============================================================================================

	private static final int MAX_SPIDER = 10; // 스파이더 생성 갯수
	private static final int MAX_BULLET = 5; // 눈물 생성 갯수

	private static final int NONE = 0;
	private static final int LEFT_MOVE = 1; // 왼쪽 방향키
	private static final int RIGHT_MOVE = 2; // 오른쪽 방향키
	private static final int UP_MOVE = 3; // 위 방향키
	private static final int UPLEFT_MOVE = 4; // 왼쪽위 방향키
	private static final int UPRIGHT_MOVE = 5; // 오른쪽위 방향키
	private static final int DOWN_MOVE = 6; // 아래 방향키
	private static final int DOWNLEFT_MOVE = 7; // 왼쪽아래 방향키
	private static final int DOWNRIGHT_MOVE = 8; // 오른쪽아래 방향키

	private static final int ATTACK_LEFT = 9; // 왼쪽 공격
	private static final int ATTACK_RIGHT = 10; // 오른쪽 공격
	private static final int ATTACK_UP = 11; // 위 공격
	private static final int ATTACK_DOWN = 12; // 아래 공격

	// =============================================================================================
	// Fields
	// =============================================================================================

	// System
	private DisplayMetrics dm; // 해상도
	private GameThread gt;
	private Audio audio;
	private int life;
	private int echojs;
	private int echoat;
	private int echo;
	private boolean head;
	private BackPressCloseHandler backPressCloseHandler;
	// GUI
	private Button life1, life2, life3; // 하트
	private TextView score;
	private ImageView im = new ImageView(this);

	// Controller
	private Button attack_up, attack_down, attack_right, attack_left; // 공격버튼
	private Button pause;// 일시정지 버튼
	private RelativeLayout layout_joystick;
	private JoyStick js;
	private PauseActivity pa = new PauseActivity();

	// Object
	private Player player;
	private Bullet bullet[];// 눈물 배열 생성
	private SpiderManager spiderManager;
	private Spider sp;

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
		setContentView(R.layout.activity_play);
		initSystem();
		initController();
		initObject();
		backPressCloseHandler = new BackPressCloseHandler(this);

		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 화면 꺼짐방지

		gt.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		gt.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gt.onResume();
	}
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}
//	@Override
//	public void onUserLeaveHint() { 홈키종료기능(resumegame 실행 문제로 보류)
//         System.exit(0);
//    }

	// =============================================================================================
	// Methods
	// =============================================================================================

	public void initSystem() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		gt = new GameThread(handler);
		audio = Audio.getInstance();
		audio.init(this);
		life = 6;
	}

	public void initController() {

		pause = (Button) findViewById(R.id.pause);
		pause.setOnTouchListener(optionListener);

		score = (TextView) findViewById(R.id.score);
		score.setTypeface(Typeface.createFromAsset(getAssets(), "210fonts.ttf"));

		attack_up = (Button) findViewById(R.id.attack_up);
		attack_up.setOnTouchListener(attackKeyListener);

		attack_down = (Button) findViewById(R.id.attack_down);
		attack_down.setOnTouchListener(attackKeyListener);

		attack_right = (Button) findViewById(R.id.attack_right);
		attack_right.setOnTouchListener(attackKeyListener);

		attack_left = (Button) findViewById(R.id.attack_left);
		attack_left.setOnTouchListener(attackKeyListener);

		life1 = (Button) findViewById(R.id.life1);
		life2 = (Button) findViewById(R.id.life2);
		life3 = (Button) findViewById(R.id.life3);

		layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
		layout_joystick.setOnTouchListener(joystickListener);

		js = new JoyStick(getApplicationContext(), layout_joystick, R.drawable.play_joystick_center);
		js.setStickSize((int) (150 / 1280.0 * dm.widthPixels), (int) (150 / 720.0 * dm.heightPixels));
		js.setLayoutSize((int) (300 / 1280.0 * dm.widthPixels), (int) ((300 / 720.0 * dm.heightPixels)));
		js.setLayoutAlpha(200);
		js.setOffset(200);
	    js.setMinimumDistance(80);
	}

	public void initObject() {
		FrameLayout fl = (FrameLayout) findViewById(R.id.FrameLayout1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);


		// 플레이어 생성

		player = new Player((ImageView) findViewById(R.id.isaac));

		for (int i = 0; i < 3; i++) {

			player.setMotion(loadDrawable("play_left_ch" + (i + 1)), i, LEFT_MOVE);
			player.setMotion(loadDrawable("play_right_ch" + (i + 1)), i, RIGHT_MOVE);
			player.setMotion(loadDrawable("play_up_ch" + (i + 1)), i, UP_MOVE);
			player.setMotion(loadDrawable("play_up_ch" + (i + 1)), i, UPLEFT_MOVE);
			player.setMotion(loadDrawable("play_up_ch" + (i + 1)), i, UPRIGHT_MOVE);
			player.setMotion(loadDrawable("play_down_ch" + (i + 1)), i, DOWN_MOVE);
			player.setMotion(loadDrawable("play_down_ch" + (i + 1)), i, DOWNLEFT_MOVE);
			player.setMotion(loadDrawable("play_down_ch" + (i + 1)), i, DOWNRIGHT_MOVE);
			player.setMotion(loadDrawable("play_left_tears_ch" + (i + 1)), i, ATTACK_LEFT);
			player.setMotion(loadDrawable("play_right_tears_ch" + (i + 1)), i, ATTACK_RIGHT);
			player.setMotion(loadDrawable("play_up_tears_ch" + (i + 1)), i, ATTACK_UP);
			player.setMotion(loadDrawable("play_down_tears_ch" + (i + 1)), i, ATTACK_DOWN);

		}

		player.setMode(getIntent().getExtras().getInt("character"));
		player.setImageDrawable(DOWN_MOVE);
		player.setEnable(true);

		// 총알 생성

		bullet = new Bullet[MAX_BULLET];
		for (int i = 0; i < bullet.length; i++) {
			
			im.setImageDrawable(getResources().getDrawable(R.drawable.play_eyedrop_0));
			bullet[i] = new Bullet(im);
			bullet[i].setEnable(false);
			fl.addView(im, lp);// 눈물이 나타나도록함
		}

		// 스파이더 생성

		spiderManager = new SpiderManager();
		for (int i = 0; i < MAX_SPIDER; i++)
			spiderManager.addSpider(this, fl, lp);
	}

	public Drawable loadDrawable(String name) {
		try {
			return getResources().getDrawable(R.drawable.class.getField(name).getInt(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void attack(int direction) {

		if (Bullet.getCount() == Bullet.COUNT) {
			Bullet.setCount(0);

			for (int i = 0; i < bullet.length; i++) {
				if (!bullet[i].isEnable()) {
					bullet[i].setX(player.getX() + 30);
					bullet[i].setY(player.getY() + 30);
					bullet[i].setDirection(ATTACK_LEFT + direction);
					bullet[i].setEnable(true);

					player.setImageDrawable(ATTACK_LEFT + direction);
					audio.play(Audio.BGM1);
					break;
				}
			}
		}
		head = true;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}

	public void setEchoat(int echoat) {
		this.echoat = echoat;
	}

	public void setEchojs(int echojs) {
		this.echojs = echojs;
	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// Echo값들을 받아오는 메소드 호출
			setEcho(gt.getEcho());
			setEchojs(gt.getEchojs());
			setEchoat(gt.getEchoat());

			// 컨트롤러 입력 (조이스틱)
	         switch (echojs) {

	         case LEFT_MOVE:
	            if (leftMove())
	               player.setX(player.getX() - 5);

	            break;

	         case RIGHT_MOVE:
	            if (rightMove())
	               player.setX(player.getX() + 5);

	            break;

	         case UP_MOVE:
	            if (upMove())
	               player.setY(player.getY() - 5);

	            break;

	         case DOWN_MOVE:
	            if (downMove())
	               player.setY(player.getY() + 5);

	            break;

	         case UPLEFT_MOVE:
	            if (upMove() && leftMove()) {
	               player.setY(player.getY() - 4);
	               player.setX(player.getX() - 4);
	            }

	            if (!upMove() && leftMove())
	               player.setX(player.getX() - 4);

	            if (upMove() && !leftMove())
	               player.setY(player.getY() - 4);

	            break;

	         case UPRIGHT_MOVE:

	            if (upMove() && rightMove()) {
	               player.setX(player.getX() + 4);
	               player.setY(player.getY() - 4);
	            }

	            if (!upMove() && rightMove())
	               player.setX(player.getX() + 4);

	            if (upMove() && !rightMove())
	               player.setY(player.getY() - 4);

	            break;

	         case DOWNLEFT_MOVE:
	            if (downMove() && leftMove()) {
	               player.setY(player.getY() + 4);
	               player.setX(player.getX() - 4);
	            }

	            if (!downMove() && leftMove())
	               player.setX(player.getX() - 4);

	            if (downMove() && !leftMove())
	               player.setY(player.getY() + 4);

	            break;

	         case DOWNRIGHT_MOVE:
	            if (downMove() && rightMove()) {
	               player.setY(player.getY() + 4);
	               player.setX(player.getX() + 4);
	            }

	            if (!downMove() && rightMove())
	               player.setX(player.getX() + 4);

	            if (downMove() && !rightMove())
	               player.setY(player.getY() + 4);

	            break;
	         }

			// 컨트롤러 입력 (공격버튼)
			switch (echoat) {

			case ATTACK_LEFT:
				attack(0);
				break;

			case ATTACK_RIGHT:
				attack(1);
				break;

			case ATTACK_UP:
				attack(2);
				break;

			case ATTACK_DOWN:
				attack(3);
				break;

			}

			// 총알 발사 카운트
			if (Bullet.getCount() < Bullet.COUNT)
				Bullet.setCount(Bullet.getCount() + 1);

			// 총알 발사 후 아래 보고 공격 상태 중단
			if (Bullet.getCount() == Bullet.COUNT - 1) {
				player.setImageDrawable(DOWN_MOVE);
			}

			// 거미 이동
			if (gt.getCount() < 30) {
				spiderManager.step(dm);
			}

			// 플레이어, 스파이더 충돌 처리 루틴
			if (player.isEnable() && player.getCount() == Player.COUNT) {
				for (int i = 0; i < spiderManager.size(); i++) {// 거미 밟으면
					// GAMEOVER
					if (spiderManager.get(i).isEnable()) {
						if (spiderManager.get(i).hitTest(player)) {

							audio.play((int) (Math.random() * 3) + 2);

							Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
							vibrator.vibrate(450);

							player.getImage().setAnimation(AnimationUtils.loadAnimation(PlayActivity.this, R.anim.hit));
							player.setCount(0);
							life--;

							switch (life) {
							case 0:
								life1.setBackgroundResource(R.drawable.play_heart_e);
								player.setEnable(false);
								spiderManager.get(i).setEnable(false);
								Intent intent = new Intent(PlayActivity.this, GameoverActivity.class);
								intent.putExtra("text", score.getText().toString());
								startActivity(intent);
								PlayActivity.this.finish();
								break;
							case 1:
								life1.setBackgroundResource(R.drawable.play_heart_h);
								break;
							case 2:
								life2.setBackgroundResource(R.drawable.play_heart_e);
								break;
							case 3:
								life2.setBackgroundResource(R.drawable.play_heart_h);
								break;
							case 4:
								life3.setBackgroundResource(R.drawable.play_heart_e);
								break;
							case 5:
								life3.setBackgroundResource(R.drawable.play_heart_h);
								break;
							}
						} // spider hitTest
					} // spider enable
				} // end for
			} else if (player.isEnable()) { // 플레이어가 무적일 때
				player.setCount(player.getCount() + 1);
			}

			// 마지막 거미가 죽으면 리스폰
			if (spiderManager.spiderAlive() == 0)
				spiderManager.respawn(player);

			for (int i = 0; i < bullet.length; i++) {
				if (bullet[i].isEnable()) {

					// 총알과 스파이더 충돌 검사

					for (int j = 0; j < spiderManager.size(); j++) {
						if (spiderManager.get(j).isEnable()) {
							if (bullet[i].hitTest(spiderManager.get(j))) {
								score.setText(Integer.parseInt(score.getText().toString()) + 10 + "");
								bullet[i].setEnable(false);
								spiderManager.get(j).setEnable(false);
								audio.play(Audio.BGM2);
							}
						}
					}

					// 총알 이동

					switch (bullet[i].getDirection()) {
					case ATTACK_LEFT:
						if (bullet[i].getX() > 194.0 / 1280.0 * dm.widthPixels) {
							bullet[i].setX(bullet[i].getX() - 8);
						} else {
							bullet[i].setEnable(false);
							audio.play(Audio.BGM2);
						}
						break;

					case ATTACK_RIGHT:
						if (bullet[i].getX() < 1050.0 / 1280.0 * dm.widthPixels) {
							bullet[i].setX(bullet[i].getX() + 8);
						} else {
							bullet[i].setEnable(false);
							audio.play(Audio.BGM2);
						}

						break;

					case ATTACK_UP:
						if (bullet[i].getY() > 40.0 / 720.0 * dm.heightPixels) {
							bullet[i].setY(bullet[i].getY() - 8);
						} else {
							bullet[i].setEnable(false);
							audio.play(Audio.BGM2);
						}
						break;

					case ATTACK_DOWN:
						if (bullet[i].getY() < 520.0 / 720.0 * dm.heightPixels) {
							bullet[i].setY(bullet[i].getY() + 8);
						} else {
							bullet[i].setEnable(false);
							audio.play(Audio.BGM2);
						}
						break;
					}
				}
			}
		}
	};
	private final OnTouchListener optionListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.pause:

				if (!PauseActivity.isEnable()) {
		               PauseActivity.setEnable(true);
		               gt.onPause();
		               audio.onPause();
		               Intent pauseIntent = new Intent(PlayActivity.this, PauseActivity.class);
		               startActivity(pauseIntent);
		            }
			}
			return true;
		}
	};

	private final OnTouchListener joystickListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			js.drawStick(event);
			int direction = js.get8Direction();

			if (direction == NONE)
				player.setImageDrawable(DOWN_MOVE);
			else if (head != true) {
				player.setImageDrawable(direction);
				head = false;
			}

			gt.setEchojs(direction);
			return true;
		}
	};

	private final OnTouchListener attackKeyListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();

			switch (v.getId()) {
			case R.id.attack_left:
				gt.setEchoat(ATTACK_LEFT);
				break;

			case R.id.attack_right:
				gt.setEchoat(ATTACK_RIGHT);
				break;

			case R.id.attack_up:
				gt.setEchoat(ATTACK_UP);
				break;

			case R.id.attack_down:
				gt.setEchoat(ATTACK_DOWN);
				break;

			}

			if (action == MotionEvent.ACTION_UP) {
				gt.setEchoat(NONE);
				head = false;
			}

			return true;
		}
	};
	public boolean leftMove() {
	      return player.getX() > 194.0 / 1280.0 * dm.widthPixels;
	   }

	   public boolean rightMove() {
	      return player.getX() < 1050.0 / 1280.0 * dm.widthPixels;
	   }

	   public boolean upMove() {
	      return player.getY() > 40.0 / 720.0 * dm.heightPixels;
	   }

	   public boolean downMove() {
	      return player.getY() < 520.0 / 720.0 * dm.heightPixels;
	   }
}
