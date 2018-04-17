package com.amator.htprinter.presenter.impl;

import com.amator.htprinter.R;
import com.amator.htprinter.base.Constans;
import com.amator.htprinter.db.BannerDbHandler;
import com.amator.htprinter.db.HomePageDbHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
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
        return ((BannerDbHandler) DbHandlerFactory.create(DbHandlerFactory.BANNER)).queryAll();
    }

    public List<HomePage> getHomePageFromDB() {
        return ((HomePageDbHandler) DbHandlerFactory.create(DbHandlerFactory.HOME_PAGE)).queryAll();
    }

    @Override
    public void loadBox(int index) {
        if (!NetUtils.isConnect()) {
            mView.showToast(ViewUtil.getString(R.string.txt_no_net));
            List<Banner> banners = getBannerFromDB();
            List<HomePage> homePages = getHomePageFromDB();
            if (banners == null && homePages == null) {
                mView.loadBoxFailed();
            } else if (banners != null && banners.size() > 0) {
                mView.loadBannerSucceed(banners);
            } else if (homePages != null && homePages.size() > 0) {
                mView.loadHomePageSucceed(homePages);
            } else {
                mView.loadBoxFailed();
            }
        } else {
            requestBanner();
            requestHomePages(index);
        }
    }

    @Override
    public void addBannerToDB(List<Banner> data) {
        ((BannerDbHandler) DbHandlerFactory.create(DbHandlerFactory.BANNER)).insertAll(data);
    }

    @Override
    public void addHomePageToDB(List<HomePage> data) {
        ((HomePageDbHandler) DbHandlerFactory.create(DbHandlerFactory.HOME_PAGE)).insertAll(data);
    }

    @Override
    public void refreshBox(int index) {
        requestHomePages(index);
    }

    @Override
    public void loadMoreBox(int index) {
        String url = Constans.ARTICAL_LIST_PRE + index + Constans.ARTICAL_LIST_BEHIND;
        ItheimaHttp.getAsync(url, new HttpResponseListener<BaseModule<HomePageBase>>() {
            @Override
            public void onResponse(BaseModule<HomePageBase> module) {
                addHomePageToDB(module.getData().getDatas());
                mView.loadHomePageSucceed(module.getData().getDatas());
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
                addBannerToDB(module.getData());
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
                addHomePageToDB(module.getData().getDatas());
                mView.loadHomePageSucceed(module.getData().getDatas());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                mView.loadHomePageFailed();
            }
        });
    }
}
