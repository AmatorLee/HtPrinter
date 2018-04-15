package com.amator.htprinter.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amator.htprinter.R;
import com.amator.htprinter.adapter.PrinterAdapter;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.presenter.impl.PrinterListActivityPresenterImpl;
import com.amator.htprinter.refreshRecyclerView.RefreshRecyclerView;
import com.amator.htprinter.ui.view.PrinterListView;
import com.amator.htprinter.uitl.DialogUtil;
import com.amator.htprinter.uitl.StatusViewManager;
import com.amator.htprinter.uitl.ViewUtil;
import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AmatorLee on 2018/4/15.
 */

public class PrinterListActivity extends BaseActivity<PrinterListActivityPresenterImpl> implements PrinterListView {

    @Inject
    PrinterListActivityPresenterImpl printerListPresenter;
    @BindView(R.id.toolbar_printer_list)
    Toolbar toolbar_printer_list;
    @BindView(R.id.refresh_printer_list)
    RefreshRecyclerView refresh_printer_list;
    private PrinterAdapter printer_adapter;
    private LPAPI api;
    private List<IDzPrinter.PrinterAddress> pairedPrinters;
    private StatusViewManager status_view_manager;
    private IDzPrinter.PrinterAddress mPrinterAddress = null;

    @Override
    protected void setListener() {
        status_view_manager.setOnRetryClick(new StatusViewManager.onRetryClick() {
            @Override
            public void onRetryLoad() {
                status_view_manager.onLoad();
                initPrinters();
            }
        });
        toolbar_printer_list.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPrinterAddress == null){
                    warnintPrinterIsNull();
                }else {
                    saveSelectedPrinterAddress();
                    finish();
                }
            }
        });
        initPrinters();
    }

    private void saveSelectedPrinterAddress() {
        SharedPreferences.Editor editor = mActivityComponent.getSharePreference().edit();
        editor.putString(Constans.PRINTER_NAME_KEY,mPrinterAddress.shownName);
        editor.putString(Constans.PRINTER_MAC,mPrinterAddress.macAddress);
        editor.putString(Constans.PRINTER_ADDRESS_TYPE,mPrinterAddress.addressType.toString());
        editor.apply();
        setResult(Constans.RESULT_PRINTER_CODE);
    }

    private void warnintPrinterIsNull() {
        DialogUtil.newInstance().dialogWithConfirmAndCancel(this, "还没选择打印机，确定退出?", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initToolbar();
        showRefreshView();
        status_view_manager = StatusViewManager.createView(this, refresh_printer_list);
        api = LPAPI.Factory.createInstance();
        initAdapter();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar_printer_list);
        getSupportActionBar().setTitle("");
    }

    private void initPrinters() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            showToast(ViewUtil.getString(R.string.unsupportedbluetooth));
            status_view_manager.onError();
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            showToast(ViewUtil.getString(R.string.unenablebluetooth));
            status_view_manager.onError();
            return;
        }
        pairedPrinters = api.getAllPrinterAddresses(null);
        printer_adapter.addAll(pairedPrinters);
    }

    private void initAdapter() {
        printer_adapter = new PrinterAdapter(mActivityComponent.getActivity());
        refresh_printer_list.setLayoutManager(new LinearLayoutManager(this));
        refresh_printer_list.setSwipeRefreshColors(ViewUtil.getColor(R.color.colorPrimary), ViewUtil.getColor(R.color.colorPrimaryDark));
        refresh_printer_list.setItemSpace(0, 4, 0, 8);
        refresh_printer_list.setAdapter(printer_adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_printer_list;
    }

    @Override
    public PrinterListActivityPresenterImpl initPresenter() {
        mActivityComponent.inject(this);
        return printerListPresenter;
    }

    @Override
    public void showRefreshView() {
        if (refresh_printer_list != null) {
            refresh_printer_list.showSwipeRefresh();
        }
    }

    @Override
    public void dismissRefreshView() {
        if (refresh_printer_list != null) {
            refresh_printer_list.dismissSwipeRefresh();
        }
    }
}
