package com.uidt.common_base.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author yijixin on 2019-10-22
 * 沉浸式状态栏
 */
public class ImmersiveUtils {

    /**
     * 设置沉浸式布局
     */
    public static void setImmersive(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //View.SYSTEM_UI_FLAG_FULLSCREEN Activity全屏显示，且状态栏被隐藏覆盖掉
            //View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏虚拟按键
            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住,需要进行适配放着被遮挡
//            int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            //追加沉浸式
            visibility |= flags;
            window.getDecorView().setSystemUiVisibility(visibility);

            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
