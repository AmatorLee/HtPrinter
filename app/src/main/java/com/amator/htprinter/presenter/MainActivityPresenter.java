package com.amator.htprinter.presenter;

import com.amator.htprinter.base.BasePresenter;
import com.amator.htprinter.base.BaseView;

/**
 * Created by AmatorLee on 2018/4/5.
 */

public interface MainActivityPresenter<T extends BaseView> extends BasePresenter<T> {

    void loadApp();

}
