package com.uidt.module_base_one;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.uidt.common_base.base.BaseActivity;

@Route(path = "/one/MainModuleOneActivity")
public class MainModuleOneActivity extends BaseActivity {

    private Toolbar toolbar;

    @Autowired
    public String name;

    @Autowired
    public int age;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main_module_one;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MainOne");
        setSupportActionBar(toolbar);

        TextView tvMainOne = findViewById(R.id.tv_mainone);
        tvMainOne.setText("name=" + name + ",age=" + age);
    }
}
