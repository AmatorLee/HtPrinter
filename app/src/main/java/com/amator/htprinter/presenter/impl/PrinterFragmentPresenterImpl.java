package com.amator.htprinter.presenter.impl;

import com.amator.htprinter.presenter.PrinterFragmentPresenter;
import com.amator.htprinter.ui.view.PrinterView;
import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;

import javax.inject.Inject;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class PrinterFragmentPresenterImpl extends BasePresenterImpl<PrinterView> implements PrinterFragmentPresenter<PrinterView>{

    @Inject
    public PrinterFragmentPresenterImpl(){

    }

    @Override
    public void print() {

    }
}
