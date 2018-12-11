package com.mastega.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mastega.passwordstrengthmeter.PasswordStrengthMeter;
import com.mastega.passwordstrengthmeter.StrengthChecker;

public class TestActivity extends AppCompatActivity {

    private PasswordStrengthMeter passwordStrengthMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        passwordStrengthMeter = findViewById(R.id.password_strength_meter);
        passwordStrengthMeter.setHiddenDrawable(getDrawable(R.drawable.ic_launcher_background));
    }

    private void setStrengthChecker() {
        StrengthChecker strengthChecker = new StrengthChecker() {
            @Override
            public int CalculateStrength(String password) {
                return 0;
            }

            @Override
            public int setHelperText() {
                return 0;
            }
        };
    }

}
