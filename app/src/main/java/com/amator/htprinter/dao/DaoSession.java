package com.amator.htprinter.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.amator.htprinter.module.Banner;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.module.UserBean;

import com.amator.htprinter.dao.BannerDao;
import com.amator.htprinter.dao.HomePageDao;
import com.amator.htprinter.dao.VoiceBeanDao;
import com.amator.htprinter.dao.UserBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bannerDaoConfig;
    private final DaoConfig homePageDaoConfig;
    private final DaoConfig voiceBeanDaoConfig;
    private final DaoConfig userBeanDaoConfig;

    private final BannerDao bannerDao;
    private final HomePageDao homePageDao;
    private final VoiceBeanDao voiceBeanDao;
    private final UserBeanDao userBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bannerDaoConfig = daoConfigMap.get(BannerDao.class).clone();
        bannerDaoConfig.initIdentityScope(type);

        homePageDaoConfig = daoConfigMap.get(HomePageDao.class).clone();
        homePageDaoConfig.initIdentityScope(type);

        voiceBeanDaoConfig = daoConfigMap.get(VoiceBeanDao.class).clone();
        voiceBeanDaoConfig.initIdentityScope(type);

        userBeanDaoConfig = daoConfigMap.get(UserBeanDao.class).clone();
        userBeanDaoConfig.initIdentityScope(type);

        bannerDao = new BannerDao(bannerDaoConfig, this);
        homePageDao = new HomePageDao(homePageDaoConfig, this);
        voiceBeanDao = new VoiceBeanDao(voiceBeanDaoConfig, this);
        userBeanDao = new UserBeanDao(userBeanDaoConfig, this);

        registerDao(Banner.class, bannerDao);
        registerDao(HomePage.class, homePageDao);
        registerDao(VoiceBean.class, voiceBeanDao);
        registerDao(UserBean.class, userBeanDao);
    }
    
    public void clear() {
        bannerDaoConfig.clearIdentityScope();
        homePageDaoConfig.clearIdentityScope();
        voiceBeanDaoConfig.clearIdentityScope();
        userBeanDaoConfig.clearIdentityScope();
    }

    public BannerDao getBannerDao() {
        return bannerDao;
    }

    public HomePageDao getHomePageDao() {
        return homePageDao;
    }

    public VoiceBeanDao getVoiceBeanDao() {
        return voiceBeanDao;
    }

    public UserBeanDao getUserBeanDao() {
        return userBeanDao;
    }

}
