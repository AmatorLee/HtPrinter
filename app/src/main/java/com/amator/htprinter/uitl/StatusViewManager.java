package com.amator.htprinter.uitl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.amator.htprinter.R;

/**
 * Created by AmatorLee on 2017/7/12.
 */

public class StatusViewManager extends StatusInterface implements View.OnClickListener {

    private LayoutInflater mInflater;
    /**
     * 加载view
     */
    private View mLoadView;
    /**
     * 错误view
     */
    private View mErrorView;
    /**
     * 无数据view
     */
    private View mEmptyView;
    /**
     * 实际view
     */
    private View mContentView;
    /**
     * 无网络链接时得view
     */
    private View mNoNetView;

    private RelativeLayout.LayoutParams mParams;
    /**
     * 保存状态view的container
     */
    private RelativeLayout mStatusContainer;

    private Context mContext;
    /**
     * 避免重复添加
     */
    private boolean isAddLoad, isAddEmpty, isAddNoNet, isAddError;
    /**
     * 可见状态
     */
    public static final int V = View.VISIBLE;
    /***
     * 不可见状态
     */
    public static final int G = View.GONE;
    /**
     * 重新加载接口
     */
    private onRetryClick mOnRetryClick;
    /**
     * 切换到主线程改变view的状态
     */
    private Handler mMainThreadHandler;


    private int empty_layout_id = -1;
    private int error_layout_id = -1;
    private int no_net_layout_id = -1;
    private int loading_layout_id = -1;

    public static final String ERROR = "error";
    public static final String EMPTY = "empty";
    public static final String NONET = "nonet";
    public static final String LOAD = "load";


    public void setOnRetryClick(onRetryClick onRetryClick) {
        mOnRetryClick = onRetryClick;
    }

    private StatusViewManager(Context context, View contentView, int loading_layout_id, int empty_layout_id, int error_layout_id, int no_net_layout_id) {
        super();
        this.loading_layout_id = loading_layout_id;
        this.empty_layout_id = empty_layout_id;
        this.error_layout_id = error_layout_id;
        this.no_net_layout_id = no_net_layout_id;
        mContentView = contentView;
        mMainThreadHandler = new Handler(Looper.getMainLooper());
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        initView();
        setOnClick();
        initContainer();
    }

    private void setOnClick() {
        if (mEmptyView != null)
            mEmptyView.setOnClickListener(this);
        if (mNoNetView != null)
            mNoNetView.setOnClickListener(this);
        if (mErrorView != null)
            mErrorView.setOnClickListener(this);
    }

    public static StatusViewManager createView(Context context, View ContentView) {
        return new StatusViewManager(context, ContentView, -1, -1, -1, -1);
    }

    public static StatusViewManager createView(Context context, View contentView, int loading_layout_id, int empty_layout_id, int error_layout_id, int no_net_layout_id) {
        return new StatusViewManager(context, contentView, loading_layout_id, empty_layout_id, error_layout_id, no_net_layout_id);
    }


    @Override
    protected void initView() {
        if (loading_layout_id == -1) {
            loading_layout_id = R.layout.layout_loading;
        }
        if (empty_layout_id == -1) {
            empty_layout_id = R.layout.layout_empty;
        }
        if (error_layout_id == -1) {
            error_layout_id = R.layout.layout_error;
        }
        if (no_net_layout_id == -1) {
            no_net_layout_id = R.layout.layout_no_net;
        }
        try {
            mLoadView = mInflater.inflate(loading_layout_id, null);
            mLoadView.setTag(LOAD);
            mErrorView = mInflater.inflate(error_layout_id, null);
            mErrorView.setTag(ERROR);
            mEmptyView = mInflater.inflate(empty_layout_id, null);
            mEmptyView.setTag(EMPTY);
            mNoNetView = mInflater.inflate(no_net_layout_id, null);
            mNoNetView.setTag(NONET);
        } finally {
            mInflater = null;
        }
    }

    @Override
    public void initContainer() {
        mStatusContainer = new RelativeLayout(mContext);
        mStatusContainer.setLayoutParams(mParams);
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        parent.addView(mStatusContainer);
    }

    @Override
    public void onLoad() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadView != null && !isAddLoad) {
                    isAddLoad = true;
                    mStatusContainer.addView(mLoadView, mParams);
                }
                show(STATUS.LOADING);
            }
        });
    }

    private void show(STATUS result) {
        switch (result) {
            case SUCCESS:
                changeVisiable(V, G, G, G, G);
                break;
            case LOADING:
                changeVisiable(G, V, G, G, G);
                break;
            case NONET:
                changeVisiable(G, G, G, G, V);
                break;
            case ERROR:
                changeVisiable(G, G, G, V, G);
                break;
            case EMPTY:
                changeVisiable(G, G, V, G, G);
                break;
        }
    }

    private void changeVisiable(final int contentStatus, final int loadStatus, final int emptyStatus, final int errorStatus, final int nonetStatus) {
        if (mContentView != null) {
            mContentView.setVisibility(contentStatus);
        }
        if (mLoadView != null) {
            mLoadView.setVisibility(loadStatus);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(emptyStatus);
        }
        if (mNoNetView != null) {
            mNoNetView.setVisibility(nonetStatus);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(errorStatus);
        }
    }

    @Override
    public void onSuccess() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                show(STATUS.SUCCESS);
            }
        });
    }

    @Override
    public void onNoNet() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isAddNoNet && mNoNetView != null) {
                    mStatusContainer.addView(mNoNetView, mParams);
                    isAddNoNet = true;
                }
                show(STATUS.NONET);
            }
        });
    }

    @Override
    public void onError() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isAddError && mErrorView != null) {
                    mStatusContainer.addView(mErrorView, mParams);
                    isAddError = true;
                }
                show(STATUS.ERROR);
            }
        });
    }

    @Override
    public void onEmpty() {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isAddEmpty && mEmptyView != null) {
                    mStatusContainer.addView(mEmptyView, mParams);
                    isAddEmpty = true;
                }
                show(STATUS.EMPTY);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (mOnRetryClick != null) {
            mOnRetryClick.onRetryLoad();
        }
    }


    public enum STATUS {
        LOADING,
        EMPTY,
        ERROR,
        SUCCESS,
        NONET
    }


    @Override
    public void onDestory() {

        isAddNoNet = false;
        isAddEmpty = false;
        isAddLoad = false;
        isAddError = false;

        mContext = null;

        if (mLoadView != null) {
            mLoadView = null;
        }
        if (mContentView != null) {
            mContentView = null;
        }
        if (mEmptyView != null) {
            mEmptyView = null;
        }
        if (mErrorView != null) {
            mErrorView = null;
        }
        if (mNoNetView != null) {
            mNoNetView = null;
        }
        if (mParams != null) {
            mParams = null;
        }
        for (int i = 0; i < mStatusContainer.getChildCount(); i++) {
            mStatusContainer.removeViewAt(i);
        }
        mStatusContainer = null;
    }

    public interface onRetryClick {
        void onRetryLoad();
    }

}
