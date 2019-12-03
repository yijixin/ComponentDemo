package com.uidt.common_base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

/**
 * @author yijixin on 2019-11-30
 */
public class ToastUtils {

    private static ToastUtils toastUtils = null;
    private Activity activity;

    public static ToastUtils getInstance(Activity activity) {
        if (toastUtils == null) {
            toastUtils = new ToastUtils(activity);
        }
        return toastUtils;
    }

    public ToastUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 位置
     * Gravity.CENTER
     * Gravity.BOTTOM
     *
     * @param info 弹出内容
     */
    @SuppressLint("ShowToast")
    public void toastShow(String info, int position) {
        Toast toast = Toast.makeText(activity, info, Toast.LENGTH_SHORT);
        toast.setGravity(position, 0, 0);
        toast.show();
    }
}
