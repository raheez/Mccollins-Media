package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.mccollinsmedia.R;

public class SplashScreen extends RootActivity {

    int splashScreenDuration = 4000;
    public ImageView logoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoImage = (ImageView) findViewById(R.id.logoimage);
        Glide.with(SplashScreen.this).load(R.drawable.mccollinsmediadubai).into(logoImage);
        propagateToMainActivity();
        animateLogo();
    }
    private void propagateToMainActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },splashScreenDuration);

    }
    private void animateLogo() {
        logoImage.animate().scaleXBy(0.2f).scaleYBy(0.2f).setDuration(100).setListener(scaleUpListener);

    }

    private Animator.AnimatorListener scaleDownListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            logoImage.animate().scaleXBy(0.2f).scaleYBy(0.2f).setDuration(250).setListener(scaleUpListener);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }
    };

    private Animator.AnimatorListener scaleUpListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            logoImage.animate().scaleXBy(-0.2f).scaleYBy(-0.2f).setDuration(250).setListener(scaleDownListener);

        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }
    };
}
