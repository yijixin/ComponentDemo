package com.uidt.common_base.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.uidt.common_base.utils.ImmersiveUtils;
import com.uidt.common_base.utils.Utils;

/**
 * @author yijixin on 2019-11-29
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        ARouter.getInstance().inject(this);
        //沉浸式
        ImmersiveUtils.setImmersive(this);

        initViews(savedInstanceState);

        //刘海屏等适配
        Utils.setBelowBangs(getTopView(),this);
    }

    protected abstract int layoutRes();

    protected abstract View getTopView();

    public abstract void initViews(Bundle savedInstanceState);
}
