package com.mastega.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mastega.progessmeter.ProgressMeter;

public class ProgressMeterActivity extends AppCompatActivity {

    private int currentState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_meter);


        final ProgressMeter progressMeter = findViewById(R.id.progress_meter);
        Button button = findViewById(R.id.button);

        progressMeter.setStates(
                new String[] {"Details", "Status", "Photo", "Confirm", "Nogg"},
                getDrawable(R.drawable.bad));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressMeter.goToState(currentState + 1);
                currentState++;
            }
        });
    }
}
