package com.uidt.module_custom_view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.uidt.common_base.base.BaseActivity;
import com.uidt.module_custom_view.custom.ProgressView;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private float lastX;

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
        toolbar.setTitle("MainCustomView");
        setSupportActionBar(toolbar);

        final ProgressView progressView = findViewById(R.id.progress_view);
        progressView.setMaxProgress(100);

        progressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        progressView.setCurrentProgress(progressView.getCurrentProgress() +
                                ((event.getX() > lastX) ? 0.5f : -0.5f));
                        lastX = event.getX();
                        break;
                    default:
                }
                return true;
            }
        });
    }
}
