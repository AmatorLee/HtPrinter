package com.amator.htprinter.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.module.UserBean;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.presenter.impl.MainActivityPresenterImpl;
import com.amator.htprinter.ui.fragment.MineFragment;
import com.amator.htprinter.ui.fragment.PrinterFragment;
import com.amator.htprinter.ui.fragment.VoiceFragment;
import com.amator.htprinter.ui.view.MainView;
import com.amator.htprinter.uitl.FragmentFactory;
import com.amator.htprinter.uitl.PrinterType;
import com.amator.htprinter.uitl.RxLogTool;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresenterImpl> implements MainView {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_box)
    BottomBarItem btn_box;
    @BindView(R.id.bottom_print)
    BottomBarItem btn_print;
    @BindView(R.id.bottom_bar_container)
    BottomBarLayout btn_bottom_bar_container;
    ;
    @Inject
    public MainActivityPresenterImpl mMainActivityPresenter;
    private Fragment fragment_box;
    private Fragment fragment_printer;
    private FragmentManager fragment_manager;
    private Fragment fragment_voice;
    private Fragment fragment_mine;
    private boolean isExit;

    @Override
    protected void setListener() {
        btn_bottom_bar_container.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem item, int i, int i1) {
                if (i1 == 0) {
                    fragment_manager.beginTransaction()
                            .show(fragment_box)
                            .hide(fragment_voice)
                            .hide(fragment_printer)
                            .hide(fragment_mine)
                            .commit();
                } else if (i1 == 2) {
                    fragment_manager.beginTransaction()
                            .show(fragment_printer)
                            .hide(fragment_voice)
                            .hide(fragment_mine)
                            .hide(fragment_box)
                            .commit();
                } else if (i1 == 1) {
                    fragment_manager.beginTransaction()
                            .show(fragment_voice)
                            .hide(fragment_box)
                            .hide(fragment_mine)
                            .hide(fragment_printer)
                            .commit();
                } else if (i1 == 3) {
                    fragment_manager.beginTransaction()
                            .show(fragment_mine)
                            .hide(fragment_box)
                            .hide(fragment_voice)
                            .hide(fragment_printer)
                            .commit();
                }
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragment_manager = getSupportFragmentManager();
        addfragment(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPrintEvent(String event) {
        RxLogTool.d(TAG, "json: " + event);
//        btn_bottom_bar_container.getBottomItem(1).performClick();
        btn_bottom_bar_container.setCurrentItem(2);
        RxLogTool.d(TAG, "printer is null: " + (fragment_printer == null));
        RxLogTool.d(TAG, "box is null: " + (fragment_box == null));
        fragment_manager.beginTransaction()
                .show(fragment_printer)
                .hide(fragment_voice)
                .hide(fragment_box)
                .hide(fragment_mine)
                .commitAllowingStateLoss();
        ((PrinterFragment) fragment_printer).print(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void printVoiceMessage(VoiceBean event, PrinterType type) {
//        btn_bottom_bar_container.getBottomItem(1).performClick();
        btn_bottom_bar_container.setCurrentItem(2);
        fragment_manager.beginTransaction()
                .show(fragment_printer)
                .hide(fragment_voice)
                .hide(fragment_mine)
                .hide(fragment_box)
                .commitAllowingStateLoss();
        ((PrinterFragment) fragment_printer).printVoiceBean(event, type);
    }

    @Override
    public MainActivityPresenterImpl initPresenter() {
        mActivityComponent.inject(this);
        return mMainActivityPresenter;
    }


    @Override
    public void addfragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragment_box = FragmentFactory.create(FragmentFactory.BOX_FRAGMENT);
            fragment_voice = FragmentFactory.create(FragmentFactory.VOICE_FRAGMENT);
            fragment_printer = FragmentFactory.create(FragmentFactory.PRINTER_FRAGMENT);
            fragment_mine = FragmentFactory.create(FragmentFactory.MINE_FRAGMENT);
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_box, "fragment_box")
                    .commit();
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_printer, "fragment_printer")
                    .commit();
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_voice, "fragment_voice").commit();
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_mine, "fragment_mine").commit();
        } else {
            fragment_box = getSupportFragmentManager().findFragmentByTag("fragment_box");
            fragment_printer = getSupportFragmentManager().findFragmentByTag("fragment_printer");
            if (fragment_box == null) {
                fragment_box = FragmentFactory.create(FragmentFactory.BOX_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_box, "fragment_box")
                        .commit();
            }
            if (fragment_printer == null) {
                fragment_printer = FragmentFactory.create(FragmentFactory.PRINTER_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_printer, "fragment_printer")
                        .commit();
            }
            if (fragment_voice == null) {
                fragment_voice = FragmentFactory.create(FragmentFactory.VOICE_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_voice, "fragment_voice")
                        .commit();
            }
            if (fragment_mine == null) {
                fragment_mine = FragmentFactory.create(FragmentFactory.MINE_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_mine, "fragment_mine").commit();
            }
        }
        fragment_manager.beginTransaction()
                .show(fragment_box)
                .hide(fragment_voice)
                .hide(fragment_printer)
                .hide(fragment_mine)
                .commit();
    }

    public void setCurrentItem(int pos) {
        btn_bottom_bar_container.setCurrentItem(pos);
        if (pos == 0) {
            fragment_manager.beginTransaction()
                    .show(fragment_box)
                    .hide(fragment_voice)
                    .hide(fragment_printer)
                    .hide(fragment_mine)
                    .commitAllowingStateLoss();
        } else if (pos == 2) {
            fragment_manager.beginTransaction()
                    .show(fragment_printer)
                    .hide(fragment_voice)
                    .hide(fragment_mine)
                    .hide(fragment_box)
                    .commitAllowingStateLoss();
        } else if (pos == 1) {
            fragment_manager.beginTransaction()
                    .show(fragment_voice)
                    .hide(fragment_box)
                    .hide(fragment_mine)
                    .hide(fragment_printer)
                    .commitAllowingStateLoss();
        } else if (pos == 3) {
            fragment_manager.beginTransaction()
                    .show(fragment_mine)
                    .hide(fragment_box)
                    .hide(fragment_voice)
                    .hide(fragment_printer)
                    .commitAllowingStateLoss();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void afterLogin(UserBean user){
        setCurrentItem(3);
        ((MineFragment)fragment_mine).login(user);
    }

    public void notifyVoice() {
        setCurrentItem(1);
        ((VoiceFragment)fragment_voice).notifyAdapter();
    }
}
