package com.ufos.cyw16.cleanyourworld;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by simone_mancini on 29/06/16.
 *
 * This class will be used to define how the floating action button should respond to other views contained within the same CoordinatorLayout.
 *
 * It has to be associate with a Floating Action Button (FAB).
 *
 * It can be define within the XML declatation as a custom attribut app:layout_behaviour:"com.codepath.floatingactionbuttontest.ScrollAwareFABBehavior"
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {


    //this kind of constructor is necessary to implement a static definition of this behavior class
    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        //it detecs the scrolling direction and compare it with fab's status
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }
}
