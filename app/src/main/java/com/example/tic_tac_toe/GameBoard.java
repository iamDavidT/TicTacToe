package com.example.tic_tac_toe;
/*
*This is the Gameboard section of the app. The game will function within this
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameBoard extends AppCompatActivity implements View.OnClickListener {
//Initialise class variables
    private Button[][] gridButtons = new Button[3][3];
    private Button homeBtn, endGameBtn;

    private String p1Symbol = "X";
    private String p2Symbol = "O";
    int p1Score = 0;
    int p2Score = 0;
    private boolean player1Turn;
    private Random random = new Random();
    private TextView tvTurn;
    private int noClicks = 0;

    Intent resultPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
//Below is to set gridbuttons for the game to the buttons used in the layout
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String boardButtonID = "button_" + i + j;
                int resID = getResources().getIdentifier(boardButtonID, "id", getPackageName());
                gridButtons[i][j] = findViewById(resID);
                gridButtons[i][j].setOnClickListener(this);
            }
        }
//These onClick methods are used for the navigation buttons to keep them seperate from the game logic
        homeBtn = findViewById(R.id.homeButton1);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(GameBoard.this, MainActivity.class);
                finish();
                startActivity(goHome);
            }
        });

        endGameBtn = findViewById(R.id.endGameButton);
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//Randomise who goes first
        player1Turn = random.nextBoolean();
        tvTurn = findViewById(R.id.textViewTurn);
        if(player1Turn == true){
            tvTurn.setText("Player X goes first");
        }
        else {
            tvTurn.setText("Player O goes first");
        }


    }

    @Override
    public void onClick(View view) {
        tvTurn.setText("");
//if statement that will stop a button being selected that is not empty
        if (!((Button) view).getText().toString().equals("")){
            return;
        }
        if (player1Turn == true){ //if statement that will place the players symbol, increase scores and call the checkWin method
            ((Button) view).setText(p1Symbol);
            noClicks++;
            p1Score++;
            if(checkWin()){
                resultPage = new Intent(GameBoard.this, Result.class);
                resultPage.putExtra("totalClicks", noClicks);
                resultPage.putExtra("p1Score", p1Score);
                resultPage.putExtra("p2Score", p2Score);
                finish();
                startActivity(resultPage);

            }
            player1Turn = false;
            nextMove();

        }
        else{
            ((Button) view).setText(p2Symbol);
            noClicks++;
            p2Score++;
            if (checkWin()){
                resultPage = new Intent(GameBoard.this, Result.class);
                resultPage.putExtra("totalClicks", noClicks);
                resultPage.putExtra("p1Score", p1Score);
                resultPage.putExtra("p2Score", p2Score);
                finish();
                startActivity(resultPage);

            }
            player1Turn = true;
            nextMove();
        }

    }
//method used to check the game board to see if there are three matching symbols in a row, returns false if there is not
    private boolean checkWin(){
        String[][] symbols = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                symbols[i][j] = gridButtons[i][j].getText().toString();
            }
        }

        for(int i = 0; i < 3; i++){
            if(symbols[i][0].equals(symbols[i][1]) && symbols[i][0].equals(symbols[i][2]) && !symbols[i][0].equals("")){
                return true;
            }
        }
        for(int j = 0; j < 3; j++){
            if(symbols[0][j].equals(symbols[1][j]) && symbols[0][j].equals(symbols[2][j]) && !symbols[0][j].equals("")){
                return true;
            }
        }

        if(symbols[0][0].equals(symbols[1][1]) && symbols[0][0].equals(symbols[2][2]) && !symbols[0][0].equals("")){
            return true;
        }

        if(symbols[0][2].equals(symbols[1][1]) && symbols[0][2].equals(symbols[2][0]) && !symbols[0][2].equals("")){
            return true;
        }

        return false;
    }
//Next move method used to check the board to see if there are 2 symbols in a row and automatically places the other players symbol in the position to block them winning
    private void nextMove(){
        String[][] symbols = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                symbols[i][j] = gridButtons[i][j].getText().toString();
            }
        }
        if(player1Turn == false){
            for(int i = 0; i < 3; i++){
                if(symbols[i][0].equals(symbols[i][1]) && !symbols[i][0].equals("") && symbols[i][2].equals("")){
                    gridButtons[i][2].setText(p2Symbol);
                    noClicks++;
                    p2Score++;
                    player1Turn = true;
                }
                else if (symbols[i][1].equals(symbols[i][2]) && !symbols[i][1].equals("") && symbols[i][0].equals("")) {
                    gridButtons[i][0].setText(p2Symbol);
                    noClicks++;
                    p2Score++;
                   player1Turn = true;
                }

            }

        }
        else{
            for(int j = 0; j < 3; j++){
                if(symbols[j][0].equals(symbols[j][1]) && !symbols[j][0].equals("") && symbols[j][2].equals("")){
                    gridButtons[j][2].setText(p1Symbol);
                    noClicks++;
                    p1Score++;
                    player1Turn = false;
                }

                else if(symbols[j][1].equals(symbols[j][2]) && !symbols[j][1].equals("") && symbols[j][0].equals("")){
                    gridButtons[j][0].setText(p1Symbol);
                    noClicks++;
                    p1Score++;
                    player1Turn = false;
                }

            }


        }
        if(checkWin()){
            resultPage = new Intent(GameBoard.this, Result.class);
            resultPage.putExtra("totalClicks", noClicks);
            resultPage.putExtra("p1Score", p1Score);
            resultPage.putExtra("p2Score", p2Score);
            resultPage.putExtra("turn", player1Turn);
            finish();
            startActivity(resultPage);


        }
        else if (noClicks == 9){
            resultPage = new Intent(GameBoard.this, Result.class);
            resultPage.putExtra("totalClicks", noClicks);
            finish();
            startActivity(resultPage);
        }
    }

}
