package com.example.balloonburstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textViewInfo, textViewMyScore, textViewHighestScore;
    Button buttonPlayAgain, buttonQuitGame;

    int score;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewInfo = findViewById(R.id.textview_info);
        textViewMyScore = findViewById(R.id.textview_myscore);
        textViewHighestScore = findViewById(R.id.textview_heighest_score);

        buttonPlayAgain = findViewById(R.id.button_play_again);
        buttonQuitGame = findViewById(R.id.button_quit_game);

        score = getIntent().getIntExtra("score", 0);
        textViewMyScore.setText("Your Score : "+ score);

        sharedPreferences = this.getSharedPreferences("score", MODE_PRIVATE);
        int topScore = sharedPreferences.getInt("topScore", 0);

        if (score >= topScore){
            sharedPreferences.edit().putInt("topScore", score).apply();
            textViewHighestScore.setText("Highest Score : " + score);
            Log.e( "Top Score : ", ""+ topScore );
            textViewInfo.setText("Congratulations. The new high score is yours. Do you want to get better scores?");
        }else {
            textViewHighestScore.setText("Highest Score : " + topScore);
            if ((topScore - score) > 10 ){
                textViewInfo.setText("You must get a little faster");
            }
            if ((topScore - score) > 3 && (topScore - score) <= 10){
                textViewInfo.setText("Good. How about getting a little fasetr?");
            }
            if ((topScore - score) <= 3 ){
                textViewInfo.setText("Excellent. If you get a little faster, you can reach the high score");
            }
        }

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });


    }
}