package com.uidt.module_base_one.core_handler;

/**
 * @author yijixin on 2019-12-20
 * 模拟ActivityThread类，手写handler
 */
public class ActivityThread {

    public static void main(String[] args){

        //初始化
        Looper.prepareMainLooper();

        final Handler handler = new Handler(){
            @Override
            public void handlerMessage(Message message) {
                super.handlerMessage(message);
                System.out.println("message="+message.obj.toString());
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.obj = "123456";
                handler.sendMessage(message);
            }
        }).start();

        //循环获取数据
        Looper.loop();
    }
}
