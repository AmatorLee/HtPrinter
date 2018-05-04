package com.amator.htprinter.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.dialog.ActionSheetDialog;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.presenter.impl.PrinterFragmentPresenterImpl;
import com.amator.htprinter.ui.activity.PrinterListActivity;
import com.amator.htprinter.ui.view.PrinterView;
import com.amator.htprinter.uitl.DialogUtil;
import com.amator.htprinter.uitl.PrinterInteractor;
import com.amator.htprinter.uitl.PrinterOptions;
import com.amator.htprinter.uitl.PrinterType;
import com.amator.htprinter.uitl.RxLogTool;
import com.amator.htprinter.uitl.ViewUtil;
import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;

import static com.amator.htprinter.base.Constans.REQUST_PRINTER_CODE;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class PrinterFragment extends BaseFragment<PrinterFragmentPresenterImpl> implements PrinterView, ActionSheetDialog.OnSheetItemClickListener {

    @BindView(R.id.toolbar_printer)
    Toolbar toolbar_printer;
    @BindView(R.id.layout_printer_not_connect)
    View layout_not_connect;
    @BindView(R.id.ripple_print_container)
    RippleBackground ripple_printing;
    @BindView(R.id.img_printing)
    ImageView img_printing;
    @BindView(R.id.btn_printer_menu)
    ImageButton btn_printer_menu;
    @Inject
    public PrinterFragmentPresenterImpl mPrinterFragmentPresenter;
    private Button btn_connect_printer;

    private LPAPI printer_api;
    private IDzPrinter.PrinterAddress printer_address;
    private int retry = 0;
    private LPAPI.Callback printer_callback = new LPAPI.Callback() {
        @Override
        public void onProgressInfo(IDzPrinter.ProgressInfo info, Object o) {
            RxLogTool.d("printer", "info: " + info);
        }

        @Override
        public void onStateChange(IDzPrinter.PrinterAddress address, IDzPrinter.PrinterState state) {
            final IDzPrinter.PrinterAddress printerAddress = address;
            RxLogTool.d("printer", "state: " + state);
            switch (state) {
                case Connected:
                case Connected2:
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    ViewUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onPrinterConnected(printerAddress);
                        }
                    });
                    break;
                case Disconnected:
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    ViewUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onPrinterDisConnected();
                        }
                    });
                    break;
            }
        }

        @Override
        public void onPrintProgress(IDzPrinter.PrinterAddress address, Object o, IDzPrinter.PrintProgress progress, Object o1) {
            switch (progress) {
                case Success:
                    ViewUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onPrintSucceed();
                        }
                    });
                    break;
                case Failed:
                    ViewUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onPrintFailed();
                        }
                    });
                    break;
            }
        }

        @Override
        public void onPrinterDiscovery(IDzPrinter.PrinterAddress address, IDzPrinter.PrinterInfo info) {

        }
    };

    private int qualit_res;
    private int density_res;
    private int gap_type_res;
    private int speed_res;

    @Override
    public void initMenu() {
        new ActionSheetDialog(mFragmentComponent.getActivityContext())
                .builder()
                .setCanceledOnTouchOutside(true)
                .setCancelable(true)
                .addSheetItem(ViewUtil.getString(R.string.setprintquality), ActionSheetDialog.SheetItemColor.Blue, this)
                .addSheetItem(ViewUtil.getString(R.string.setprintdensity), ActionSheetDialog.SheetItemColor.Blue, this)
                .addSheetItem(ViewUtil.getString(R.string.setprintspeed), ActionSheetDialog.SheetItemColor.Blue, this)
                .addSheetItem(ViewUtil.getString(R.string.setgaptype), ActionSheetDialog.SheetItemColor.Blue, this)
                .show();
    }

    @Override
    public void initPrinter() {
        if (isPrinterConnected()) {
            return;
        }
        SharedPreferences sharedPreferences = mFragmentComponent.getSharePreference();
        String lastPrinterMac = sharedPreferences.getString(Constans.PRINTER_MAC, null);
        String lastPrinterName = sharedPreferences.getString(Constans.PRINTER_NAME_KEY, null);
        String lastPrinterType = sharedPreferences.getString(Constans.PRINTER_ADDRESS_TYPE, null);
        IDzPrinter.AddressType lastAddressType = TextUtils.isEmpty(lastPrinterType) ? null : Enum.valueOf(IDzPrinter.AddressType.class, lastPrinterType);
        if (lastPrinterMac == null || lastPrinterName == null || lastAddressType == null) {
            printer_address = null;
        } else {
            printer_address = new IDzPrinter.PrinterAddress(lastPrinterName, lastPrinterMac, lastAddressType);
        }
        if (printer_address != null) {
            if (printer_api.openPrinterByAddress(printer_address)) {
                onPrinterConnecting(printer_address, true);
                return;
            }
        }
        onPrinterDisConnected();
    }

    @Override
    public void onPrinterConnecting(IDzPrinter.PrinterAddress printerAddress, boolean showDialog) {
        if (printerAddress != null && printer_address.isValid()) {
            if (showDialog) {
                DialogUtil.newInstance().normal(mFragmentComponent.getActivityContext(),
                        ViewUtil.getString(R.string.nowisconnectingprinter) + printerAddress.shownName);
            }
        }
    }

    @Override
    public void onPrinterConnected(IDzPrinter.PrinterAddress address) {
        printer_address = address;
        DialogUtil.newInstance().dismissDialog();
        layout_not_connect.setVisibility(View.GONE);
        ripple_printing.setVisibility(View.VISIBLE);
        showToast(getString(R.string.connectprintersuccess));
        if (!TextUtils.isEmpty(event)) {
            handlePrintEvent(event);
            this.event = null;
        }
        if (mVoiceBean != null) {
            handlePrintVoice(mVoiceBean, type);
            mVoiceBean = null;
        }
    }

    private void handlePrintVoice(VoiceBean bean, PrinterType type) {
        switch (type) {
            case ONE:
                if (PrinterInteractor.print1dCode(bean.getMessage(), printer_api, printWithBundle(1, 90))) {
                    onPrinStart();
                } else {
                    onPrintFailed();
                }
                break;
            case TWO:
                if (PrinterInteractor.print2DCode(bean.getMessage(), printer_api, printWithBundle(1, 0))) {
                    onPrinStart();
                } else {
                    onPrintFailed();
                }
                break;
            case TEXT:
                if (PrinterInteractor.printText(bean.getMessage(), printer_api, printWithBundle(1, 0))) {
                    onPrinStart();
                } else {
                    onPrintFailed();
                }
                break;
        }
    }

    @Override
    public void onPrinterDisConnected() {
        DialogUtil.newInstance().dismissDialog();
        ripple_printing.setVisibility(View.GONE);
        layout_not_connect.setVisibility(View.VISIBLE);
        showToast(getString(R.string.connectprinterfailed));
    }

    @Override
    public boolean isPrinterConnected() {
        IDzPrinter.PrinterState state = printer_api.getPrinterState();

        // 打印机未连接
        if (state == null || state.equals(IDzPrinter.PrinterState.Disconnected)) {
            showToast(getString(R.string.pleaseconnectprinter));
            return false;
        }

        // 打印机正在连接
        if (state.equals(IDzPrinter.PrinterState.Connecting)) {
            showToast(getString(R.string.waitconnectingprinter));
            return false;
        }

        // 打印机已连接
        return true;
    }

    @Override
    public void onPrinStart() {
        showToast(ViewUtil.getString(R.string.nowisprinting));
        startAnimation();
    }

    @Override
    public void onPrintSucceed() {
        stopAnimation();
        showToast(ViewUtil.getString(R.string.printsuccess));
    }

    @Override
    public void onPrintFailed() {
        stopAnimation();
        showToast(ViewUtil.getString(R.string.printfailed));
    }

    @Override
    public void handlePrintEvent(String event) {
        try {
            JSONObject jsonObject = new JSONObject(event);
            String type = jsonObject.getString("type");
            PrinterType printerType = Enum.valueOf(PrinterType.class, type);
            String url = jsonObject.getString("url");
            StringBuilder content = new StringBuilder();
            if (!jsonObject.isNull("title")) {
                String title = jsonObject.getString("title");
                content.append("Title:");
                content.append(title);
            }
            content.append("\n");
            content.append("Url:");
            content.append(url);
            switch (printerType) {
                case ONE:
                    if (PrinterInteractor.print1dCode(content.toString(), printer_api, printWithBundle(1, 90))) {
                        onPrinStart();
                    } else {
                        onPrintFailed();
                    }
                    break;
                case TWO:
                    if (PrinterInteractor.print2DCode(content.toString(), printer_api, printWithBundle(1, 0))) {
                        onPrinStart();
                    } else {
                        onPrintFailed();
                    }
                    break;
                case TEXT:
                    if (PrinterInteractor.printText(content.toString(), printer_api, printWithBundle(1, 0))) {
                        onPrinStart();
                    } else {
                        onPrintFailed();
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String event;

    @Override
    public void print(String event) {
        if (!isPrinterConnected()) {
            this.event = event;
            return;
        }
        handlePrintEvent(event);
    }

    @Override
    protected PrinterFragmentPresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return mPrinterFragmentPresenter;
    }

    @Override
    protected void initView() {
        btn_connect_printer = ViewUtil.bindView(layout_not_connect, R.id.btn_select_printer);
        printer_api = LPAPI.Factory.createInstance(printer_callback);
        SharedPreferences preference = mFragmentComponent.getSharePreference();
        qualit_res = preference.getInt(Constans.QUALITY_KEY, -1);
        density_res = preference.getInt(Constans.DENSITY_KEY, -1);
        gap_type_res = preference.getInt(Constans.GAP_TYPE_KEY, -1);
        speed_res = preference.getInt(Constans.SPEED_KEY, -1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initPrinter();
        }
    }

    @Override
    protected void setListener() {
        btn_connect_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentComponent.getActivity(), PrinterListActivity.class);
                startActivityForResult(intent, REQUST_PRINTER_CODE);
            }
        });
        btn_printer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPrinterConnected()) {
                    showToast(ViewUtil.getString(R.string.pleaseconnectprinter));
                    return;
                }
                initMenu();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_printer;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUST_PRINTER_CODE) {
            if (resultCode == Constans.RESULT_PRINTER_CODE) {
                initPrinter();
                startTimer();
            }
        }
    }

    private Timer timer = null;

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                retry++;
                if (retry >= 5) {
                    retry = 0;
                    timer.cancel();
                    timer = null;
                    DialogUtil.newInstance().dismissDialog();
                }
            }
        }, 1000, 1000);
    }

    public Animation createAnimation() {
        Animation rorateAnim = AnimationUtils.loadAnimation(mFragmentComponent.getActivityContext(),
                R.anim.printing);
        LinearInterpolator lin = new LinearInterpolator();
        rorateAnim.setInterpolator(lin);
        return rorateAnim;
    }


    public Bundle printWithBundle(int copies, int orientation) {
        Bundle param = new Bundle();
        // 打印浓度
        if (density_res >= 0) {
            param.putInt(IDzPrinter.PrintParamName.PRINT_DENSITY, density_res);
        }

        // 打印速度
        if (speed_res >= 0) {
            param.putInt(IDzPrinter.PrintParamName.PRINT_SPEED, speed_res);
        }

        // 间隔类型
        if (gap_type_res >= 0) {
            param.putInt(IDzPrinter.PrintParamName.GAP_TYPE, gap_type_res);
        }

        // 打印页面旋转角度
        if (orientation != 0) {
            param.putInt(IDzPrinter.PrintParamName.PRINT_DIRECTION, orientation);
        }

        // 打印份数
        if (copies > 1) {
            param.putInt(IDzPrinter.PrintParamName.PRINT_COPIES, copies);
        }
        return param;
    }


    public void stopAnimation() {
        if (ripple_printing != null && ripple_printing.isRippleAnimationRunning()) {
            ripple_printing.stopRippleAnimation();
        }
        if (img_printing != null) {
            img_printing.clearAnimation();
        }
    }

    public void startAnimation() {
        if (ripple_printing != null
                && ripple_printing.getVisibility() == View.VISIBLE &&
                !ripple_printing.isRippleAnimationRunning()) {
            ripple_printing.startRippleAnimation();
        }
        if (img_printing.getVisibility() == View.VISIBLE) {
            img_printing.setAnimation(createAnimation());
        }
    }


    @Override
    public void onDestroy() {
        if (printer_api != null) {
            printer_api.quit();
        }
        fini();
        super.onDestroy();
    }

    private void fini() {
        SharedPreferences sharedPreferences = mFragmentComponent.getSharePreference();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Constans.QUALITY_KEY, qualit_res);
        editor.putInt(Constans.DENSITY_KEY, density_res);
        editor.putInt(Constans.SPEED_KEY, speed_res);
        editor.putInt(Constans.GAP_TYPE_KEY, gap_type_res);
        if (printer_address != null) {
            editor.putString(Constans.PRINTER_MAC, printer_address.macAddress);
            editor.putString(Constans.PRINTER_NAME_KEY, printer_address.shownName);
            editor.putString(Constans.PRINTER_ADDRESS_TYPE, printer_address.addressType.toString());
        }
        editor.apply();
    }

    @Override
    public void onClick(int which) {
        switch (which) {
            case 1:
                qualit_res = PrinterOptions.getPrintQuality(mFragmentComponent.getActivityContext(), printer_address);
                break;
            case 2:
                density_res = PrinterOptions.getDensity(mFragmentComponent.getActivityContext(), printer_address);
                break;
            case 3:
                speed_res = PrinterOptions.getSpeed(mFragmentComponent.getActivityContext(), printer_address);
                break;
            case 4:
                gap_type_res = PrinterOptions.getPrintType(mFragmentComponent.getActivityContext(), printer_address);
                break;
        }
    }

    private VoiceBean mVoiceBean;
    private PrinterType type;

    public void printVoiceBean(VoiceBean event, PrinterType type) {
        if (!isPrinterConnected()) {
            this.mVoiceBean = event;
            this.type = type;
            return;
        }
        handlePrintVoice(event, type);
    }
}
