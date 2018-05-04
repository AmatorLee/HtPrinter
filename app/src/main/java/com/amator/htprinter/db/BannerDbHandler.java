package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.BannerDao;
import com.amator.htprinter.module.Banner;
import com.amator.htprinter.uitl.TimeUtil;

import java.util.ArrayList;
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
        boolean isLogin = HtPrinterApplcation.getsApplicationComponent().getSharePreference().getBoolean("isLogin",false);
        if (!isLogin){
            return;
        }
        if (banners != null && banners.size() > 0) {
            if (!isCanCache()) {
                return;
            }
            List<Banner> datas = new ArrayList<>(banners.size());
            for (int i = 0; i < banners.size(); i++) {
                datas.add(i, banners.get(i).copy(i));
            }
            deleteAll();
            getBannerDao().insertOrReplaceInTx(datas);
            TimeUtil.saveBannerCurTime();
        }
    }

    @Override
    public void intsert(Banner banner) {
        getBannerDao().insertOrReplace(banner);
    }

    @Override
    public void updateAll(List<Banner> banners) {
//        if (banners != null && banners.size() > 0) {
//            getBannerDao().updateInTx(banners);
//            TimeUtil.saveBannerCurTime();
//        }
    }

    @Override
    public List<Banner> queryAll() {
        if (isCanRead()) {
            return getBannerDao().loadAll();
        } else {
            getBannerDao().deleteAll();
            TimeUtil.deleteBannerCurTimeCache();
            return null;
        }
    }

    @Override
    public void deleteAll() {
        getBannerDao().deleteAll();
        TimeUtil.deleteBannerCurTimeCache();
    }
}
