package com.uidt.module_base_one;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.uidt.common_base.app.BaseAplication;
import com.uidt.common_base.base.BaseActivity;
import com.uidt.common_base.utils.PreferenceUtil;
import com.uidt.module_base_one.databinding.ActivityMvpDemoBinding;
import com.uidt.module_base_one.vm.LoginViewModel;

public class MvpDemoActivity extends BaseActivity {

    private Toolbar toolbar;
    private Message message;

    //最好使用的写法
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected int layoutRes() {
        return R.layout.activity_mvp_demo;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MvpDemo");
        setSupportActionBar(toolbar);

        ActivityMvpDemoBinding activityMvpDemoBinding = DataBindingUtil.setContentView(this,R.layout.activity_mvp_demo);

        LoginViewModel loginViewModel = new LoginViewModel(activityMvpDemoBinding);
        String nameInfo = PreferenceUtil.getString(BaseAplication.getAppContext(),"login_name","");
        String pwdInfo = PreferenceUtil.getString(BaseAplication.getAppContext(),"login_pwd_"+nameInfo,"");
        loginViewModel.userInfo.name.set(nameInfo);
        loginViewModel.userInfo.pwd.set(pwdInfo);

        message = new Message();
        message.what = 3;
        message.obj = "123";
        handler.sendMessageDelayed(message,100);

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeMessages(3);

    }
}
