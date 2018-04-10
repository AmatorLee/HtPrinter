package com.amator.htprinter.presenter.impl;

import android.util.Log;

import com.amator.htprinter.presenter.MainActivityPresenter;
import com.amator.htprinter.ui.view.MainView;

import javax.inject.Inject;

/**
 * Created by AmatorLee on 2018/4/5.
 */

public class MainActivityPresenterImpl extends BasePresenterImpl<MainView> implements MainActivityPresenter<MainView> {

    @Inject
    public MainActivityPresenterImpl(){

    }

    @Override
    public void loadApp() {
        Log.i("dagger", "loadApp: ");
    }
}
