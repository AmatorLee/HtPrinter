package com.amator.htprinter.base;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public interface BasePresenter<T extends BaseView> {

    public void attachView(T view);

    public void detachView();
}
