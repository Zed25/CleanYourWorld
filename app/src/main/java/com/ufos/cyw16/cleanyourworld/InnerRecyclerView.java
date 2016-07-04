/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.InnerRecyclerView
 * Last modified: 04/07/16 22.57
 */

package com.ufos.cyw16.cleanyourworld;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by simone_mancini on 04/07/16.
 */
public class InnerRecyclerView extends RecyclerView implements View.OnTouchListener {

    private boolean enableTouchIntercept = false;

    private RecyclerView parentRecyclerView;

    public InnerRecyclerView(Context context) {
        super(context);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (enableTouchIntercept) {
            return super.onInterceptTouchEvent(e);
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (parentRecyclerView != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Disable intercepting touch to allow children to scroll
                setEnableTouchIntercept(true);
            } else if (event.getAction() == MotionEvent.ACTION_UP ||
                    event.getAction() == MotionEvent.ACTION_CANCEL) {
                // Re-enable after children handles touch
                setEnableTouchIntercept(false);
            }
        }

        return super.onTouchEvent(event);
    }

    public boolean isEnableTouchIntercept() {
        return enableTouchIntercept;
    }

    public void setEnableTouchIntercept(boolean enableTouchIntercept) {
        this.enableTouchIntercept = enableTouchIntercept;
    }

    public RecyclerView getParentRecyclerView() {
        return parentRecyclerView;
    }

    public void setParentRecyclerView(RecyclerView parentRecyclerView) {
        this.parentRecyclerView = parentRecyclerView;
    }
}
