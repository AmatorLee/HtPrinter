package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.HomePageDao;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.uitl.TimeUtil;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public class HomePageDbHandler implements DBHandler<HomePage>{
    public boolean isCanCache() {
        return TimeUtil.isHomePageCanCache();
    }

    public boolean isCanRead() {
        return TimeUtil.isHomePageCanQuery();
    }

    public HomePageDao getHomePageDao() {
        return HtPrinterApplcation.getsApplicationComponent().getDaoSession().getHomePageDao();
    }


    @Override
    public void insertAll(List<HomePage> banners) {
        if (banners != null && banners.size() > 0) {
            if (!isCanCache()) {
                return;
            }
            getHomePageDao().insertOrReplaceInTx(banners);
            TimeUtil.saveHomePageCurTime();
        }
    }

    @Override
    public void updateAll(List<HomePage> banners) {
        if (banners != null && banners.size() > 0) {
            getHomePageDao().updateInTx(banners);
            TimeUtil.saveHomePageCurTime();
        }
    }

    @Override
    public List<HomePage> queryAll() {
        if (!isCanRead()) {
            return getHomePageDao().loadAll();
        } else {
            getHomePageDao().deleteAll();
            TimeUtil.deleteHomePageCurTimeCache();
            return null;
        }
    }

    @Override
    public void deleteAll(List<HomePage> banners) {
        if (banners != null && banners.size() > 0) {
            getHomePageDao().deleteInTx(banners);
            TimeUtil.deleteHomePageCurTimeCache();
        }
    }
}
