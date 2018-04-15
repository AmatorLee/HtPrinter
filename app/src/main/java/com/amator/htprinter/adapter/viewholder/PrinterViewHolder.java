package com.amator.htprinter.adapter.viewholder;

import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.R;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.ui.activity.PrinterListActivity;
import com.amator.htprinter.uitl.TextUtils;
import com.dothantech.printer.IDzPrinter;

/**
 * Created by AmatorLee on 2018/4/14.
 */

public class PrinterViewHolder extends BaseViewHolder<IDzPrinter.PrinterAddress>{

    private TextView name;
    private TextView mac;
    private PrinterListActivity mActivity;

    public PrinterViewHolder(PrinterListActivity activity,ViewGroup parent) {
        super(parent, R.layout.layout_printer_address);
        mActivity = activity;
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        name = findViewById(R.id.txt_printer_show_name);
        mac = findViewById(R.id.txt_printer_mac);
    }

    @Override
    public void setData(IDzPrinter.PrinterAddress data) {
        if (!android.text.TextUtils.isEmpty(data.shownName)){
            TextUtils.setText(name,"名称: ",data.shownName);
        }
        if (!android.text.TextUtils.isEmpty(data.macAddress)){
            TextUtils.setText(mac,"Mac地址: ",data.macAddress);
        }
    }

    @Override
    public void onItemViewClick(IDzPrinter.PrinterAddress data) {
        SharedPreferences.Editor editor = HtPrinterApplcation.getsApplicationComponent().getSharePreference().edit();
        editor.putString(Constans.PRINTER_NAME_KEY,data.shownName);
        editor.putString(Constans.PRINTER_MAC,data.macAddress);
        editor.putString(Constans.PRINTER_ADDRESS_TYPE,data.addressType.toString());
        editor.apply();
        mActivity.setResult(Constans.RESULT_PRINTER_CODE);
        mActivity.finish();
    }



}
