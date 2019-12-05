package com.uidt.module_custom_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uidt.common_base.base.BaseActivity;

public class MapActivity extends BaseActivity {

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity,MapActivity.class);
        activity.startActivity(intent);
    }

    private Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.activity_map;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MapView");
        setSupportActionBar(toolbar);


    }
}
