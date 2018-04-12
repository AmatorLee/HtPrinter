package com.amator.htprinter.ui.fragment;

import android.support.v7.widget.Toolbar;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.presenter.impl.PrinterFragmentPresenterImpl;
import com.amator.htprinter.ui.view.PrinterView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class PrinterFragment extends BaseFragment<PrinterFragmentPresenterImpl> implements PrinterView {

    @BindView(R.id.toolbar_printer)
    Toolbar toolbar_printer;
    @Inject
    public PrinterFragmentPresenterImpl mPrinterFragmentPresenter;


    @Override
    protected PrinterFragmentPresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return mPrinterFragmentPresenter;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_printer;
    }
}
