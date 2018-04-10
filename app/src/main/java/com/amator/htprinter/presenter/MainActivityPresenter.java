package com.amator.htprinter.presenter;

import com.amator.htprinter.ui.view.BaseView;

/**
 * Created by AmatorLee on 2018/4/5.
 */

public interface MainActivityPresenter<T extends BaseView> extends BasePresenter<T> {

    void loadApp();

}
