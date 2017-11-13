package com.toracode.androidtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
    private static final String EXTRA_CORRECT_ANSWERS = "com.toracode.androidtest.correct_answers";
    private static final String EXTRA_TOTAL_ANSWERS = "com.toracode.androidtest.total_answers";
    private int mCorrectAnswers;
    private int mTotalAnswers;
    private TextView mResultTextView;
    private Button mReplayButton;

    public static Intent newIntent(Context packageContext, int correctAnswers, int totalAnswers) {
        Intent intent = new Intent(packageContext, FinishActivity.class);
        intent.putExtra(EXTRA_CORRECT_ANSWERS, correctAnswers);
        intent.putExtra(EXTRA_TOTAL_ANSWERS, totalAnswers);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        mResultTextView = (TextView) findViewById(R.id.result_text_view);
        mReplayButton = (Button) findViewById(R.id.replay_button);
        printScore();

        mReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = clearActivities();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    private Intent clearActivities() {
        Intent intent = new Intent(FinishActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private void printScore() {
        Intent prevIntent = getIntent();
        mCorrectAnswers = prevIntent.getIntExtra(EXTRA_CORRECT_ANSWERS, 0);
        mTotalAnswers = prevIntent.getIntExtra(EXTRA_TOTAL_ANSWERS, 1);
        double scoreDouble = 10.0 * mCorrectAnswers / mTotalAnswers;
        String scoreString = String.format("%.2f", scoreDouble);
        mResultTextView.setText("Bạn đã trả lời đúng " + mCorrectAnswers + "/" + mTotalAnswers + " câu hỏi và ghi được " + scoreDouble +" điểm.");
    }
}
