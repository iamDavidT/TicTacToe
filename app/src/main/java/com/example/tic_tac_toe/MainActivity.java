package com.example.tic_tac_toe;

/*Main activity of the Tic Tac Toe Game
* Written by David Tidswell CSE2MAX
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button start, highScore, exit;//create button variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Find buttons on the layout and set onclick listeners
        start = findViewById(R.id.startButton);
        start.setOnClickListener(this);

        highScore = findViewById(R.id.scoreButton);
        highScore.setOnClickListener(this);

        exit = findViewById(R.id.exitButton);
        exit.setOnClickListener(this);

    }
//Onclick method used for button implementation
    @Override
    public void onClick(View view) {
        String buttonID = view.toString();

        if (buttonID.contains("startButton")){
            Intent playGame = new Intent(MainActivity.this, GameBoard.class);
            finish();
            startActivity(playGame);
        }
        else if (buttonID.contains("scoreButton")){
            Intent viewScores = new Intent(MainActivity.this, ScoreBoard.class);
            finish();
            startActivity(viewScores);
        }
        else if (buttonID.contains("exitButton")){
            finish();
            System.exit(0);
        }
    }
}