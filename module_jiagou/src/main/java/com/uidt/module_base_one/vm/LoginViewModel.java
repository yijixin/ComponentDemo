package com.uidt.module_base_one.vm;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.uidt.common_base.app.BaseAplication;
import com.uidt.common_base.utils.PreferenceUtil;
import com.uidt.module_base_one.bean.UserInfo;
import com.uidt.module_base_one.databinding.ActivityMvpDemoBinding;

/**
 * @author yijixin on 2019-12-17
 */
public class LoginViewModel {

    public UserInfo userInfo;

    public LoginViewModel(ActivityMvpDemoBinding activityMvpDemoBinding) {

        userInfo = new UserInfo();
        activityMvpDemoBinding.setLoginViewModel(this);
    }

    public TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //绑定
            userInfo.name.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher pwdTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            userInfo.pwd.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //做更新操作需要用handler在主线程中进行
                    String nameInfo = PreferenceUtil.getString(BaseAplication.getAppContext(),"login_name","");
                    String pwdInfo = PreferenceUtil.getString(BaseAplication.getAppContext(),"login_pwd_"+nameInfo,"");
                    if (TextUtils.isEmpty(nameInfo) && TextUtils.isEmpty(pwdInfo)) {
                        if ("123".equals(userInfo.name.get()) && "123".equals(userInfo.pwd.get())) {
                            PreferenceUtil.putString(BaseAplication.getAppContext(),"login_name",userInfo.name.get());
                            PreferenceUtil.putString(BaseAplication.getAppContext(),"login_pwd_" + userInfo.name.get(),userInfo.pwd.get());
                            Log.e("====>","登录成功");
                        } else {
                            Log.e("====>","登录失败");
                        }
                    } else {
                        if (nameInfo.equals(userInfo.name.get()) && pwdInfo.equals(userInfo.pwd.get())) {
                            PreferenceUtil.putString(BaseAplication.getAppContext(),"login_name",userInfo.name.get());
                            PreferenceUtil.putString(BaseAplication.getAppContext(),"login_pwd_" + userInfo.name.get(),userInfo.pwd.get());
                            Log.e("====>", "登录成功111");
                        } else {
                            Log.e("====>", "登录失败111");
                        }
                    }
                }
            }).start();
        }
    };
}
