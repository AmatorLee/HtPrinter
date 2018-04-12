package com.amator.htprinter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.di.component.DaggerFragmentComponent;
import com.amator.htprinter.di.component.FragmentComponent;
import com.amator.htprinter.di.module.FragmentModule;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by AmatorLee on 2018/4/6.
 */

public abstract class BaseFragment<T extends BasePresenterImpl> extends Fragment implements BaseView{

    private View mInflater;
    private Unbinder mUnbinder;
    private T mPresenter;
    protected FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        mPresenter = injectPresenter();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    protected abstract T injectPresenter();

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(HtPrinterApplcation.getsApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mInflater == null) {
            mInflater = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, mInflater);
            initView();
            setListener();
        }
        ViewGroup parent = (ViewGroup) mInflater.getParent();
        if (parent != null) {
            parent.removeView(mInflater);
        }
        return mInflater;
    }

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract int getLayoutId();

    @Override
    public void showToast(String message) {
        Toast.makeText(mFragmentComponent.getActivityContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null){
            mPresenter.detachView();
        }
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
