package com.petillon.alexandre.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.petillon.alexandre.topquiz.R;
import com.petillon.alexandre.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 1;
    public static final String BUNDLE_EXTRA_FIRSTNAME = "firstname";
    public static final String BUNDLE_EXTRA_SCORE = "score";

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        preferences =  getPreferences(MODE_PRIVATE);

        String firstname = preferences.getString(BUNDLE_EXTRA_FIRSTNAME, null);
        if (null == firstname) {
            mPlayButton.setEnabled(false);
        } else {
            int score = preferences.getInt(BUNDLE_EXTRA_SCORE, 0);
            mGreetingText.setText("Hello " + firstname + "! "
                + "Your last score is " + score + ", can you do better?");
            mUser.setFirstname(firstname);
            mUser.setScore(score);
            mNameInput.setText(firstname);
        }

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayButton.setEnabled(charSequence.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                mUser.setFirstname(mNameInput.getText().toString());
                preferences.edit().putString(BUNDLE_EXTRA_FIRSTNAME, mUser.getFirstname()).apply();
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            preferences.edit().putInt(BUNDLE_EXTRA_SCORE, score).apply();
            mGreetingText.setText("Hey " + mUser.getFirstname() + "! "
                    + "Your just did a score of " + score + ", can you do better?");
            mUser.setScore(score);
        }
    }
}
