package com.amator.htprinter.ui.activity;

import android.os.Handler;
import android.util.Log;

import com.amator.htprinter.R;
import com.amator.htprinter.presenter.impl.MainActivityPresenterImpl;
import com.amator.htprinter.ui.MainView;

import javax.inject.Inject;

import static com.alipay.sdk.app.statistic.c.B;

public class MainActivity extends BaseActivity<MainActivityPresenterImpl> implements MainView{

    @Inject
    public MainActivityPresenterImpl mMainActivityPresenter;

    @Override
    protected void setListener() {
    }

    @Override
    protected void initView() {
        mMainActivityPresenter.loadApp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityPresenterImpl initPresenter() {
        mActivityComponent.inject(this);
        return mMainActivityPresenter;
    }


}
