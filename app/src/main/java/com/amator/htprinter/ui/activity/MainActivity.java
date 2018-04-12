package com.amator.htprinter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.amator.htprinter.R;
import com.amator.htprinter.base.BaseActivity;
import com.amator.htprinter.presenter.impl.MainActivityPresenterImpl;
import com.amator.htprinter.ui.view.MainView;
import com.amator.htprinter.uitl.FragmentFactory;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresenterImpl> implements MainView {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottom_box)
    BottomBarItem btn_box;
    @BindView(R.id.bottom_print)
    BottomBarItem btn_print;
    @BindView(R.id.bottom_bar_container)
    BottomBarLayout btn_bottom_bar_container;;
    @Inject
    public MainActivityPresenterImpl mMainActivityPresenter;
    private BottomBarLayout.OnItemSelectedListener bottom_bar_select_listener;
    private Fragment fragment_box;
    private Fragment fragment_printer;
    private FragmentManager fragment_manager;

    @Override
    protected void setListener() {
        btn_bottom_bar_container.setOnItemSelectedListener(bottom_bar_select_listener);
        fragment_manager.beginTransaction()
                .show(fragment_box)
                .hide(fragment_printer)
                .commit();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragment_manager = getSupportFragmentManager();
        addfragment(savedInstanceState);
        bottom_bar_select_listener = new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem item, int i, int i1) {
                if (i1 == 0){
                    fragment_manager.beginTransaction()
                            .show(fragment_box)
                            .hide(fragment_printer)
                            .commit();
                }else if (i1 == 1){
                    fragment_manager.beginTransaction()
                            .show(fragment_printer)
                            .hide(fragment_box)
                            .commit();
                }
            }
        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
            fragment_printer = FragmentFactory.create(FragmentFactory.PRINTER_FRAGMENT);
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_box, "fragment_box")
                    .commit();
            fragment_manager.beginTransaction()
                    .add(R.id.rl_main_container, fragment_printer, "fragment_printer")
                    .commit();
        } else {
            fragment_box = getSupportFragmentManager().findFragmentByTag("fragment_box");
            fragment_printer = getSupportFragmentManager().findFragmentByTag("fragment_printer");
            if (fragment_box == null){
                fragment_box = FragmentFactory.create(FragmentFactory.BOX_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_box, "fragment_box")
                        .commit();
            }
            if (fragment_printer == null){
                fragment_printer = FragmentFactory.create(FragmentFactory.PRINTER_FRAGMENT);
                fragment_manager.beginTransaction()
                        .add(R.id.rl_main_container, fragment_printer, "fragment_printer")
                        .commit();
            }
        }
    }
}
