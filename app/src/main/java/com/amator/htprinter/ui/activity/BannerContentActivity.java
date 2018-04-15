package com.amator.htprinter.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.dialog.ActionSheetDialog;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.presenter.impl.ContentActivityPresenterImpl;
import com.amator.htprinter.ui.view.ContentView;
import com.amator.htprinter.uitl.RxLogTool;
import com.amator.htprinter.uitl.StatusViewManager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by AmatorLee on 2018/4/12.
 */

public class BannerContentActivity extends BaseActivity<ContentActivityPresenterImpl> implements ContentView, ActionSheetDialog.OnSheetItemClickListener {

    public static final String TAG = BannerContentActivity.class.getSimpleName();

    @Inject
    ContentActivityPresenterImpl content_activity_presenter;
    @BindView(R.id.toolbar_content)
    Toolbar toolbar_content;
    @BindView(R.id.webview_content)
    WebView webview;
    @BindView(R.id.btn_print_menu)
    ImageButton btn_print_menu;
    private boolean isExcuteError = false;
    private StatusViewManager status_view_manager;
    private Banner banner;
    private ActionBar action_bar;

    @Override
    protected void setListener() {
        toolbar_content.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_print_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar_content.setTitle(title);
            }
        });
        webview.setWebViewClient(new WebViewClient() {

            @TargetApi(21)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                RxLogTool.d(TAG, "shouldOverrideUrlLoading2: " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                RxLogTool.d(TAG, "shouldOverrideUrlLoading1: " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                RxLogTool.d(TAG, "onPageStarted: " + url);
                isExcuteError = false;
                status_view_manager.onLoad();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                RxLogTool.d(TAG, "onPageFinish: " + url);
                if (isExcuteError) {
                    status_view_manager.onError();
                } else {
                    status_view_manager.onSuccess();
                }
            }

            @TargetApi(23)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                RxLogTool.d(TAG, "onReceivedError: " + error.getErrorCode());
                isExcuteError = true;
            }

            @TargetApi(21)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                RxLogTool.d(TAG, "onReceivedHttpError: " + errorResponse.getStatusCode());
                isExcuteError = true;
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            this.banner = (Banner) intent.getSerializableExtra("banner");
            if (banner != null && !TextUtils.isEmpty(banner.getUrl())) {
                loadUrl(banner.getUrl());
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void openMenu() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("打印一维码", ActionSheetDialog.SheetItemColor.Blue, this)
                .addSheetItem("打印二维码", ActionSheetDialog.SheetItemColor.Blue, this)
                .addSheetItem("打印详细信息", ActionSheetDialog.SheetItemColor.Blue, this)
                .show();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar_content);
        action_bar = getSupportActionBar();
        action_bar.setTitle("");
        status_view_manager = StatusViewManager.createView(this, webview);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_content;
    }

    @Override
    public ContentActivityPresenterImpl initPresenter() {
        mActivityComponent.inject(this);
        return content_activity_presenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        status_view_manager.onDestory();
        if (webview != null) {
            ViewGroup parent = (ViewGroup) webview.getParent();
            if (parent != null) {
                parent.removeView(webview);
            }
            webview.removeAllViews();
            webview.loadUrl("about:blank");
            webview.stopLoading();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroy();
            webview = null;
        }
    }

    @Override
    public void initSetting() {
        WebSettings ws = webview.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);

        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);

//        setDefaultZoom
        // This method was deprecated in API level 19. api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        //缩放比例 1
        webview.setInitialScale(1);

        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);

        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    @Override
    public void loadUrl(String url) {
        webview.loadUrl(url);
    }

    @Override
    public void onClick(int which) {
        RxLogTool.d(TAG, "which: " + which);
    }
}
