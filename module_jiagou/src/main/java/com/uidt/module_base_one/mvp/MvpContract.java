package com.uidt.module_base_one.mvp;

import com.uidt.common_base.base.BaseEntity;
import com.uidt.common_base.base.BaseModel;
import com.uidt.common_base.base.BasePresenter;

/**
 * @author yijixin on 2019-12-17
 */
public interface MvpContract {

    interface MvpModel {
        void requestMvpLogin(String name,String pwd);
    }

    interface MvpView<T extends BaseEntity>{
        void loadMvpLoginResult(T t);
    }

    interface MvpPresenter<T extends BaseEntity>{
        abstract void requestByViewMvpLogin(String name,String pwd);

        abstract void reponseByModelMvpLogin(T t);
    }

}
