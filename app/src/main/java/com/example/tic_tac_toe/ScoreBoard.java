package com.example.tic_tac_toe;

/*
*This is the Activity that displays the database of high scores to the user. Relies on the DBHelper class
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity implements View.OnClickListener {
//Initialise variables
    private Button playAgain, exit;
    DBHelper myDB;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    String name, score, date;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        myDB = new DBHelper(this);
        gridView = (GridView) findViewById(R.id.GridView1);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);//To get around ArrayAdapter needing to use a TextView to work rather than GridView

//Using the Cursor to move to each row of the table, getting the information requested in each row
        Cursor cursor = myDB.displayData();
        if (cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndex("playerName"));
                score = cursor.getString(cursor.getColumnIndex("noMoves"));
                date = cursor.getString(cursor.getColumnIndex("dateTime"));
                list.add(name);
                list.add(score);
                list.add(date);
                gridView.setAdapter(adapter);
            }
            while(cursor.moveToNext());
        }
//setting onClick methods for the navigation buttons
        playAgain = findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(this);

        exit = findViewById(R.id.exitButton2);
        exit.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        String buttonID = view.toString();

        if(buttonID.contains("playAgainButton")){
            Intent intent = new Intent(ScoreBoard.this, GameBoard.class);
            finish();
            startActivity(intent);
        }
        else if (buttonID.contains("exitButton2")){
            finish();
            System.exit(0);
        }

    }
}
