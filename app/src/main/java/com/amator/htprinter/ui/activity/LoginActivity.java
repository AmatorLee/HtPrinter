package com.amator.htprinter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.db.UserDbHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
import com.amator.htprinter.module.UserBean;
import com.amator.htprinter.presenter.impl.BasePresenterImpl;
import com.amator.htprinter.uitl.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by AmatorLee on 2018/5/4.
 */
public class LoginActivity extends BaseActivity<BasePresenterImpl> {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar;
    @BindView(R.id.edt_username)
    EditText mEdtUserName;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.txt_to_register)
    TextView mTxtRegister;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public BasePresenterImpl initPresenter() {
        return null;
    }

    @OnClick({R.id.btn_login, R.id.txt_to_register})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_login:
                String username = mEdtUserName.getText().toString();
                String password = mEdtPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    showToast(ViewUtil.getString(R.string.txt_username_password_is_null));
                    return;
                }
                if (!com.amator.htprinter.uitl.TextUtils.checkEmail(username) && !com.amator.htprinter.uitl.TextUtils.checkCellphone(username)) {
                    showToast(ViewUtil.getString(R.string.txt_username_error));
                    return;
                }
                if (password.length() < 8) {
                    showToast(ViewUtil.getString(R.string.txt_password_error));
                    return;
                }
                login(username, password);
                break;
            case R.id.txt_to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    private void login(String username, String password) {
        UserBean user = ((UserDbHandler) DbHandlerFactory.create(DbHandlerFactory.USER)).login(username, password);
        if (user == null) {
            showToast(ViewUtil.getString(R.string.txt_user_login_error));
            return;
        }
        showToast(ViewUtil.getString(R.string.login_succeed));
        EventBus.getDefault().post(user);
        finish();
    }

}
