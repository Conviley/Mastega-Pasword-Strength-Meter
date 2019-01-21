package com.mastega.progessmeter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tjelvar Guo on 2018-12-15.
 */

public class ProgressMeter extends CardView {

    private Context ctx;
    private List<StateItem> stateItems = new ArrayList<>();

    private LinearLayout container;
    private LinearLayout statesLayout;
    private LinearLayout stateDescsLayout;
    private LinearLayout.LayoutParams stateIVParams;
    private LinearLayout.LayoutParams stateTVParams;

    private OnStateChangeListener stateChangeListener;

    private int currentState = 0;

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

        initLayouts();
        setStates(new String[] {"State 1", "State 2", "State 3"} , ctx.getDrawable(R.drawable.awesome));

        LinearLayout.LayoutParams verticalCenterParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        verticalCenterParams.gravity = Gravity.CENTER_VERTICAL;

        container.addView(statesLayout);
        container.addView(stateDescsLayout);
        container.setPadding(0,10,0,0);
        this.addView(container, verticalCenterParams);


    }

    private void initLayouts(){
        container = new LinearLayout(ctx);
        container.setOrientation(LinearLayout.VERTICAL);

        statesLayout = new LinearLayout(ctx);
        statesLayout.setOrientation(LinearLayout.HORIZONTAL);

        stateDescsLayout = new LinearLayout(ctx);
        stateDescsLayout.setOrientation(LinearLayout.HORIZONTAL);

        stateIVParams = new LinearLayout.LayoutParams(
                0, dpAsPixels(25));
        stateIVParams.weight = 1;

        stateTVParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT);
        stateTVParams.weight = 1;
    }

    private int dpAsPixels (int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    private void draw(){
        statesLayout.removeAllViews();
        stateDescsLayout.removeAllViews();

        for (StateItem stateItem : stateItems) {
            statesLayout.addView(stateItem.getCircleIndicator(), stateIVParams);
            stateDescsLayout.addView(stateItem.getTextView(), stateTVParams);
        }

        invalidate();
    }

    public int getCurrentState() {
        return currentState;
    }

    public void goToState(int state) {
        if (currentState != state - 1) {
            throw new IllegalArgumentException("State: " + (state - 1) + "is not reached yet!");
        }

        for (StateItem stateItem : stateItems) {
            if (stateItem.getStep() == state - 1) {
                stateItem.setCompleted(true);
                stateItem.getCircleIndicator().stop();
            } else if (stateItem.getStep() == state){
                stateItem.getCircleIndicator().indicate();
            }
        }
        currentState++;
        invalidate();
    }

    public void nextState() {
        if(currentState >= stateItems.size()) {
            return;
        }
        currentState++;
        for (StateItem stateItem : stateItems) {
            if (stateItem.getStep() == currentState - 1) {
                stateItem.setCompleted(true);
                stateItem.getCircleIndicator().stop();
            } else if (stateItem.getStep() == currentState) {
                stateItem.getCircleIndicator().indicate();
            }
        }
        stateChangeListener.onStateChange(currentState);
        invalidate();
    }

    public void prevState() {
        if(currentState <= 0) {
            return;
        }
        for (StateItem stateItem : stateItems) {
            if (stateItem.getStep() == currentState) {
                stateItem.getCircleIndicator().stop();
            } else if (stateItem.getStep() == currentState - 1) {
                stateItem.setCompleted(false);
                stateItem.getCircleIndicator().indicate();
            }
        }
        currentState--;
        stateChangeListener.onStateChange(currentState);
        invalidate();
    }

    public void setStates(String[] stateDescs, Drawable stateDrawable){
        stateItems.clear();
        for(int i = 0; i < stateDescs.length; i++) {
            if (i == 0) {
               StateItem stateItem = new StateItem(i, stateDescs[i], stateDrawable.getConstantState().newDrawable().mutate(), ctx);
               stateItem.getCircleIndicator().indicate();
               stateItems.add(stateItem);
            } else {
                stateItems.add(new StateItem(i, stateDescs[i], stateDrawable.getConstantState().newDrawable().mutate(), ctx));
            }

        }

        draw();
    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        stateChangeListener = listener;
    }
}
