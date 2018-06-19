package com.petillon.alexandre.topquiz.controller;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.petillon.alexandre.topquiz.R;
import com.petillon.alexandre.topquiz.model.Question;
import com.petillon.alexandre.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_QUESTIONBANK = "questionBank";
    public static final String BUNDLE_STATE_QUESTION = "question";
    public static final String BUNDLE_STATE_NUMBER_QUESTION = "numberQuestion";
    public static final String BUNDLE_STATE_SCORE = "score";

    private TextView mQuestionText;
    private Button mAnswerButtonA;
    private Button mAnswerButtonB;
    private Button mAnswerButtonC;
    private Button mAnswerButtonD;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mScore;

    private boolean mEnableTouchEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionText = (TextView) findViewById(R.id.activity_game_question);
        mAnswerButtonA = (Button) findViewById(R.id.activity_game_btn_a);
        mAnswerButtonB = (Button) findViewById(R.id.activity_game_btn_b);
        mAnswerButtonC = (Button) findViewById(R.id.activity_game_btn_c);
        mAnswerButtonD = (Button) findViewById(R.id.activity_game_btn_d);

        //super.onCreate(savedInstanceState);

        mQuestionBank = this.generateQuestion();
        if (savedInstanceState != null) {
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_NUMBER_QUESTION);
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mCurrentQuestion = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTIONBANK);
        } else {
            mNumberOfQuestions = 3;
            mScore = 0;
            mQuestionBank = this.generateQuestion();
            mCurrentQuestion = mQuestionBank.getQuestion();
        }
        mEnableTouchEvent = true;

        mAnswerButtonA.setTag(0);
        mAnswerButtonB.setTag(1);
        mAnswerButtonC.setTag(2);
        mAnswerButtonD.setTag(3);

        mAnswerButtonA.setOnClickListener(this);
        mAnswerButtonB.setOnClickListener(this);
        mAnswerButtonC.setOnClickListener(this);
        mAnswerButtonD.setOnClickListener(this);

        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_NUMBER_QUESTION, mNumberOfQuestions);
        outState.putParcelable(BUNDLE_STATE_QUESTION, mCurrentQuestion);
        outState.putParcelable(BUNDLE_STATE_QUESTIONBANK, mQuestionBank);

        super.onSaveInstanceState(outState);

    }

    private void displayQuestion(Question currentQuestion) {
        mQuestionText.setText(currentQuestion.getQuestion());
        mAnswerButtonA.setText(currentQuestion.getChoiceList().get(0));
        mAnswerButtonB.setText(currentQuestion.getChoiceList().get(1));
        mAnswerButtonC.setText(currentQuestion.getChoiceList().get(2));
        mAnswerButtonD.setText(currentQuestion.getChoiceList().get(3));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvent && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mEnableTouchEvent = false;
        int answerIndex = (int) view.getTag();

        if (answerIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (--mNumberOfQuestions <= 0) {
                    endMessage();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                    mEnableTouchEvent = true;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    private void endMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private QuestionBank generateQuestion () {
        Question question1 = new Question("What are the first three words of the Constitution?",
                Arrays.asList("We the People",
                        "We the world",
                        "Yes we can",
                        "Make america great"),
                0);

        Question question2 = new Question("How many stars does the flag have?",
                Arrays.asList("54",
                        "42",
                        "50",
                        "60"),
                2);

        Question question3 = new Question("When do we celebrate Independence Day?",
                Arrays.asList("May 1",
                        "July 4",
                        "August 14",
                        "September 8"),
                1);

        Question question4 = new Question("What is the capital of the United States?",
                Arrays.asList("New York",
                        "Los Angeles",
                        "Las vegas",
                        "Washington, D.C."),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4));
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mNumberOfQuestions);
        parcel.writeInt(mScore);
        parcel.writeParcelable(mQuestionBank, i);
        parcel.writeParcelable(mCurrentQuestion, i);
    }

    public static final Parcelable.Creator<GameActivity> CREATOR
            = new Parcelable.Creator<GameActivity>() {
        public GameActivity createFromParcel(Parcel in) {
            return new GameActivity(in);
        }

        public GameActivity[] newArray(int size) {
            return new GameActivity[size];
        }
    };

    private GameActivity(Parcel in) {
        this.mNumberOfQuestions = in.readInt();
        this.mScore = in.readInt();
        this.mQuestionBank = in.readParcelable(QuestionBank.class.getClassLoader());
        this.mCurrentQuestion = in.readParcelable(Question.class.getClassLoader());
    }
    */
}
