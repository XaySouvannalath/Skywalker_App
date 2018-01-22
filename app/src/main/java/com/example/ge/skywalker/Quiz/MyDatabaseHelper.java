package com.example.ge.skywalker.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by GE on 1/22/2018.
 */

class MyDataBaseHelper extends SQLiteOpenHelper {

    // Database name
    public static String DATABASE_QUESTION = "questionBank.db";
    // Current version of database
    private static final int DATABASE_VERSION = 1;
    // Database table name
    private static final String TABLE_QUESTION = "QuestionBank";
    private static final String TABLE_QUESTION_SQL = "tb_sql_question";

    // All fields used in database table
    private static final String KEY_ID = "id";
    private static final String QUESTION = "question";
    private static final String CHOICE1 = "choice1";
    private static final String CHOICE2 = "choice2";
    private static final String CHOICE3 = "choice3";
    private static final String CHOICE4 = "choice4";
    private static final String ANSWER = "answer";
    // all fields use in tb_sql_questin;
    private static final String KEY_ID_SQL = "id_sql";
    private static final String QUESTION_SQL = "question_sql";
    private static final String CHOICE1_SQL = "choice1_sql";
    private static final String CHOICE2_SQL = "choice2_sql";
    private static final String CHOICE3_SQL = "choice3_sql";
    private static final String CHOICE4_SQL = "choice4_sql";
    private static final String ANSWER_SQL = "answer_sql";



    // Question Table Create Query in this string
    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
            + TABLE_QUESTION_SQL + "(" + KEY_ID_SQL
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTION_SQL + " TEXT,"
            + CHOICE1_SQL + " TEXT, " + CHOICE2_SQL + " TEXT, " + CHOICE3_SQL + " TEXT, "
            + CHOICE4_SQL + " TEXT, " + ANSWER_SQL + " TEXT);";

    //create table of sql question
    private static final String CREATE_TABLE_QUESTION_SQL = "CREATE TABLE "
            + TABLE_QUESTION + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTION + " TEXT,"
            + CHOICE1 + " TEXT, " + CHOICE2 + " TEXT, " + CHOICE3 + " TEXT, "
            + CHOICE4 + " TEXT, " + ANSWER + " TEXT);";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_QUESTION, null, DATABASE_VERSION);
    }

    /**
     * This method is called by system if the database is accessed but not yet
     * created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTION); // create question table
        db.execSQL(CREATE_TABLE_QUESTION_SQL);
    }

    /**
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_QUESTION);// drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_QUESTION_SQL);
        onCreate(db);
    }

    /**
     * This method is used to add question detail in question Table
     */
    public long addInitialQuestion (Question_Linux question) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(QUESTION, question.getQuestion());
        values.put(CHOICE1, question.getChoice(0));
        values.put(CHOICE2, question.getChoice(1));
        values.put(CHOICE3,  question.getChoice(2));
        values.put(CHOICE4,  question.getChoice(3));
        values.put(ANSWER, question.getAnswer());
        // insert row in question table
        long insert = db.insert(TABLE_QUESTION, null, values);
        return insert;
    }

    public long addInitialQuestion_SQL (Question_SQL question) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(QUESTION_SQL, question.getQuestion());
        values.put(CHOICE1_SQL, question.getChoice(0));
        values.put(CHOICE2_SQL, question.getChoice(1));
        values.put(CHOICE3_SQL,  question.getChoice(2));
        values.put(CHOICE4_SQL,  question.getChoice(3));
        values.put(ANSWER_SQL, question.getAnswer());
        // insert row in question table
        long insert = db.insert(TABLE_QUESTION_SQL, null, values);
        return insert;
    }

    /**
     * To extract data from database and save it Arraylist of data type
     * Question
     */
    public List<Question_Linux> getAllQuestionsList() {
        List<Question_Linux> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question_Linux question = new Question_Linux();

                String questText= c.getString(c.getColumnIndex(QUESTION));
                question.setQuestion(questText);

                String choice1Text= c.getString(c.getColumnIndex(CHOICE1));
                question.setChoice(0,choice1Text);

                String choice2Text= c.getString(c.getColumnIndex(CHOICE2));
                question.setChoice(1,choice2Text);

                String choice3Text= c.getString(c.getColumnIndex(CHOICE3));
                question.setChoice(2,choice3Text);

                String choice4Text= c.getString(c.getColumnIndex(CHOICE4));
                question.setChoice(3,choice4Text);

                String answerText= c.getString(c.getColumnIndex(ANSWER));
                question.setAnswer(answerText);

                // adding to Questions list
                questionArrayList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }
    // FOR sql
    public List<Question_SQL> getAllQuestionsList_sql() {
        List<Question_SQL> questionArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION_SQL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question_SQL question = new Question_SQL();

                String questText= c.getString(c.getColumnIndex(QUESTION_SQL));
                question.setQuestion(questText);

                String choice1Text= c.getString(c.getColumnIndex(CHOICE1_SQL));
                question.setChoice(0,choice1Text);

                String choice2Text= c.getString(c.getColumnIndex(CHOICE2_SQL));
                question.setChoice(1,choice2Text);

                String choice3Text= c.getString(c.getColumnIndex(CHOICE3_SQL));
                question.setChoice(2,choice3Text);

                String choice4Text= c.getString(c.getColumnIndex(CHOICE4_SQL));
                question.setChoice(3,choice4Text);

                String answerText= c.getString(c.getColumnIndex(ANSWER_SQL));
                question.setAnswer(answerText);

                // adding to Questions list
                questionArrayList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionArrayList);
        }
        return questionArrayList;
    }
}
