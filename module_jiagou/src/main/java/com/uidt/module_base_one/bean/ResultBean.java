package com.uidt.module_base_one.bean;

import com.uidt.module_base_one.exp.BaseEntity;

/**
 * @author yijixin on 2019-12-17
 */
public class ResultBean extends BaseEntity {
    private int code;
    private boolean isSuccess;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
