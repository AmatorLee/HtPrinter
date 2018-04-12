package com.amator.htprinter.presenter;

import com.amator.htprinter.base.BasePresenter;
import com.amator.htprinter.base.BaseView;

/**
 * Created by AmatorLee on 2018/4/12.
 */

public interface ContentActivityPresenter<T extends BaseView> extends BasePresenter<T> {

    void printMessage();

    void printQRCode();

}
