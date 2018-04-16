package com.amator.htprinter.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.presenter.impl.PrinterFragmentPresenterImpl;
import com.amator.htprinter.ui.activity.PrinterListActivity;
import com.amator.htprinter.ui.view.PrinterView;
import com.amator.htprinter.uitl.DialogUtil;
import com.amator.htprinter.uitl.PrinterInteractor;
import com.amator.htprinter.uitl.PrinterOptions;
import com.amator.htprinter.uitl.PrinterType;
import com.amator.htprinter.uitl.ViewUtil;
import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.skyfishjy.library.RippleBackground;

import javax.inject.Inject;

import butterknife.BindView;

import static com.amator.htprinter.base.Constans.REQUST_PRINTER_CODE;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class PrinterFragment extends BaseFragment<PrinterFragmentPresenterImpl> implements PrinterView {

    @BindView(R.id.toolbar_printer)
    Toolbar toolbar_printer;
    @BindView(R.id.layout_printer_not_connect)
    View layout_not_connect;
    @BindView(R.id.ripple_print_container)
    RippleBackground ripple_printing;
    @BindView(R.id.img_printing)
    ImageView img_printing;
    @Inject
    public PrinterFragmentPresenterImpl mPrinterFragmentPresenter;
    private Button btn_connect_printer;

    private LPAPI printer_api;
    private IDzPrinter.PrinterAddress printer_address;
    private LPAPI.Callback printer_callback = new LPAPI.Callback() {
        @Override
        public void onProgressInfo(IDzPrinter.ProgressInfo info, Object o) {
        }

        @Override
        public void onStateChange(IDzPrinter.PrinterAddress address, IDzPrinter.PrinterState state) {
            final IDzPrinter.PrinterAddress printerAddress = address;
            switch (state) {
                case Connected:
                case Connected2:
                    ViewUtil.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onPrinterConnected(printerAddress);
                        }
                    });
                    break;
                case Disconnected:
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

    private FloatingActionMenu printer_menu;
    private int qualit_res;
    private int density_res;
    private int gap_type_res;
    private int speed_res;

    @Override
    public void initMenu() {
        ImageView menuButton = new ImageView(mFragmentComponent.getActivity());
        menuButton.setImageDrawable(ViewUtil.getDrawable(R.mipmap.icon_device));
        FloatingActionButton fab = new FloatingActionButton.Builder(mFragmentComponent.getActivity())
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setContentView(menuButton)
                .build();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView btn_quality = new TextView(mFragmentComponent.getActivity());
        btn_quality.setLayoutParams(params);
        btn_quality.setText(ViewUtil.getString(R.string.quality));
        TextView btn_density = new TextView(mFragmentComponent.getActivity());
        btn_density.setLayoutParams(params);
        btn_density.setText(ViewUtil.getString(R.string.density));
        TextView btn_gap_type = new TextView(mFragmentComponent.getActivity());
        btn_gap_type.setLayoutParams(params);
        btn_gap_type.setText(ViewUtil.getString(R.string.gap_type));
        TextView btn_speed = new TextView(mFragmentComponent.getActivity());
        btn_speed.setLayoutParams(params);
        btn_speed.setText(ViewUtil.getString(R.string.speed));

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity());
        SubActionButton quality = rLSubBuilder.setContentView(btn_quality).build();
        SubActionButton density = rLSubBuilder.setContentView(btn_density).build();
        SubActionButton gap_type = rLSubBuilder.setContentView(btn_gap_type).build();
        SubActionButton speed = rLSubBuilder.setContentView(btn_speed).build();

        printer_menu = new FloatingActionMenu.Builder(mFragmentComponent.getActivity())
                .addSubActionView(quality)
                .addSubActionView(density)
                .addSubActionView(gap_type)
                .addSubActionView(speed)
                .attachTo(fab)
                .build();
        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printer_menu.close(true);
                qualit_res = PrinterOptions.getPrintQuality(mFragmentComponent.getActivityContext(), printer_address);
            }
        });
        density.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printer_menu.close(true);
                density_res = PrinterOptions.getDensity(mFragmentComponent.getActivityContext(), printer_address);
            }
        });
        gap_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printer_menu.close(true);
                gap_type_res = PrinterOptions.getPrintType(mFragmentComponent.getActivityContext(), printer_address);
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printer_menu.close(true);
                speed_res = PrinterOptions.getSpeed(mFragmentComponent.getActivityContext(), printer_address);
            }
        });
    }

    @Override
    public void initPrinter() {

        if (printer_address != null) {
            if (printer_api.openPrinterByAddress(printer_address)) {
                onPrinterConnecting(printer_address, true);
                initMenu();
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
        layout_not_connect.setVisibility(View.GONE);
        if (printer_menu != null) {
            printer_menu.getActivityContentView().setVisibility(View.VISIBLE);
        }
        ripple_printing.setVisibility(View.VISIBLE);
        showToast(getString(R.string.connectprintersuccess));
    }

    @Override
    public void onPrinterDisConnected() {
        if (printer_menu != null) {
            printer_menu.getActivityContentView().setVisibility(View.GONE);
        }
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
    public void print(String event) {
        if (!isPrinterConnected()){
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(event);
            String type = jsonObject.getString("type");
            PrinterType printerType = Enum.valueOf(PrinterType.class, type);
            String title = jsonObject.getString("title");
            String url = jsonObject.getString("url");
            StringBuilder content = new StringBuilder();
            content.append("Title:");
            content.append(title);
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

    @Override
    protected PrinterFragmentPresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return mPrinterFragmentPresenter;
    }

    @Override
    protected void initView() {
        btn_connect_printer = ViewUtil.bindView(layout_not_connect, R.id.btn_select_printer);
        printer_api = LPAPI.Factory.createInstance(printer_callback);
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
//                initPrinter();
            }
        }
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

}
