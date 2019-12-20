package com.uidt.module_base_one.core_handler;

/**
 * @author yijixin on 2019-12-20
 * 模拟的Looper类
 */
public class Looper {

    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
    public MessageQueue mQueue;

    public Looper() {
        mQueue = new MessageQueue();
    }

    public static void prepareMainLooper() {
        prepare();
    }

    public static void prepare() {
        sThreadLocal.set(new Looper());
    }

    public static void loop() {
        Looper looper = myLooper();
        if (looper == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        MessageQueue messageQueue = looper.mQueue;

        while (true) {
            Message msg = messageQueue.next();
            if (msg == null) {
                return;
            }

            msg.target.dispatchMessage(msg);
        }
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }
}
