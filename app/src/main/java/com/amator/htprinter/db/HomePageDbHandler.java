package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.HomePageDao;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.uitl.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public class HomePageDbHandler implements DBHandler<HomePage> {
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
        boolean isLogin = HtPrinterApplcation.getsApplicationComponent().getSharePreference().getBoolean("isLogin",false);
        if (!isLogin){
            return;
        }
        if (banners != null && banners.size() > 0) {
            if (!isCanCache()) {
                return;
            }
            List<HomePage> datas = new ArrayList<>(banners.size());
            for (int i = 0; i < banners.size(); i++) {
                datas.add(i,banners.get(i).copy(i));
            }
            deleteAll();
            getHomePageDao().insertOrReplaceInTx(datas);
            TimeUtil.saveHomePageCurTime();
        }
    }

    @Override
    public void intsert(HomePage page) {
        getHomePageDao().insertOrReplace(page);
    }

    @Override
    public void updateAll(List<HomePage> banners) {
//        if (banners != null && banners.size() > 0) {
//            getHomePageDao().updateInTx(banners);
//            TimeUtil.saveHomePageCurTime();
//        }
    }

    @Override
    public List<HomePage> queryAll() {
        if (isCanRead()) {
            return getHomePageDao().loadAll();
        } else {
            getHomePageDao().deleteAll();
            TimeUtil.deleteHomePageCurTimeCache();
            return null;
        }
    }

    @Override
    public void deleteAll() {
        getHomePageDao().deleteAll();
        TimeUtil.deleteHomePageCurTimeCache();
    }
}
