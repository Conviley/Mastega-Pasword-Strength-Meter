package com.mastega.progessmeter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Tjelvar Guo on 2018-12-15.
 */

public class CircleIndicator extends RelativeLayout {

    private Context ctx;
    private ImageView back;
    private ImageView front;

    public CircleIndicator(Context context) {
        super(context);
        init(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        ctx = context;
        back = new ImageView(ctx);
        back.setImageDrawable(ctx.getDrawable(R.drawable.awesome));

        front = new ImageView(ctx);
        front.setImageDrawable(ctx.getDrawable(R.drawable.nomouthgray));

        RelativeLayout.LayoutParams centerParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        centerParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        centerParam.setMargins(0,10,0,10);
        this.addView(back,centerParam);
        this.addView(front, centerParam);
    }

    public ImageView getBack() {
        return back;
    }

    public ImageView getFront() {
        return front;
    }

    public void indicate(){
        final Animation enlarge = AnimationUtils.loadAnimation(ctx, R.anim.enlarge);
        back.startAnimation(enlarge);
    }

    public void stop(){
        back.clearAnimation();
    }

    public void setFrontDrawable(Drawable drawable) {
        front.setImageDrawable(drawable);
    }
}
