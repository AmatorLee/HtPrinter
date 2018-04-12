package com.amator.htprinter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.di.component.ActivityComponent;
import com.amator.htprinter.di.component.DaggerActivityComponent;
import com.amator.htprinter.di.module.ActivityModule;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.uitl.RxActivityTool;;

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
        setContentView(getLayoutId());
        RxActivityTool.addActivity(this);
        mUnbinder = ButterKnife.bind(this);
        initActivityComponent();
        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initView(savedInstanceState);
        setListener();
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(HtPrinterApplcation.getsApplicationComponent())
                .build();

    }

    protected abstract void setListener();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    public abstract T initPresenter();

    @Override
    public void showToast(String message) {
        Toast.makeText(mActivityComponent.getActivityContext(), message, Toast.LENGTH_SHORT).show();
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
