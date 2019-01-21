package com.mastega.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mastega.progessmeter.OnStateChangeListener;
import com.mastega.progessmeter.ProgressMeter;

public class ProgressMeterActivity extends AppCompatActivity {

    private int currentState = 0;

    private ProgressMeter progressMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_meter);


        progressMeter = findViewById(R.id.progress_meter);
        Button buttonNextState = findViewById(R.id.next_state);
        Button buttonPrevState = findViewById(R.id.prev_state);

        progressMeter.setStates(
                new String[] {"Details", "Status", "Photo", "Confirm", "Shipping"},
                getDrawable(R.drawable.circle_good));

        progressMeter.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                updateStateInfo(state);
            }
        });
        buttonNextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressMeter.nextState();
            }
        });
        buttonPrevState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressMeter.prevState();
            }
        });
    }

    private void updateStateInfo(int currentState) {
        TextView textView = findViewById(R.id.text_stuff);
        switch (currentState) {
            case 0:
                textView.setText("This is just some text. You are now at the first step.");
            case 1:
                textView.setText("Now we're at text 2");
                break;
            case 2:
                textView.setText("Now You're supposed to do this");
                break;
            case 3:
                textView.setText("Do this now");
                break;
            case 4:
                textView.setText("Now this is the final step");
                break;
        }
    }
}
