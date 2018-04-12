package com.amator.htprinter.ui.view;

import com.amator.htprinter.base.BaseView;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.module.HomePage;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/9.
 */

public interface BoxView extends BaseView {

    void loadBannerSucceed(List<Banner> datas);

    void loadHomePageSucceed(List<HomePage> datas);

    void initHeader();

    void dissmissRefreshView();

    void dismissLoadMoreView();

    void loadBannerFailed();

    void loadHomePageFailed();
}
