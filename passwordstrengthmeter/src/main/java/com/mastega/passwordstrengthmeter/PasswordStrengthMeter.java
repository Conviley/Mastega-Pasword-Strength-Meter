package com.mastega.passwordstrengthmeter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public class PasswordStrengthMeter extends LinearLayout{

    private CardView container;

    private TextView errorTextView;

    private EditText inputField;

    private ImageView[] strengthIcon;

    private ImageView toggleVisibility;

    private String placeholderText;

    private Context ctx;

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

        container = new CardView(context);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );

        container.setCardBackgroundColor(Color.RED);
        inputField = new EditText(ctx);
        container.addView(inputField);
        this.addView(container, containerParams);
    }
}
