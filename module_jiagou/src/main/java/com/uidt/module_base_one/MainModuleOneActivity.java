package com.uidt.module_base_one;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.uidt.common_base.base.BaseActivity;
import com.uidt.common_base.utils.PreferenceUtil;
import com.uidt.module_base_one.annotation.ClickBehavior;
import com.uidt.module_base_one.annotation.LoginCheck;

@Route(path = "/one/MainModuleOneActivity")
public class MainModuleOneActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "JiaGou ==>";

    private Toolbar toolbar;
    private TextView tvTestOne;

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

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnShop = findViewById(R.id.btn_shop);
        Button btnCoupon = findViewById(R.id.btn_coupon);
        Button btnMine = findViewById(R.id.btn_mine);
        tvTestOne = findViewById(R.id.tv_test_one);

        btnLogin.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnCoupon.setOnClickListener(this);
        btnMine.setOnClickListener(this);

        Log.e("====>","=="+Thread.currentThread());

        PreferenceUtil.putBoolean(MainModuleOneActivity.this,"login_result",false);

        thread.start();

//        handler1.post()

//        TextView tvMainOne = findViewById(R.id.tv_mainone);
//        tvMainOne.setText("name=" + name + ",age=" + age);
    }

    @Override
    public void initPresenter() {

    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //sd东方郡送牢饭就是连接阿拉基古灵精怪发了疯就够啦法规及按老规矩奥利给记录数据管理局管理局按老规矩房管局
//            tvTestOne.setText("1223344");
            Log.e("MainModuleOneActivity->",""+Thread.currentThread());
            Message message = new Message();
            message.what = 3;
            message.obj = "1234566";
            handler1.sendMessageDelayed(message,3000);
            //Toast内部使用了handler进行了切换，Looper用于保存当前线程
//            Looper.prepare();
//            Toast.makeText(MainModuleOneActivity.this,"toast",Toast.LENGTH_SHORT).show();
//            Looper.loop();
        }
    });

    private Handler handler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return true;
        }
    });

    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_shop:
                shop();
                break;
            case R.id.btn_coupon:
                coupon();
                break;
            case R.id.btn_mine:
                mine();
                break;
            default:
        }
    }

    @ClickBehavior("我的")
    @LoginCheck
    private void mine() {
        Log.e(TAG,"跳转我的界面");
        OtherActivity.startAction(MainModuleOneActivity.this);
    }

    @ClickBehavior("优惠券")
    @LoginCheck
    private void coupon() {
        Log.e(TAG,"跳转优惠券界面");
        OtherActivity.startAction(MainModuleOneActivity.this);
    }

    @ClickBehavior("购物")
    @LoginCheck
    private void shop() {
        Log.e(TAG,"跳转购物界面");
        OtherActivity.startAction(MainModuleOneActivity.this);
    }

    @ClickBehavior("登录")
    private void login() {
        Log.e(TAG,"模拟接口请求。。。验证通过，登录成功");
        PreferenceUtil.putBoolean(MainModuleOneActivity.this,"login_result",true);
    }
}
