package com.mastega.progessmeter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tjelvar Guo on 2018-12-15.
 */

class StateItem {

    private int step;
    private boolean reached;

    private CircleIndicator circleIndicator;
    private TextView textView;

    public StateItem(int step, String text, Drawable drawable, Context ctx) {
        this.step = step;
        circleIndicator = new CircleIndicator(ctx);
        circleIndicator.setFrontDrawable(drawable);
        setSaturation(0);

        textView = new TextView(ctx);
        textView.setText(text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        reached = false;
    }

    private void setSaturation (int value) {
        ColorMatrix matrix = new ColorMatrix();
        // change saturation to "1" for full color.
        matrix.setSaturation(value);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        circleIndicator.getBack().setColorFilter(filter);
        circleIndicator.getFront().setColorFilter(filter);
    }

    public int getStep() {
        return step;
    }

    public CircleIndicator getCircleIndicator() {
        return circleIndicator;
    }

    public TextView getTextView() {
        return textView;
    }

    public boolean isReached() {
        return reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
        if (reached) {
            setSaturation(1);
        } else {
            setSaturation(0);
        }
    }
}
