package com.example.application.travelue;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView tvNameApp = (TextView)findViewById(R.id.tvLogo);
        ImageView imgLogo = (ImageView) findViewById(R.id.imgRabano);

        // sets a Pretty Custom Font




        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);
        Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.grow_disappear_animation);
        imgLogo.startAnimation(myanim);
        tvNameApp.startAnimation(myanim2);
        openApp(true);
    }


    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen
                        .this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
