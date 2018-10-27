package com.kodwell.infodisplay;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.kodwell.infodisplay.listeners.OnCustomTouchListener;

/**
 * Created by sudha on 7/4/2018.
 */

public class MainActivity extends Activity {
    LinearLayout parentLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);

        parentLayout = findViewById(R.id.parent);
        OnCustomTouchListener  swipeListner = new OnCustomTouchListener(MainActivity.this) {
            @Override
            public void onSwipeBottom(View view) {
                super.onSwipeBottom(view);
            }


            @Override
            public void onSwipeTop(View view) {
                super.onSwipeTop(view);
               /* Intent intent = new Intent( "com.example.administrator.setting.HOYO_SETTING");
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                overridePendingAnimationExitToTop();
                finish();

            }


            @Override
            public void onSwipeRight(View view) {
                super.onSwipeRight(view);



            }

            @Override
            public void onSwipeLeft(View view) {
                super.onSwipeLeft(view);




            }

            @Override
            public void onLongClicked(View view) {


            }
        };

        parentLayout.setOnTouchListener(swipeListner);
    }
    protected void overridePendingAnimationExitToTop() {

        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
