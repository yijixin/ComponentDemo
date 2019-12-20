package com.uidt.common_base.base;

import java.lang.ref.WeakReference;

/**
 * @author yijixin on 2019-12-17
 * Presenter基类
 */
public abstract class BasePresenter<V extends BaseActivity, M extends BaseModel, CONTRACT> {

    private M m;
    //绑定View层的弱引用
    private WeakReference<V> weakReference;

    public BasePresenter() {
       m = getModel();
    }

    public void bindView(V v) {
        weakReference = new WeakReference<>(v);
    }

    public void unbindView() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
            System.gc();
        }
    }

    public V getView(){
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public abstract CONTRACT getContract();

    public abstract M getModel();
}
