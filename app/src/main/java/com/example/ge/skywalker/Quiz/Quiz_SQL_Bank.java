package com.example.ge.skywalker.Quiz;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE on 1/22/2018.
 */

public class Quiz_SQL_Bank {
    // declare list of Question objects
    List<Question_SQL> list = new ArrayList<>();
    MyDataBaseHelper myDataBaseHelper;

    // method returns number of questions in list
    public int getLength(){
        return list.size();
    }

    // method returns question from list based on list index
    public String getQuestion(int a) {
        return list.get(a).getQuestion();
    }

    // method return a single multiple choice item for question based on list index,
    // based on number of multiple choice item in the list - 1, 2, 3 or 4
    // as an argument
    public String getChoice(int index, int num) {
        return list.get(index).getChoice(num-1);
    }

    //  method returns correct answer for the question based on list index
    public String getCorrectAnswer(int a) {
        return list.get(a).getAnswer();
    }



    public void initQuestions_sql(Context context) {
        myDataBaseHelper = new MyDataBaseHelper(context);
        list = myDataBaseHelper.getAllQuestionsList_sql();//get questions/choices/answers from database

        if (list.isEmpty()) {//if list is empty, populate database with default questions/choices/answers
            myDataBaseHelper.addInitialQuestion_SQL(new Question_SQL("What does SQL Stand for ?",
                    new String[]{"2001", "2003", "2004", "2005"}, "2005"));
            myDataBaseHelper.addInitialQuestion_SQL(new Question_SQL("What is the name of build toolkit for Android Studio?",
                    new String[]{"JVM", "Gradle", "Dalvik", "HAXM"}, "Gradle"));
            myDataBaseHelper.addInitialQuestion_SQL(new Question_SQL("What widget can replace any use of radio buttons?",
                    new String[]{"Toggle Button", "Spinner", "Switch Button", "ImageButton"}, "Spinner"));
            myDataBaseHelper.addInitialQuestion_SQL(new Question_SQL("What is a widget in Android app?",
                    new String[]{"reusable GUI element", "Layout for Activity", "device placed in cans of beer", "build toolkit"}, "reusable GUI element"));
            myDataBaseHelper.addInitialQuestion_SQL(new Question_SQL("What is Linux?",
                    new String[]{"reusable GUI element", "Layout for Activity", "device placed in cans of beer", "build toolkit"}, "reusable GUI element"));
            list = myDataBaseHelper.getAllQuestionsList_sql();//get list from database again

        }
    }

}
