package com.amator.htprinter.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amator.htprinter.R;
import com.amator.htprinter.adapter.VoiceAdapter;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.db.VoiceHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.presenter.impl.VoiceFragmentPresenterImpl;
import com.amator.htprinter.refreshRecyclerView.RefreshRecyclerView;
import com.amator.htprinter.ui.activity.MainActivity;
import com.amator.htprinter.ui.activity.RecordActivity;
import com.amator.htprinter.ui.view.VoiceView;
import com.amator.htprinter.uitl.StatusViewManager;
import com.amator.htprinter.uitl.ViewUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by AmatorLee on 2018/5/2.
 */

public class VoiceFragment extends BaseFragment<VoiceFragmentPresenterImpl> implements VoiceView {


    private static final int RECORDER_CODE = 2222;
    @BindView(R.id.toolbar_voice)
    Toolbar mToolbar;
    @BindView(R.id.recycler_voice)
    RefreshRecyclerView mRefreshRecyclerView;
    private StatusViewManager mStatusViewManager;
    private List<VoiceBean> mVoiceBeans;
    private VoiceAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void initToolbar() {
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).setTitle("");
        mToolbar.inflateMenu(R.menu.voice);
    }

    @Override
    public void queryVoice() {
        mStatusViewManager.onLoad();
        List<VoiceBean> beans = ((VoiceHandler) DbHandlerFactory.create(DbHandlerFactory.VOICE)).queryAll();
        if (beans == null || beans.isEmpty()) {
            mStatusViewManager.onEmpty();
        } else {
            mAdapter.clear();
            mAdapter.addAll(beans);
            mStatusViewManager.onSuccess();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mToolbar.getMenu().clear();
        inflater.inflate(R.menu.voice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    protected VoiceFragmentPresenterImpl injectPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initToolbar();
        mVoiceBeans = new ArrayList<>();
        mAdapter = new VoiceAdapter((MainActivity) getActivity(), mVoiceBeans);
        mRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefreshRecyclerView.setItemSpace(0, 0, 0, 10);
        mRefreshRecyclerView.setAdapter(mAdapter);
        mStatusViewManager = StatusViewManager.createView(getActivity(), mRefreshRecyclerView);
//        queryVoice();
    }

    @Override
    protected void setListener() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AndPermission.with(getActivity())
                        .permission(Permission.RECORD_AUDIO)
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                showToast(ViewUtil.getString(R.string.txt_no_permission));
                            }
                        })
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(getActivity(), RecordActivity.class);
                                startActivityForResult(intent, RECORDER_CODE);
                            }
                        })
                        .start();

                return false;
            }
        });
        mStatusViewManager.setOnRetryClick(new StatusViewManager.onRetryClick() {
            @Override
            public void onRetryLoad() {
                queryVoice();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voice;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isCreate && !hidden) {
            queryVoice();
        }
    }

    public void notifyAdapter() {
        queryVoice();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORDER_CODE && resultCode == Activity.RESULT_OK) {
            queryVoice();
        }
    }
}
