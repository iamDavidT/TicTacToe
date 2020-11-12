package com.example.tic_tac_toe;

/*
*This activity is used to display the winner and score to the players.
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity implements View.OnClickListener {
//initialise variables to be used
    private Button saveScore, playAgain, exit;
    private boolean p1Turn;
    private int p1Score, p2Score, totalClicks;


    TextView resultMessage;
    TextView scoreView;
    EditText playerName;


    DBHelper myDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_screen);
//Get intents from the GameBoard.java class
        p1Score = getIntent().getIntExtra("p1Score", 0);
        p2Score = getIntent().getIntExtra("p2Score", 0);
        totalClicks = getIntent().getIntExtra("totalClicks", 0);
        p1Turn = getIntent().getBooleanExtra("turn", false);

        resultMessage = findViewById(R.id.textViewResult);
        scoreView = findViewById(R.id.textViewNoClicks);
        playerName = (EditText) findViewById(R.id.editTextgetName);
//set onClick listener on the navigation buttons
        saveScore = findViewById(R.id.saveScoreButton);
        saveScore.setOnClickListener(this);
        playAgain = findViewById(R.id.playAgainButton2);
        playAgain.setOnClickListener(this);
        exit = findViewById(R.id.exitButton3);
        exit.setOnClickListener(this);

//if statement to determine what will be displayed to the users based off of the information passed in the intent from the GameBoard
        if (totalClicks == 9){
            resultMessage.setText("We have ourselves a draw");
            TextView getName = findViewById(R.id.textViewGetName);
            getName.setVisibility(View.INVISIBLE);
            playerName.setVisibility(View.INVISIBLE);
            saveScore.setVisibility(View.INVISIBLE);
        }
        else if(p1Score >= p2Score && !p1Turn){
            resultMessage.setText("Player X is the Winner. Congratulations and bad luck Player O");
            scoreView.setText("Your score is : " + p1Score);

        }
        else{
            resultMessage.setText("Player O is the Winner. Congratulations and bad luck Player X");
            scoreView.setText("Your score is: " + p2Score);

        }



    }

    @Override
    public void onClick(View view) {
        String buttonID = view.toString();

        if (buttonID.contains("saveScoreButton")){ //Save the scores to the database
            myDB = new DBHelper(this);
            String name = playerName.getText().toString();
            int score = getHighestScore();
            myDB.addHighScore(name, score);
            Intent intent = new Intent(Result.this, ScoreBoard.class);
            finish();
            startActivity(intent);
        }
        else if (buttonID.contains("playAgainButton2")){ //Play again
            Intent playAgain = new Intent(Result.this, GameBoard.class);
            finish();
            startActivity(playAgain);
        }
        else if (buttonID.contains("exitButton3")){ //Exit
            finish();
            System.exit(0);
        }

    }
//Small function to determine the highest score. used when the saveScoreButton is clicked
    public int getHighestScore(){
        if (p1Score > p2Score){
            return p1Score;
        }
        else{
            return p2Score;
        }
    }


}
