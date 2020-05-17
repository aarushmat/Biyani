package com.example.biyani;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity
{
 ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=findViewById(R.id.image);



        Animation ani = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        imageView.startAnimation(ani);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                Intent i = new Intent(SplashScreen.this, LoginMainActivity.class);
//                startActivity(i);
//                staticDataHelper = StaticDataHelper.getInstance(SplashActivity.this);
                if (StaticDataHelper.getBooleanFromPreferences(SplashActivity.this, "isuserlogin"))
                {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//                    View sharedView = textView;
                    startActivity(mainIntent);
                    SplashActivity.this.finish();

                }
                else
                {
                    //startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
//                    View sharedView = textView;
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                }


                overridePendingTransition(R.anim.in_fade,R.anim.out_fade);
                finish();
            }
        }, 1500);


//        staticDataHelper = StaticDataHelper.getInstance(SplashActivity.this);
//        gotonextActivity();

    }

}