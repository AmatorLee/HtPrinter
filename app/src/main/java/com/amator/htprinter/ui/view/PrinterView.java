package com.amator.htprinter.ui.view;

import com.amator.htprinter.base.BaseView;
import com.dothantech.printer.IDzPrinter;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public interface PrinterView extends BaseView {

    void initMenu();

    void initPrinter();

    void onPrinterConnecting(IDzPrinter.PrinterAddress printerAddress,boolean showDialog);

    void onPrinterConnected(IDzPrinter.PrinterAddress address);

    void onPrinterDisConnected();

    boolean isPrinterConnected();

    void onPrinStart();

    void onPrintSucceed();

    void onPrintFailed();


    void print(String event);
}
