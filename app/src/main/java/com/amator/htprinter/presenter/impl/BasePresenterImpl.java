package com.amator.htprinter.presenter.impl;

import com.amator.htprinter.presenter.BasePresenter;
import com.amator.htprinter.ui.BaseView;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>{

    private T mView;

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
