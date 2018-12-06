package com.mastega.passwordstrengthmeter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public class PasswordStrengthMeter extends LinearLayout{

    private CardView container;

    private RelativeLayout cardViewLayout;

    private TextView errorTextView;

    private EditText inputField;

    private ArrayList<Integer> strengthIcons;
    private ImageView strengthIcon;

    private ImageView toggleVisibility;

    private String placeholderText = "PASSWORD";

    private Context ctx;

    private int currentStrength;

    private StockStrengthChecker strengthChecker;

    private boolean hidden = true;

    public PasswordStrengthMeter(Context context) {
        super(context);
        init(context);
    }

    public PasswordStrengthMeter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PasswordStrengthMeter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PasswordStrengthMeter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.ctx = context;
        this.setOrientation(VERTICAL);

        container = new CardView(ctx);

        inputField = new EditText(ctx);
        inputField.setId(View.generateViewId());
        inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        errorTextView = new TextView(ctx);
        toggleVisibility = new ImageView(ctx);
        toggleVisibility.setBackgroundResource(R.drawable.monkyblind);
        toggleVisibility.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidden) {
                    hidden = false;
                    toggleVisibility.setBackgroundResource(R.drawable.monkeysee);
                    inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    hidden = true;
                    toggleVisibility.setBackgroundResource(R.drawable.monkyblind);
                    inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                inputField.setSelection(inputField.getText().length());
            }
        });

        inputField.setBackground(null);

        cardViewLayout = new RelativeLayout(ctx);

        strengthIcons = new ArrayList<>();
        strengthIcon = new ImageView(ctx);

        strengthIcon.setId(View.generateViewId());
        strengthIcon.setBackgroundResource(R.drawable.nomouthgray);

        strengthChecker = new StockStrengthChecker();


        RelativeLayout.LayoutParams inputFieldParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        inputFieldParams.addRule(RelativeLayout.RIGHT_OF, strengthIcon.getId());


        RelativeLayout.LayoutParams strengthIconParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        strengthIconParams.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout.LayoutParams toggleVisibilityParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        toggleVisibilityParams.addRule(RelativeLayout.CENTER_VERTICAL);
        toggleVisibilityParams.addRule(RelativeLayout.ALIGN_PARENT_END);


        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
               1000, LayoutParams.MATCH_PARENT
        );

        containerParams.gravity = Gravity.CENTER;



        cardViewLayout.setPadding(dpAsPixels(5),0,dpAsPixels(5),0);

        strengthIcons.add(R.drawable.nomouthgray);
        strengthIcons.add(R.drawable.cry);
        strengthIcons.add(R.drawable.bad);
        strengthIcons.add(R.drawable.okay);
        strengthIcons.add(R.drawable.good);
        strengthIcons.add(R.drawable.awesome);
        strengthIcons.add(R.drawable.love);

        //containerParams.setMargins(0, dpAsPixels, 0 ,0 );

        container.setCardBackgroundColor(Color.parseColor("#f7f8f9"));
        inputField.setHint(R.string.place_holder);

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int strength = strengthChecker.CalculateStrength(s.toString());
                if (currentStrength != strength) {
                    setStrengthIcon(strength);
                    currentStrength = strength;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        errorTextView.setText(R.string.test_error);

        //FILL LAYOUT WITH STUFF
        cardViewLayout.addView(strengthIcon, strengthIconParams);
        cardViewLayout.addView(inputField, inputFieldParams);
        cardViewLayout.addView(toggleVisibility, toggleVisibilityParams);
        container.addView(cardViewLayout);

        this.addView(container, containerParams);
        this.addView(errorTextView);
    }

    private void setStrengthIcon (int strength) {
        if (strength > 6 || strength < 0) {
            throw new IllegalArgumentException("Number out of range! Must be within (0-5)");

        }
        int Icon = strengthIcons.get(strength);
        strengthIcon.setBackgroundResource(Icon);

        final Animation slide_up = AnimationUtils.loadAnimation(ctx, R.anim.scale_up);
        strengthIcon.startAnimation(slide_up);

        invalidate();
        requestLayout();
    }

    private int dpAsPixels (int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }
}
