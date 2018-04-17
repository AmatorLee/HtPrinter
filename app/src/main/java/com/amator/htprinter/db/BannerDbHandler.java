package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.BannerDao;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.uitl.TimeUtil;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public class BannerDbHandler implements DBHandler<Banner> {

    public boolean isCanCache() {
        return TimeUtil.isBannerCanCache();
    }

    public boolean isCanRead() {
        return TimeUtil.isBannerCanQuery();
    }

    public BannerDao getBannerDao() {
        return HtPrinterApplcation.getsApplicationComponent().getDaoSession().getBannerDao();
    }


    @Override
    public void insertAll(List<Banner> banners) {
        if (banners != null && banners.size() > 0) {
            if (!isCanCache()) {
                return;
            }
            getBannerDao().insertOrReplaceInTx(banners);
            TimeUtil.saveBannerCurTime();
        }
    }

    @Override
    public void updateAll(List<Banner> banners) {
        if (banners != null && banners.size() > 0) {
            getBannerDao().updateInTx(banners);
            TimeUtil.saveBannerCurTime();
        }
    }

    @Override
    public List<Banner> queryAll() {
        if (!isCanRead()) {
            return getBannerDao().loadAll();
        } else {
            getBannerDao().deleteAll();
            TimeUtil.deleteBannerCurTimeCache();
            return null;
        }
    }

    @Override
    public void deleteAll(List<Banner> banners) {
        if (banners != null && banners.size() > 0) {
            getBannerDao().deleteInTx(banners);
            TimeUtil.deleteBannerCurTimeCache();
        }
    }
}
