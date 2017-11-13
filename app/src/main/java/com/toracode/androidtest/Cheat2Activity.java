package com.toracode.androidtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Cheat2Activity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE_2 = "com.toracode.androidtest.answer_is_true_2";
    private static final String EXTRA_USER_CHEATED = "com.toracode.androidtest.user_cheated";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mConfirmCheat2;
    private Button mGoBack2;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, Cheat2Activity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE_2, answerIsTrue);
        return intent;
    }

    public void setUserCheated2(boolean didUserCheat2) {
        Intent data = new Intent();
        data.putExtra(EXTRA_USER_CHEATED, didUserCheat2);
        setResult(RESULT_OK, data);
    }

    public static boolean didUserCheat2(Intent result) {
        return result.getBooleanExtra(EXTRA_USER_CHEATED, false);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat2);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view_2);
        mConfirmCheat2 = (Button) findViewById(R.id.confirm_cheat_2);
        mGoBack2 = (Button) findViewById(R.id.go_back_2);

        mGoBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 2 cases when cheat. The successful case has 30% of occurrence.
        mConfirmCheat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = cheat2Result();
                // Cheat successful. Displaying answer.
                if (result < 3) {
                    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE_2, false);
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    }
                    else {
                        mAnswerTextView.setText(R.string.false_button);
                    }
                }
                // Cheat failed. User gets caught. Moving to CaughtActivity.
                else {
                    startActivity(new Intent(Cheat2Activity.this, CaughtActivity.class));
                }
                mConfirmCheat2.setClickable(false);
                setUserCheated2(true);
            }
        });
    }

    /** Return a random number from 0 to 9*/
    private int cheat2Result() {
        return (int) (Math.random()*10);
    }
}
