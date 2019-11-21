package com.isaac.system;

import com.isaac.PlayActivity;

import android.os.Handler;
import android.os.Message;

public class GameThread extends Thread {

	// =============================================================================================
	// Constants
	// =============================================================================================
	PlayActivity playat;
	// =============================================================================================
	// Fields
	// =============================================================================================

	private boolean runnable;
	private Handler handler;
	private int echo;
	private int echoat;
	private int echojs;
	private int count;
	Message msg;
	// =============================================================================================
	// Constructors
	// =============================================================================================

	public GameThread(Handler handler) {
		this.handler = handler;
		runnable = true;
	}

	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}

	public int getEcho() {
		return echo;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}
	
	public int getEchoat() {
		return echoat;
	}

	public void setEchoat(int echo) {
		this.echoat = echo;
	}

	public int getEchojs() {
		return echojs;
	}

	public void setEchojs(int echo) {
		this.echojs = echo;
	}
	public int getCount() {
		return count;
	}

	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	@Override
	public void run() {

		try {
			while (true) {
				if (runnable) {
					handler.sendEmptyMessage(0);
					count++;
					if (count % 100 == 0)
						count = 0;
					Thread.sleep(10);
				} else {
					synchronized (this) {
						wait();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// =============================================================================================
	// Methods
	// =============================================================================================

	public void onPause() {
		runnable = false;
	}

	public void onResume() {
		runnable = true;

		synchronized (this) {
			notify();
		}
	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
