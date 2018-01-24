package com.example.ge.skywalker.Quiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ge.skywalker.Chart.vl_score;
import com.example.ge.skywalker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.UserLogin.LoginPassword.globalusername;


public class Quiz_Linux_Activity extends AppCompatActivity {
    private Quiz_Linux_Bank mQuestionLibrary = new Quiz_Linux_Bank();
    DatabaseReference refScores,refScoresNewSet, refTestShowScore;
    private TextView mScoreView;   // view for current total score
    private TextView mQuestionView;  //current question to answer
    private Button mButtonChoice1; // multiple choice 1 for mQuestionView
    private Button mButtonChoice2; // multiple choice 2 for mQuestionView
    private Button mButtonChoice3; // multiple choice 3 for mQuestionView
    private Button mButtonChoice4; // multiple choice 4 for mQuestionView
List<vl_score> valueScore;
    AlertDialog.Builder aa;
    private String mAnswer;  // correct answer for question in mQuestionView
    private int mScore = 0;  // current total score
    private int mQuestionNumber = 0; // current question number
    MediaPlayer ringcorrect, ringwrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__linux);
        // setup screen for the first question with four alternative to answer
        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);
        ringcorrect = MediaPlayer.create(this,R.raw.correct);
      aa = new AlertDialog.Builder(getApplicationContext());
        valueScore = new ArrayList<>();
        ringwrong = MediaPlayer.create(this,R.raw.wrong);
//UploadScore(1);
        mQuestionLibrary.initQuestions(getApplicationContext());
        updateQuestion();
        // show current total score for the user
        updateScore(mScore);


    }

    private void updateQuestion(){
        // check if we are not outside array bounds for questions
        if(mQuestionNumber<mQuestionLibrary.getLength() ){
            // set the text for new question,
            // and new 4 alternative to answer on four buttons
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber,4));
            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;
        }
        else {
           // Toast.makeText(Quiz_Linux_Activity.this, "It was the last question!", Toast.LENGTH_SHORT).show();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Quiz_Linux_Activity.this, Quiz_Score_Activity.class);
                    intent.putExtra("score", mScore); // pass the current score to the second screen
                    startActivity(intent);
                }
            }, 5000); */

            Intent intent = new Intent(Quiz_Linux_Activity.this, Quiz_Score_Activity.class);
            intent.putExtra("score", mScore); // pass the current score to the second screen
            startActivity(intent);





        }
    }

    // show current total score for the user
    private void updateScore(int point) {
        mScoreView.setText(""+mScore+"/"+mQuestionLibrary.getLength());
    }

    public void onClick(View view) {
        //all logic for all answers buttons in one method
        Button answer = (Button) view;
        // if the answer is correct, increase the score
        if (answer.getText().equals(mAnswer)){
            mScore = mScore + 1;
            ringcorrect.start();
          //  Toast.makeText(Quiz_Linux_Activity.this, "Correct!", Toast.LENGTH_SHORT).show();
            setColorforChoiceButton();
            UploadScore(1);
        }else{
            ringwrong.start();
         //   Toast.makeText(Quiz_Linux_Activity.this, "Wrong!", Toast.LENGTH_SHORT).show();
           setColorforChoiceButton();

        }
        // show current total score for the user
        updateScore(mScore);
        // once user answer the question, we move on to the next one, if any


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(Quiz_Linux_Activity.this, "delay 5 seconds", Toast.LENGTH_SHORT).show();
                updateQuestion();
                mButtonChoice1.setBackgroundResource(R.drawable.back);
                mButtonChoice2.setBackgroundResource(R.drawable.back);
                mButtonChoice3.setBackgroundResource(R.drawable.back);
                mButtonChoice4.setBackgroundResource(R.drawable.back);
            }
        },3000);

    }
    public void setColorforChoiceButton(){
        if(!mButtonChoice1.getText().toString().equals(mAnswer)){
            mButtonChoice1.setBackgroundResource(R.drawable.backofwrong);
        }
        if(!mButtonChoice2.getText().toString().equals(mAnswer)){
            mButtonChoice2.setBackgroundResource(R.drawable.backofwrong);
        }
        if(!mButtonChoice3.getText().toString().equals(mAnswer)){
            mButtonChoice3.setBackgroundResource(R.drawable.backofwrong);
        }
        if(!mButtonChoice4.getText().toString().equals(mAnswer)){
            mButtonChoice4.setBackgroundResource(R.drawable.backofwrong);
        }
        if(mButtonChoice1.getText().toString().equals(mAnswer)){
            mButtonChoice1.setBackgroundResource(R.drawable.backofcorrect);
          //  UploadScore(1);
        }
        if(mButtonChoice2.getText().toString().equals(mAnswer)){
            mButtonChoice2.setBackgroundResource(R.drawable.backofcorrect);
          //  UploadScore(1);
        }
        if(mButtonChoice3.getText().toString().equals(mAnswer)){
            mButtonChoice3.setBackgroundResource(R.drawable.backofcorrect);
           // UploadScore(1);
        }
        if(mButtonChoice4.getText().toString().equals(mAnswer)){
            mButtonChoice4.setBackgroundResource(R.drawable.backofcorrect);
           // UploadScore(1);
        }
    }
    public void UploadScore(final int newscore){
    refScores = FirebaseDatabase.getInstance().getReference("Scores").child(globalusername).child("score");
        refScoresNewSet = FirebaseDatabase.getInstance().getReference("Scores");
        final String usernamee = globalusername;
        refScores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    vl_score vlScore =new vl_score(usernamee, newscore);
                    refScoresNewSet.child(usernamee).setValue(vlScore);
                }else{
                    int  oldscore = Integer.parseInt(dataSnapshot.getValue().toString());
                    int  newScore;
                    newScore = oldscore + newscore;
                    vl_score vlScore =new vl_score(usernamee, newScore);
                    refScoresNewSet.child(usernamee).setValue(vlScore);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
