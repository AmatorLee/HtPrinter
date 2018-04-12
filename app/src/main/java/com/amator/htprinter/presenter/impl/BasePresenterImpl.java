package com.amator.htprinter.presenter.impl;

import com.amator.htprinter.base.BasePresenter;
import com.amator.htprinter.base.BaseView;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>{

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null){
            mView = null;
        }
    }
}
