package com.beet.application.travelue;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView imgLogo, imgLogoMordido1, imgLogoMordido2, imgLogoMordido3, imgLogoMordido4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //TextView tvNameApp = (TextView)findViewById(R.id.tvLogo);
        //TextView tvSloganApp = (TextView) findViewById(R.id.tvName);
        imgLogo = (ImageView) findViewById(R.id.imgRabano);
        imgLogoMordido1 = (ImageView) findViewById(R.id.imgRabano1);
        imgLogoMordido2 = (ImageView) findViewById(R.id.imgRabano2);
        imgLogoMordido3 = (ImageView) findViewById(R.id.imgRabano3);
        imgLogoMordido4 = (ImageView) findViewById(R.id.imgRabano4);

        // sets a Pretty Custom Font

        TextView myTitle = (TextView)findViewById(R.id.tvLogo);
        TextView mySubtitle = (TextView)findViewById(R.id.tvName);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Mistletoe.otf");
        //myTitle.setTypeface(myFont);
        mySubtitle.setTypeface(myFont);


        //Animation myanim = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);
        Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);
        //imgLogo.startAnimation(myanim);
        myTitle.startAnimation(myanim2);
        openApp();
        openApp1();
        openApp2();

    }


    public void openApp() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgLogo.setVisibility(View.INVISIBLE);
                imgLogoMordido1.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }
    public void openApp1() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgLogoMordido1.setVisibility(View.INVISIBLE);
                imgLogoMordido2.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
    public void openApp2() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgLogoMordido2.setVisibility(View.INVISIBLE);
                imgLogoMordido4.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
    /*
    public void openApp3() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 4000);
    }
    */
}
