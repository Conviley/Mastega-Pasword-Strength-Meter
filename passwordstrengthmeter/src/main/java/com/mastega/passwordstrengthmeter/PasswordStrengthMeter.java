package com.mastega.passwordstrengthmeter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public class PasswordStrengthMeter extends LinearLayout{

    private TextView errorTextView;
    private EditText inputField;
    private ImageView strengthIcon;
    private ImageView toggleVisibility;

    private ArrayList<Drawable> strengthIcons;
    private Context ctx;
    private StrengthChecker strengthChecker;
    private Drawable visibleIcon;
    private Drawable hiddenIcon;

    private int currentStrength;
    private boolean hidden = true;
    private int maxStrength = 6;

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

        CardView container = new CardView(ctx);

        inputField = new EditText(ctx);
        inputField.setId(View.generateViewId());
        inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        errorTextView = new TextView(ctx);
        toggleVisibility = new ImageView(ctx);
        hiddenIcon = ctx.getDrawable(R.drawable.monkyblind);
        visibleIcon = ctx.getDrawable(R.drawable.monkeysee);
        toggleVisibility.setBackground(hiddenIcon);
        toggleVisibility.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidden) {
                    hidden = false;
                    toggleVisibility.setBackground(visibleIcon);
                    inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    hidden = true;
                    toggleVisibility.setBackground(hiddenIcon);
                    inputField.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                inputField.setSelection(inputField.getText().length());
            }
        });

        inputField.setBackground(null);

        RelativeLayout cardViewLayout = new RelativeLayout(ctx);

        strengthIcons = new ArrayList<>();
        strengthIcon = new ImageView(ctx);

        strengthIcon.setId(View.generateViewId());
        strengthIcon.setBackgroundResource(R.drawable.nomouthgray);

        strengthChecker = new StockStrengthChecker(ctx);


        RelativeLayout.LayoutParams inputFieldParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        inputFieldParams.addRule(RelativeLayout.RIGHT_OF, strengthIcon.getId());


        RelativeLayout.LayoutParams strengthIconParams = new RelativeLayout.LayoutParams(
                dpAsPixels(25), dpAsPixels(25));

        strengthIconParams.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout.LayoutParams toggleVisibilityParams = new RelativeLayout.LayoutParams(
                dpAsPixels(25), dpAsPixels(25));

        toggleVisibilityParams.addRule(RelativeLayout.CENTER_VERTICAL);
        toggleVisibilityParams.addRule(RelativeLayout.ALIGN_PARENT_END);


        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
               LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        );

        containerParams.gravity = Gravity.CENTER;

        containerParams.setMargins(dpAsPixels(1),0,dpAsPixels(1),0); //hax to keep shadows.

        cardViewLayout.setPadding(dpAsPixels(5),0,dpAsPixels(5),0);

        strengthIcons.add(ctx.getDrawable(R.drawable.nomouthgray));
        strengthIcons.add(ctx.getDrawable(R.drawable.cry));
        strengthIcons.add(ctx.getDrawable(R.drawable.bad));
        strengthIcons.add(ctx.getDrawable(R.drawable.okay));
        strengthIcons.add(ctx.getDrawable(R.drawable.good));
        strengthIcons.add(ctx.getDrawable(R.drawable.awesome));
        strengthIcons.add(ctx.getDrawable(R.drawable.love));

        //containerParams.setMargins(0, dpAsPixels, 0 ,0 );

        container.setCardBackgroundColor(Color.parseColor("#f7f8f5"));
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
                    errorTextView.setText(strengthChecker.getHelperText());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //errorTextView.setText(R.string.test_error);

        //FILL LAYOUT WITH STUFF
        cardViewLayout.addView(strengthIcon, strengthIconParams);
        cardViewLayout.addView(inputField, inputFieldParams);
        cardViewLayout.addView(toggleVisibility, toggleVisibilityParams);
        container.addView(cardViewLayout);

        this.addView(container, containerParams);
        this.addView(errorTextView);
    }

    private void setStrengthIcon (int strength) {
        if (strength > maxStrength || strength < 0) {
            throw new IllegalArgumentException("Strength out of range! Must be within (0-" + maxStrength + ")! Your strength: " + strength);

        }
        Drawable icon = strengthIcons.get(strength);
        strengthIcon.setBackground(icon);

        final Animation slide_up = AnimationUtils.loadAnimation(ctx, R.anim.scale_up);
        strengthIcon.startAnimation(slide_up);

        invalidate();
        requestLayout();
    }

    private int dpAsPixels (int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    public void setStrengthChecker(StrengthChecker strengthChecker){
        this.strengthChecker = strengthChecker;
    }

    public void setStrengthDrawables(ArrayList<Drawable> strengthIcons){
        if (strengthIcons.size() == maxStrength + 1) {
            this.strengthIcons.clear();
            //copy(this.strengthIcons, strengthIcons);
            this.strengthIcons = new ArrayList<>(strengthIcons);
            strengthIcon.setBackground(strengthIcons.get(0));
        } else {
            throw new IllegalArgumentException("Array length must equal maxStrength + 1");
        }
    }

    public void setMaxStrength(int strength) {
        maxStrength = strength;
    }
    public void setToggleVisibilityIcons(Drawable visibleIcon, Drawable hiddenIcon) {
        this.visibleIcon = visibleIcon;
        this.hiddenIcon = hiddenIcon;
        toggleVisibility.setBackground(hiddenIcon);
    }
}
