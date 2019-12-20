package com.uidt.module_base_one;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uidt.common_base.base.BaseActivity;

public class OtherActivity extends BaseActivity {

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity,OtherActivity.class);
        activity.startActivity(intent);
    }

    private Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.activity_other;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Other");
        setSupportActionBar(toolbar);
    }

    @Override
    public void initPresenter() {

    }
}
