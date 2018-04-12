package com.amator.htprinter.presenter.impl;

import com.amator.htprinter.R;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.module.BaseModule;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.module.HomePageBase;
import com.amator.htprinter.presenter.BoxFragmentPresenter;
import com.amator.htprinter.ui.view.BoxView;
import com.amator.htprinter.uitl.NetUtils;
import com.amator.htprinter.uitl.ViewUtil;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by AmatorLee on 2018/4/9.
 */

public class BoxFragmentPresenterImpl extends BasePresenterImpl<BoxView> implements BoxFragmentPresenter<BoxView> {

    private static final String TAG = BoxFragmentPresenterImpl.class.getSimpleName();

    @Inject
    public BoxFragmentPresenterImpl() {

    }

    public List<Banner> getBannerFromDB() {
        return null;
    }

    public List<HomePage> getHomePageFromDB() {
        return null;
    }

    @Override
    public void loadBox(int index) {
        if (!NetUtils.isConnect()) {
            mView.showToast(ViewUtil.getString(R.string.txt_no_net));
            List<Banner> banners = getBannerFromDB();
            List<HomePage> homePages = getHomePageFromDB();
            if (banners == null
                    || banners.size() == 0) {
                mView.loadBannerFailed();
            }
            if (homePages == null || homePages.size() == 0) {
                mView.loadHomePageFailed();
            }
        } else {
            requestBanner();
            requestHomePages(index);
        }
    }

    @Override
    public void addBannerToDB(List<Banner> data) {

    }

    @Override
    public void addHomePageToDB(List<HomePage> data) {

    }

    @Override
    public void refreshBox(int index) {

    }

    @Override
    public void loadMoreBox(int index) {
        String url = Constans.ARTICAL_LIST_PRE + index + Constans.ARTICAL_LIST_BEHIND;
        ItheimaHttp.getAsync(url, new HttpResponseListener<BaseModule<List<HomePage>>>() {
            @Override
            public void onResponse(BaseModule<List<HomePage>> module) {
                mView.loadHomePageSucceed(module.getData());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                mView.loadHomePageFailed();
            }
        });
    }

    @Override
    public void requestBanner() {
        ItheimaHttp.getAsync(Constans.BANNER_PATH, new HttpResponseListener<BaseModule<List<Banner>>>() {
            @Override
            public void onResponse(BaseModule<List<Banner>> module) {
                mView.loadBannerSucceed(module.getData());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                mView.loadBannerFailed();
            }
        });
    }

    @Override
    public void requestHomePages(int index) {
        String url = Constans.ARTICAL_LIST_PRE + index + Constans.ARTICAL_LIST_BEHIND;
        ItheimaHttp.getAsync(url, new HttpResponseListener<BaseModule<HomePageBase>>() {
            @Override
            public void onResponse(BaseModule<HomePageBase> module) {
                mView.loadHomePageSucceed(module.getData().getDatas());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                mView.loadHomePageFailed();
            }
        });
    }
}
