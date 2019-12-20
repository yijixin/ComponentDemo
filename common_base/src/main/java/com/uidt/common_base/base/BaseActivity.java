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
public abstract class BaseActivity<P extends BasePresenter,CONTRACT> extends AppCompatActivity {

    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        ARouter.getInstance().inject(this);
        //沉浸式
        ImmersiveUtils.setImmersive(this);

        //弱引用
//        p = getPresenter();
        //绑定
//        p.bindView(this);
        initViews(savedInstanceState);

        //刘海屏等适配
        Utils.setBelowBangs(getTopView(),this);
    }

    protected abstract int layoutRes();

    protected abstract View getTopView();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initPresenter();

//    public abstract CONTRACT getContract();
//
//    public abstract P getPresenter();
//
//    //如果Presenter出现异常，须告知View层
//    public void error(Exception e) {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //解除绑定
//        p.unbindView();
//    }
}
