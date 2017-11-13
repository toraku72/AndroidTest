package com.toracode.androidtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cheat1Activity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE_1 = "com.toracode.androidtest.answer_is_true_1";
    private static final String EXTRA_USER_CHEATED = "com.toracode.androidtest.user_cheated";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mConfirmCheat1;
    private Button mGoBack1;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, Cheat1Activity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE_1, answerIsTrue);
        return intent;
    }

    public void setUserCheated1(boolean didUserCheat1) {
        Intent data = new Intent();
        data.putExtra(EXTRA_USER_CHEATED, didUserCheat1);
        setResult(RESULT_OK, data);
    }

    public static boolean didUserCheat1(Intent result) {
        return result.getBooleanExtra(EXTRA_USER_CHEATED, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat1);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view_1);
        mConfirmCheat1 = (Button) findViewById(R.id.confirm_cheat_1);
        mGoBack1 = (Button) findViewById(R.id.go_back_1);

        mGoBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mConfirmCheat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE_1, false);
                int result = cheat1Result();
                // 3 Cases when cheat. Each of first twos case has 20% of occurrence.
                switch (result) {
                    // First case, cheat successful and get correct answer.
                    case 0: {
                        if (mAnswerIsTrue) {
                            mAnswerTextView.setText(R.string.true_button);
                        }
                        else {
                            mAnswerTextView.setText(R.string.false_button);
                        }
                    }
                    break;
                    // Second case, cheat successful but get incorrect answer.
                    case 1: {
                        if (mAnswerIsTrue) {
                            mAnswerTextView.setText(R.string.false_button);
                        }
                        else {
                            mAnswerTextView.setText(R.string.true_button);
                        }
                    }
                    break;
                    // Final case, cheat failed. User gets caught. Moving to CaughtActivity.
                    default: {
                        startActivity(new Intent(Cheat1Activity.this, CaughtActivity.class));
                    }
                }
                mConfirmCheat1.setClickable(false);
                setUserCheated1(true);
            }
        });

    }

    /** Return a random number from 0 to 4.*/
    private int cheat1Result() {
        return (int) (Math.random() * 5);
    }
}
