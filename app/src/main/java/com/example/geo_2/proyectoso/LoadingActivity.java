package com.example.geo_2.proyectoso;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        img=(ImageView)findViewById(R.id.img1);

        ClsAnimation();
        contNumber.start();
    }


    private void ClsAnimation(){

        Animation animate;
        animate= AnimationUtils.loadAnimation(this,R.anim.loading);
        animate.reset();
        img.startAnimation(animate);


    }
    CountDownTimer contNumber = new CountDownTimer(4000, 1000){

        @Override
        public void onTick(long millisUntilFinished){


        }



        @Override
        public void onFinish() {
            Bundle b = getIntent().getExtras();
            b.putString("Name", b.getString("Name"));
            Intent intent= new Intent(LoadingActivity.this,PlayListActivity.class);
            intent.putExtras(b);
            startActivity(intent);
            LoadingActivity.this.finish();
        }


        };

}
