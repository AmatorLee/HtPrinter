package com.amator.htprinter.presenter;

import com.amator.htprinter.ui.view.BaseView;

/**
 * Created by AmatorLee on 2018/4/9.
 */

public interface BoxFragmentPresenter<T extends BaseView> extends BasePresenter<T> {

    void loadBox();

    void addToBox();

}
