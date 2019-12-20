package com.uidt.common_base.utils;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

/**
 * @author yijixin on 2019-10-22
 */
public class Utils {

    /**
     * 判断是否是全屏
     */
    public static boolean hasFullScreen(Activity activity) {
        //看flags从右向左数第 11 位是 0 还是 1，只要与 FLAG_FULLSCREEN 做个逻辑与就行了，除了第 11 位，其它位都变成了 0
        if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true;
        }
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();

        if ((systemUiVisibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.SYSTEM_UI_FLAG_FULLSCREEN) {
            return true;
        }
        if ((systemUiVisibility & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) {
            return true;
        }

        TypedValue typedValue = new TypedValue();
        activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowFullscreen}).getValue(0, typedValue);
        if (typedValue.type == TypedValue.TYPE_INT_BOOLEAN) {
            if (typedValue.data != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Android9.0以上的手机是否有刘海屏
     */
    public static boolean hasDisplayCutout(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View decorView = window.getDecorView();
            WindowInsets insets = decorView.getRootWindowInsets();
            if (insets != null) {
                DisplayCutout displayCutout = insets.getDisplayCutout();
                //displayCutout.getBoundingRects() 有几个刘海屏
                //displayCutout.getSafeInsetTop() 刘海屏距顶部的距离
                if (displayCutout != null && displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0) {
                    return true;
                }
            }

            return false;
        }
        //非Android 9.0的手机不适合
        return false;
    }

    /**
     * 设置布局在刘海屏下方 即控件避开刘海屏区域。否则会被刘海屏遮挡
     */
    public static void setBelowBangs(View view, Activity activity) {
        if (view == null) {
            throw new RuntimeException("view not is null!");
        } else {
            if (Utils.hasFullScreen(activity)) {
                view.setPadding(view.getPaddingLeft(), getHeightForDisplayCutout(activity), view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getHeightForDisplayCutout(Activity activity) {
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return activity.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }
}
