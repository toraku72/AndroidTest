package com.toracode.androidtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CaughtActivity extends AppCompatActivity {
    private Button mReplayButton;
    private Button mToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caught);

        mReplayButton = (Button) findViewById(R.id.replay_button_caught);
        mToHomeButton = (Button) findViewById(R.id.to_home_button);

        mReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = redoActivities();
                startActivity(intent);
            }
        });

        mToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = clearActivities();
                startActivity(intent);
            }
        });
    }
    private Intent clearActivities() {
        Intent intent = new Intent(CaughtActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private Intent redoActivities() {
        Intent intent = new Intent(CaughtActivity.this, ExamActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
