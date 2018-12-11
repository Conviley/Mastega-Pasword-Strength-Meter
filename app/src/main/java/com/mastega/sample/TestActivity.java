package com.mastega.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mastega.passwordstrengthmeter.PasswordStrengthMeter;
import com.mastega.passwordstrengthmeter.StrengthChecker;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private PasswordStrengthMeter passwordStrengthMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        passwordStrengthMeter = findViewById(R.id.password_strength_meter);
        passwordStrengthMeter.setToggleVisibilityIcons(
                getDrawable(R.drawable.visible), getDrawable(R.drawable.hidden));

        passwordStrengthMeter.setMaxStrength(2);

        ArrayList<Drawable> strengthIcons = new ArrayList<>();
        strengthIcons.add(getDrawable(R.drawable.circle_neutral));
        strengthIcons.add(getDrawable(R.drawable.circle_bad));
        strengthIcons.add(getDrawable(R.drawable.circle_good));
        passwordStrengthMeter.setStrengthDrawables(strengthIcons);

        passwordStrengthMeter.setStrengthChecker(createStrengthChecker());
    }

    private StrengthChecker createStrengthChecker() {
        StrengthChecker strengthChecker = new StrengthChecker() {
            private int strength = 0;
            @Override
            public int CalculateStrength(String password) {
                strength = 0;
                if (password.length() >= 1) {
                    strength += 1;
                    if (password.length() >= 8) {
                        strength += 1;
                    }
                }
                return strength;
            }

            @Override
            public int setHelperText() {
                switch(strength) {
                    case 1:
                        return R.string.under_eight_test;
                    case 2:
                        return R.string.ideal_password_test;
                    default:
                        return R.string.empty;
                }
            }
        };
        return strengthChecker;
    }

}
