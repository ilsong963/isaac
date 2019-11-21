package com.isaac;

import com.isaac.system.Audio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PauseActivity extends Activity implements OnClickListener {

   // =============================================================================================
   // Constants
   // =============================================================================================

   // =============================================================================================
   // Fields
   // =============================================================================================

   private Audio audio;
   private static boolean pauseEnable;

   // =============================================================================================
   // Constructors
   // =============================================================================================

   // =============================================================================================
   // Getter & Setter
   // =============================================================================================

   public static boolean isEnable() {
      return pauseEnable;
   }

   public static void setEnable(boolean pe) {
      pauseEnable = pe;
   }

   // =============================================================================================
   // Methods for/from SuperClass/Interfaces
   // =============================================================================================

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pause);
      findViewById(R.id.resume).setOnClickListener(this);
      audio = Audio.getInstance();
   }
   @Override
	public void onBackPressed() {
	   setEnable(false);
       audio.onResume();
       finish();
       super.onBackPressed();
	}
   // =============================================================================================
   // Methods
   // =============================================================================================


   public void onClick(View v) {
      // TODO Auto-generated method stub
      switch (v.getId()) {

      case R.id.resume:
         setEnable(false);
         audio.onResume();
         finish();
         break;
      }

   }
   // =============================================================================================
   // Inner and Anonymous Classes
   // =============================================================================================

}