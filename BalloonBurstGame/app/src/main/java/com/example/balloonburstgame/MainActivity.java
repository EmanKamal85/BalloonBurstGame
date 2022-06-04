package com.example.balloonburstgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewTime, textViewCountDown, textViewScore;
    ImageView balloon1, balloon2, balloon3, balloon4, balloon5, balloon6, balloon7, balloon8, balloon9;
    GridLayout gridLayout;
    int score = 0;

    ImageView[] balloonsArray;
    android.os.Handler handler;
    Runnable runnable;

    MediaPlayer mediaPlayer;

    boolean volumeStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer =MediaPlayer.create(this, R.raw.boom_sound);
        textViewTime = findViewById(R.id.textview_time);
        textViewCountDown = findViewById(R.id.textview_countdown);
        textViewScore = findViewById(R.id.textview_score);
        balloon1 = findViewById(R.id.balloon1);
        balloon2 = findViewById(R.id.balloon2);
        balloon3 = findViewById(R.id.balloon3);
        balloon4 = findViewById(R.id.balloon4);
        balloon5 = findViewById(R.id.balloon5);
        balloon6 = findViewById(R.id.balloon6);
        balloon7 = findViewById(R.id.balloon7);
        balloon8 = findViewById(R.id.balloon8);
        balloon9 = findViewById(R.id.balloon9);
        gridLayout = findViewById(R.id.grid_layout);

        balloonsArray = new ImageView[]{balloon1, balloon2, balloon3, balloon4, balloon5, balloon6, balloon7, balloon8, balloon9};

        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewCountDown.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {

                balloonControl();

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textViewTime.setText("Remaining Time : "+String.valueOf(millisUntilFinished/1000));

                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();

                    }
                }.start();

            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_mute){
            if (volumeStatus == false){
                mediaPlayer.setVolume(0, 0);
                volumeStatus = true;
                item.setIcon(R.drawable.ic_baseline_volume_off_24);
            }else {
                mediaPlayer.setVolume(1,1);
                volumeStatus = false;
                item.setIcon(R.drawable.ic_baseline_volume_up_24);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void increaseScoreByOne(View view){

        score++;
        textViewScore.setText("Score : " + score);
        if (mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }else {
            mediaPlayer.start();
        }

        for (ImageView balloon : balloonsArray){
            if (view.getId() == balloon.getId()){
                balloon.setImageResource(R.drawable.balloon_boom);
            }
        }

    }

    public void balloonControl(){


        textViewCountDown.setVisibility(View.INVISIBLE);
        textViewTime.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);
        //gridLayout.setVisibility(View.VISIBLE);

        handler =new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView balloon: balloonsArray){
                    balloon.setVisibility(View.INVISIBLE);
                    balloon.setImageResource(R.drawable.balloon);
                }
                Random random = new Random();
                int i = random.nextInt(balloonsArray.length);
                balloonsArray[i].setVisibility(View.VISIBLE);

                if (score < 5){
                    handler.postDelayed(runnable, 2000);
                }
                else if (score >= 5 && score < 10){
                    handler.postDelayed(runnable, 1500);
                }else if (score >= 10 && score < 15){
                    handler.postDelayed(runnable, 1000);
                }else if (score >= 15){
                    handler.postDelayed(runnable, 500);
                }

            }
        };
        handler.post(runnable);




    }
}