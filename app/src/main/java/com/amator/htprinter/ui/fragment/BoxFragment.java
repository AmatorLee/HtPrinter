package com.amator.htprinter.ui.fragment;

import android.support.v7.widget.Toolbar;

import com.amator.htprinter.R;
import com.amator.htprinter.presenter.impl.BoxFragmentPresenterImpl;
import com.amator.htprinter.ui.view.BoxView;

import javax.inject.Inject;

import butterknife.BindView;
import cn.lemon.view.RefreshRecyclerView;

/**
 * Created by AmatorLee on 2018/4/9.
 */

public class BoxFragment  extends BaseFragment<BoxFragmentPresenterImpl> implements BoxView{

    @BindView(R.id.toolbar_box)
    Toolbar toolbar_box;
    @BindView(R.id.recycler_refresh)
    RefreshRecyclerView recycler_refresh;
    @Inject
    public BoxFragmentPresenterImpl mBoxFragmentPresenter;

    @Override
    public void loadBoxSucceed() {

    }

    @Override
    public void loadBoxFailed() {

    }

    @Override
    protected BoxFragmentPresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return mBoxFragmentPresenter;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_box;
    }
}
