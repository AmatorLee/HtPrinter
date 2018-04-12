package com.amator.htprinter.module;

import com.itheima.retrofitutils.listener.HttpResponseListener;

import java.io.Serializable;

/**
 * Created by AmatorLee on 2018/4/11.
 */

public class BaseModule<T> implements Serializable{

    private static final long serialVersionUID = BaseModule.class.hashCode();

    private T data;
    private int errorCode;
    private String errorMsg;

    public boolean isSucceed(){
        return getErrorCode() == 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
