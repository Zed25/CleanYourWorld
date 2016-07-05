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
public class MultilevelRecyclerView extends RecyclerView{

    private boolean enableTouchIntercept = false;

    private MultilevelRecyclerView parentRecyclerView;

    private MultilevelRecyclerView childRecyclerView;

    public MultilevelRecyclerView(Context context) {
        super(context);
    }

    public MultilevelRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultilevelRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if(parentRecyclerView != null) {
            if (!parentRecyclerView.enableTouchIntercept) {
                //System.out.println("onInterruptTouchEvent with parent");
                return super.onInterceptTouchEvent(e);
            }
        }
        if(enableTouchIntercept) {
            //System.out.println("onInterruptTouchEvent");
            return super.onInterceptTouchEvent(e);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (parentRecyclerView != null) {
            if (e.getAction() == MotionEvent.ACTION_MOVE||
                    e.getAction() == MotionEvent.ACTION_UP) {
                // Disable intercepting touch to allow children to scroll
                parentRecyclerView.setEnableTouchIntercept(false);
                System.out.println("false");
            } else if (e.getAction() == MotionEvent.ACTION_CANCEL) {
                // Re-enable after children handles touch
                parentRecyclerView.setEnableTouchIntercept(true);
                System.out.println("true");
            }
        }
        //System.out.println("Touch event");
        return super.onTouchEvent(e);
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

    public void setParentRecyclerView(MultilevelRecyclerView parentRecyclerView) {
        this.parentRecyclerView = parentRecyclerView;
    }

    public MultilevelRecyclerView getChildRecyclerView() {
        return childRecyclerView;
    }

    public void setChildRecyclerView(MultilevelRecyclerView childRecyclerView) {
        this.childRecyclerView = childRecyclerView;
    }
}
