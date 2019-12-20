package com.uidt.common_base.base;

/**
 * @author yijixin on 2019-12-17
 */
public abstract class BaseModel<P extends BasePresenter,CONTRACT> {
    protected P p;

    public BaseModel(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}
