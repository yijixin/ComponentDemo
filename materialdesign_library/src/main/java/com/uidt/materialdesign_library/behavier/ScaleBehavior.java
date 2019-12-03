package com.uidt.materialdesign_library.behavier;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

/**
 * @author yijixin on 2019-12-02
 */
public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private Interpolator interpolator;
    private boolean isRuning;

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        interpolator = new AccelerateDecelerateInterpolator();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只有返回true，后续动作才会触发
        //垂直滑动  axes为坐标轴
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        if (dyConsumed > 0 && !isRuning && child.getVisibility() == View.VISIBLE) {
            //上滑 缩小隐藏 动画
            scaleHide(child);
        } else if (dyConsumed < 0 && !isRuning && child.getVisibility() == View.INVISIBLE){
            scaleShow(child);
        }
    }

    private void scaleShow(final V child) {
        child.setVisibility(View.VISIBLE);
        ViewCompat.animate(child).alpha(1).scaleX(1).scaleY(1).setInterpolator(interpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isRuning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isRuning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isRuning = false;
                    }
                }).setDuration(500)
                .start();
    }

    private void scaleHide(final V child) {
        ViewCompat.animate(child).alpha(0).scaleX(0).scaleY(0).setInterpolator(interpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isRuning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isRuning = false;
                        child.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isRuning = false;
                    }
                }).setDuration(500)
                .start();
    }
}
