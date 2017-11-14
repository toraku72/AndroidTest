package com.toracode.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExamActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHEAT_1 = 1;
    private static final int REQUEST_CODE_CHEAT_2 = 2;
    private static final int REQUEST_CODE_FINISH = 20;
    private static final String KEY_CURRENT_INDEX = "com.toracode.androidtest.current_index";
    private static final String KEY_CORRECT_COUNT = "com.toracode.androidtest.correct_count";
    private static final String KEY_USER_CHEAT_1 = "com.toracode.androidtest.user_cheat_1";
    private static final String KEY_USER_CHEAT_2 = "com.toracode.androidtest.user_cheat_2";

    private TextView mQuestionTextView;
    private Button mTrueButton, mFalseButton;
    private Button mNextButton;
    private Button mCheatButton1, mCheatButton2;
    private Button mFinishButton;

    private boolean mDidUserCheat1, mDidUserCheat2;

    private int mCorrectCount = 0;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = {
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, false),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, true),
            new Question(R.string.question_8, false),
            new Question(R.string.question_9, false),
            new Question(R.string.question_10, true),


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mCheatButton1 = (Button) findViewById(R.id.cheat_button_1);
        mCheatButton2 = (Button) findViewById(R.id.cheat_button_2);
        mFinishButton = (Button) findViewById(R.id.finish_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                allowAnswer(false);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                allowAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex < mQuestionBank.length - 1) {
                    mCurrentIndex++;
                    updateQuestion();
                    // Enable buttons for next question.
                    allowAnswer(true);
                }
                else {
                    mNextButton.setClickable(false);
                    Toast.makeText(ExamActivity.this, R.string.finish_toast, Toast.LENGTH_SHORT).show();
                    mFinishButton.setVisibility(View.VISIBLE);

                }
            }
        });

        mCheatButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = Cheat1Activity.newIntent(ExamActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT_1);
            }
        });

        mCheatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = Cheat2Activity.newIntent(ExamActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT_2);
            }
        });

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalAnswers = mQuestionBank.length;
                Intent intent = FinishActivity.newIntent(ExamActivity.this, mCorrectCount, totalAnswers);
                startActivityForResult(intent, REQUEST_CODE_FINISH);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT_1) {
            if (data == null) {
                return;
            }
            mDidUserCheat1 = Cheat1Activity.didUserCheat1(data);
        }
        if (mDidUserCheat1) {
            mCheatButton1.setClickable(false);
        }

        if (requestCode == REQUEST_CODE_CHEAT_2) {
            if (data == null) {
                return;
            }
            mDidUserCheat2 = Cheat2Activity.didUserCheat2(data);
        }
        if (mDidUserCheat2) {
            mCheatButton2.setClickable(false);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_CORRECT_COUNT, mCorrectCount);
        savedInstanceState.putBoolean(KEY_USER_CHEAT_1, mDidUserCheat1);
        savedInstanceState.putBoolean(KEY_USER_CHEAT_2, mDidUserCheat2);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (answerIsTrue == userPressedTrue) {
            mCorrectCount++;
            messageResId = R.string.correct_toast;
        }
        else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(ExamActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /** Enable or disable all test buttons except Next button. */
    private void allowAnswer(boolean allow) {
        mTrueButton.setClickable(allow);
        mFalseButton.setClickable(allow);
        mCheatButton1.setClickable(allow);
        mCheatButton2.setClickable(allow);
    }
}
