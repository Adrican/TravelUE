package com.example.application.travelue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView myTitle = (TextView)findViewById(R.id.textViewRECORTES);
        ImageView imgView = (ImageView) findViewById(R.id.imageViewRECORTES);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);
        //Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);

        myTitle.startAnimation(myanim);
//        imgView.startAnimation(myanim2);
        openApp(true);







    }

    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen
                        .this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

}
