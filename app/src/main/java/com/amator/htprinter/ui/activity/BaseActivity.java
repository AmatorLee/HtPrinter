package com.amator.htprinter.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.di.component.ActivityComponent;
import com.amator.htprinter.di.component.DaggerActivityComponent;
import com.amator.htprinter.di.module.ActivityModule;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.ui.BaseView;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxDeviceTool;
import com.vondear.rxtools.view.RxToast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public abstract class BaseActivity<T extends BasePresenterImpl> extends AppCompatActivity implements BaseView{

    private T mPresenter;
    private Unbinder mUnbinder;
    protected ActivityComponent mActivityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxDeviceTool.setPortrait(this);
        setContentView(getLayoutId());
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        RxActivityTool.addActivity(this);
        mUnbinder = ButterKnife.bind(this);
        initActivityComponent();
        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initView();
        setListener();
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(HtPrinterApplcation.getsApplicationComponent())
                .build();

    }

    protected abstract void setListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public abstract T initPresenter();

    @Override
    public void showToast(String message) {
        RxToast.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        if (mPresenter != null){
            mPresenter.detachView();
        }
        RxActivityTool.finishActivity();
    }
}
