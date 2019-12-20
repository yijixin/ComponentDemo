package com.uidt.module_base_one.core_handler;

/**
 * @author yijixin on 2019-12-20
 * 模拟Handler类
 */
public class Handler {

    private MessageQueue messageQueue;
    private Looper mLooper;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        messageQueue = mLooper.mQueue;
    }

    public void sendMessage(Message message) {
        eueueMessage(message);
    }

    private void eueueMessage(Message message) {
        //对Handler赋值
        message.target = this;

        messageQueue.eueueMessage(message);
    }

    public void handlerMessage(Message message) {

    }

    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }
}
