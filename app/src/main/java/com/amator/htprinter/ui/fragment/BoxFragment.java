package com.amator.htprinter.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amator.htprinter.R;
import com.amator.htprinter.adapter.BoxAdapter;
import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.presenter.impl.BoxFragmentPresenterImpl;
import com.amator.htprinter.refreshRecyclerView.RefreshRecyclerView;
import com.amator.htprinter.refreshRecyclerView.adapter.Action;
import com.amator.htprinter.ui.activity.BannerContentActivity;
import com.amator.htprinter.ui.view.BoxView;
import com.amator.htprinter.uitl.GlideImageLoader;
import com.amator.htprinter.uitl.StatusViewManager;
import com.amator.htprinter.uitl.ViewUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by AmatorLee on 2018/4/9.
 */

public class BoxFragment extends BaseFragment<BoxFragmentPresenterImpl> implements BoxView {

    @BindView(R.id.toolbar_box)
    Toolbar toolbar_box;
    @BindView(R.id.recycler_refresh)
    RefreshRecyclerView recycler_refresh;
    @Inject
    public BoxFragmentPresenterImpl mBoxFragmentPresenter;
    private BoxAdapter box_adapter;
    private List<HomePage> home_page_datas;
    private View head_view;
    private Banner banner_layout;
    private StatusViewManager status_view_manager;
    private int pageIndex = 0;
    private boolean isLoadMore = false;
    private boolean isRefresh = false;
    private List<com.amator.htprinter.module.Banner> banner_datas;

    @Override
    public void loadBannerSucceed(List<com.amator.htprinter.module.Banner> datas) {
        List<String> banners = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (datas != null) {
            banner_datas.clear();
            banner_datas.addAll(datas);
            for (int i = 0; i < datas.size(); i++) {
                banners.add(datas.get(i).getImagePath());
                titles.add(datas.get(i).getTitle());
            }
            banner_layout.update(banners);
            banner_layout.setBannerTitles(titles);
        }
    }

    @Override
    public void loadHomePageSucceed(List<HomePage> datas) {
        if (!isRefresh && pageIndex == 0 && !isLoadMore) {
            status_view_manager.onSuccess();
            box_adapter.addAll(datas);
        }
        if (isRefresh) {
            box_adapter.clear();
            box_adapter.addAll(datas);
            dissmissRefreshView();
        }
        if (isLoadMore) {
            box_adapter.addAll(datas);
            dismissLoadMoreView();
        }
    }

    @Override
    public void initHeader() {
        head_view = ViewUtil.createView(R.layout.layout_box_banner);
        banner_layout = ViewUtil.bindView(head_view, R.id.banner_home);
        banner_layout.setImageLoader(new GlideImageLoader());
        banner_datas = new ArrayList<>();
    }

    @Override
    public void dissmissRefreshView() {
        if (isRefresh) {
            recycler_refresh.dismissSwipeRefresh();
            isRefresh = false;
        }
    }

    @Override
    public void dismissLoadMoreView() {
        if (isLoadMore) {
            recycler_refresh.showNoMore();
            isLoadMore = false;
        }
    }

    @Override
    public void loadBannerFailed() {
        showToast(getString(R.string.txt_banner_load_error));
    }

    @Override
    public void loadHomePageFailed() {
        if (pageIndex == 0 && !isRefresh) {
            status_view_manager.onError();
        } else {
            dissmissRefreshView();
            dismissLoadMoreView();
        }
    }

    @Override
    protected BoxFragmentPresenterImpl injectPresenter() {
        mFragmentComponent.inject(this);
        return mBoxFragmentPresenter;
    }

    @Override
    protected void initView() {
        home_page_datas = new ArrayList<>();
        box_adapter = new BoxAdapter(mFragmentComponent.getActivityContext(), home_page_datas);
        initHeader();
        recycler_refresh.setLayoutManager(new LinearLayoutManager(mFragmentComponent.getActivityContext()));
        recycler_refresh.setItemSpace(0, 0, 0, 10);
        recycler_refresh.setSwipeRefreshColorsFromRes(R.color.colorPrimary, R.color.colorPrimaryDark);
        recycler_refresh.setAdapter(box_adapter);
        box_adapter.setHeader(head_view);
        status_view_manager = StatusViewManager.createView(mFragmentComponent.getActivityContext(),
                recycler_refresh);
        status_view_manager.onLoad();
        mBoxFragmentPresenter.loadBox(pageIndex);
    }


    @Override
    protected void setListener() {
        status_view_manager.setOnRetryClick(new StatusViewManager.onRetryClick() {
            @Override
            public void onRetryLoad() {
                status_view_manager.onLoad();
                mBoxFragmentPresenter.loadBox(pageIndex);
            }
        });
        recycler_refresh.addRefreshAction(new Action() {
            @Override
            public void onAction() {
                pageIndex = 0;
                mBoxFragmentPresenter.loadBox(pageIndex);
                isRefresh = true;
            }
        });
        recycler_refresh.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                pageIndex++;
                mBoxFragmentPresenter.loadBox(pageIndex);
                isLoadMore = true;
            }
        });
        banner_layout.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                com.amator.htprinter.module.Banner banner = banner_datas.get(position);
                startContentActivity(banner);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_box;
    }

    public void startContentActivity(com.amator.htprinter.module.Banner banner) {
        Intent intent = new Intent(mFragmentComponent.getActivity(), BannerContentActivity.class);
        intent.putExtra("banner", banner);
        startActivity(intent);
    }
}
