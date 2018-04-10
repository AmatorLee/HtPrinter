package com.amator.htprinter.uitl;

/**
 * Created by AmatorLee on 2017/7/12.
 */

public abstract class StatusInterface {

    protected abstract void initView();


    protected abstract void initContainer();


    protected abstract void onLoad();


    protected abstract void onSuccess();


    protected abstract void onNoNet();


    protected abstract void onError();


    protected abstract void onEmpty();

    protected abstract void onDestory();

}
