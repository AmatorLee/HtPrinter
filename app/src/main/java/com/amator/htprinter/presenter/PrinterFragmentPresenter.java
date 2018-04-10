package com.amator.htprinter.presenter;

import com.amator.htprinter.ui.view.BaseView;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public interface PrinterFragmentPresenter<T extends BaseView> extends BasePresenter<T> {

    void print();

}
