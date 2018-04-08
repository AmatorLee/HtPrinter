package com.amator.htprinter.presenter;

import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.ui.BaseView;

/**
 * Created by AmatorLee on 2018/4/5.
 */

public interface MainActivityPresenter<T extends BaseView> extends BasePresenter<T> {

    void loadApp();

}
