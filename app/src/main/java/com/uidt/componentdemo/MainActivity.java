package com.uidt.componentdemo;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.uidt.common_base.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Main");
        setSupportActionBar(toolbar);

        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/one/MainModuleOneActivity")
                        .withString("name","MainActivity")
                        .withInt("age",19)
                        .navigation();
            }
        });
    }

    @Override
    public void initPresenter() {

    }
}
