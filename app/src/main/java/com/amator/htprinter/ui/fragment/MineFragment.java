package com.amator.htprinter.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.db.BannerDbHandler;
import com.amator.htprinter.db.HomePageDbHandler;
import com.amator.htprinter.db.VoiceHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
import com.amator.htprinter.module.UserBean;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.ui.activity.LoginActivity;
import com.amator.htprinter.uitl.DialogUtil;
import com.amator.htprinter.uitl.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AmatorLee on 2018/5/4.
 */
public class MineFragment extends BaseFragment<BasePresenterImpl> {

    @BindView(R.id.txt_user_name)
    TextView mTxtUserName;
    @BindView(R.id.btn_clear_db)
    Button mBtnClearDb;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    private boolean isLogin = false;


    @Override
    protected BasePresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return null;
    }

    @Override
    protected void initView() {
        isLogin = mFragmentComponent.getSharePreference().getBoolean("isLogin", false);
        String username = mFragmentComponent.getSharePreference().getString("username", "");
        if (!isLogin) {
            mTxtUserName.setText(R.string.txt_click_login);
            mTxtUserName.setEnabled(true);
            mBtnLogout.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(username)) {
            mTxtUserName.setText(username);
            mTxtUserName.setEnabled(false);
            mBtnLogout.setVisibility(View.VISIBLE);
        } else {
            mTxtUserName.setEnabled(true);
            mTxtUserName.setText(R.string.txt_click_login);
            mBtnLogout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.btn_logout, R.id.btn_clear_db, R.id.txt_user_name})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_clear_db) {
            isLogin = mFragmentComponent.getSharePreference().getBoolean("isLogin", false);
            if (!isLogin) {
                showToast(ViewUtil.getString(R.string.txt_pleaselogin));
            } else {
                clearDb();
            }
        } else if (id == R.id.btn_logout) {

            DialogUtil.newInstance()
                    .dialogWithConfirmAndCancel(getContext(), "退出登录将清除缓存", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            logout();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });

        } else if (id == R.id.txt_user_name) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void logout() {
        mFragmentComponent.getSharePreference().edit().putBoolean("isLogin", false).apply();
        mFragmentComponent.getSharePreference().edit().putString("username", "").apply();
        mTxtUserName.setText(R.string.txt_click_login);
        mTxtUserName.setEnabled(true);
        mBtnLogout.setVisibility(View.GONE);
        clearDb();
    }

    private void clearDb() {
        ((VoiceHandler) DbHandlerFactory.create(DbHandlerFactory.VOICE)).deleteAll();
        ((BannerDbHandler) DbHandlerFactory.create(DbHandlerFactory.BANNER)).deleteAll();
        ((HomePageDbHandler) DbHandlerFactory.create(DbHandlerFactory.HOME_PAGE)).deleteAll();
        showToast(ViewUtil.getString(R.string.txt_clear_db_succeed));
    }

    public void login(UserBean user) {
        mFragmentComponent.getSharePreference().edit().putBoolean("isLogin", true).apply();
        mFragmentComponent.getSharePreference().edit().putString("username", user.getNick()).apply();
        mTxtUserName.setText(user.getNick());
        mTxtUserName.setEnabled(false);
        mBtnLogout.setVisibility(View.VISIBLE);
    }
}
