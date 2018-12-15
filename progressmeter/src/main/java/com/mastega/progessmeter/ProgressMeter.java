package com.mastega.progessmeter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tjelvar Guo on 2018-12-15.
 */

public class ProgressMeter extends CardView {

    private Context ctx;
    private ArrayList<ImageView> stateIVs = new ArrayList<>();
    private ArrayList<TextView> stateTVs = new ArrayList<>();

    public ProgressMeter(Context context) {
        super(context);
        init(context);
    }

    public ProgressMeter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressMeter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.ctx = context;

        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(ctx);
            imageView.setBackground(ctx.getDrawable(R.drawable.awesome));
            stateIVs.add(imageView);

            TextView textView = new TextView(ctx);
            String stateText = "State " + i;
            textView.setText(stateText);
            stateTVs.add(textView);
        }


        LinearLayout container = new LinearLayout(ctx);
        container.setOrientation(LinearLayout.VERTICAL);

        LinearLayout statesLayout = new LinearLayout(ctx);
        statesLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (ImageView imageView : stateIVs) {
            statesLayout.addView(imageView);
        }
        container.addView(statesLayout);

        LinearLayout statesTextLayout = new LinearLayout(ctx);
        statesTextLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (TextView stateText : stateTVs) {
            statesTextLayout.addView(stateText);
        }
        container.addView(statesTextLayout);

    }

    private int dpAsPixels (int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

}
