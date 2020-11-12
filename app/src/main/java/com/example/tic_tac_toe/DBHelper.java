package com.example.tic_tac_toe;

/*
*This is the database helper class, used to create, upgrade, add and display scores. There is a delete method unused
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {
//initialise class variables
    public static final String DATABASE_NAME = "highScores.db";
    public static final String TABLE_NAME = "winners";
    public static final String COLUMN_ID = "playerID";
    public static final String COLUMN_NAME = "playerName";
    public static final String COLUMN_MOVES = "noMoves";
    public static final String COLUMN_DATE_TIME = "dateTime";

//SQLiteOpenHelper constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //onCreate method to create a table in the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE winners" +
                "(playerID integer PRIMARY KEY, playerName text, noMoves integer, dateTime text)");
    }
//onUpgrade method. This will stop the table from being recreated
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS winners");
        onCreate(sqLiteDatabase);
    }
//Used to add the players name and score to the table. A date time stamp will be created and saved into the table also
    public boolean addHighScore(String playerName, int moves){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, playerName);
        contentValues.put(COLUMN_MOVES, moves);
        DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
        String currentTime = df.format(Calendar.getInstance().getTime());
        contentValues.put(COLUMN_DATE_TIME, currentTime);
        database.insert("winners", null, contentValues);
        database.close();

        return true;
    }
//Used to display the data required to the user. shows the name, how many moves the player made and the date-time stamp
    public Cursor displayData(){
        SQLiteDatabase db = getReadableDatabase();


        return db.rawQuery("SELECT playerName, noMoves, dateTime FROM winners ORDER BY noMoves asc", null);
    }
//unused method but will delete the data in the table. created to remove previous saved information during debugging
    public void deleteData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


}
