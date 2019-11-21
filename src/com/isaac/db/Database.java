package com.isaac.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Database {

	// =============================================================================================
	// Constants
	// =============================================================================================

	// =============================================================================================
	// Fields
	// =============================================================================================

	private static Database instance;

	private SharedPreferences pref;
	private Editor editor;

	// =============================================================================================
	// Constructors
	// =============================================================================================

	// =============================================================================================
	// Getter & Setter
	// =============================================================================================

	// =============================================================================================
	// Methods for/from SuperClass/Interfaces
	// =============================================================================================

	// =============================================================================================
	// Methods
	// =============================================================================================

	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public void initial(Context context, String name) {
		pref = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
		editor = pref.edit();
	}

	public void put(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void put(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void put(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void put(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public boolean get(String key, boolean value) {
		try {
			return pref.getBoolean(key, value);
		} catch (Exception e) {
			return value;
		}
	}

	public int get(String key, int value) {
		try {
			return pref.getInt(key, value);
		} catch (Exception e) {
			return value;
		}
	}

	public long get(String key, long value) {
		try {
			return pref.getLong(key, value);
		} catch (Exception e) {
			return value;
		}
	}

	public String get(String key, String value) {
		try {
			return pref.getString(key, value);
		} catch (Exception e) {
			return value;
		}
	}

	// =============================================================================================
	// Inner and Anonymous Classes
	// =============================================================================================

}
