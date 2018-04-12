package com.amator.htprinter.presenter;

import com.amator.htprinter.base.BasePresenter;
import com.amator.htprinter.base.BaseView;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.module.HomePage;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/9.
 */

public interface BoxFragmentPresenter<T extends BaseView> extends BasePresenter<T> {

    void loadBox(int index);

    void addBannerToDB(List<Banner> data);

    void addHomePageToDB(List<HomePage> data);

    void refreshBox(int index);

    void loadMoreBox(int index);

    void requestBanner();

    void requestHomePages(int index);

}
