package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.UserBeanDao;
import com.amator.htprinter.module.UserBean;

import java.util.List;

/**
 * Created by AmatorLee on 2018/5/4.
 */
public class UserDbHandler implements DBHandler<UserBean> {

    private UserBeanDao getUserDao() {
        return HtPrinterApplcation.getsApplicationComponent().getDaoSession().getUserBeanDao();
    }

    @Override
    public void insertAll(List<UserBean> beans) {
        return;
    }

    @Override
    public void intsert(UserBean bean) {
        getUserDao().insertOrReplace(bean);
    }

    @Override
    public void updateAll(List<UserBean> beans) {
        return;
    }

    @Override
    public List<UserBean> queryAll() {
        return getUserDao().loadAll();
    }

    @Override
    public void deleteAll() {
        getUserDao().deleteAll();
    }

    public boolean checkIsRegister(UserBean user) {
        UserBean bean = getUserDao().queryBuilder().where(UserBeanDao.Properties.Username.eq(user.getUsername())).unique();
        if (bean == null) {
            return false;
        } else {
            return true;
        }
    }

    public UserBean login(String username, String password) {
        UserBean userBean = getUserDao().queryBuilder()
                .where(UserBeanDao.Properties.Username.eq(username), UserBeanDao.Properties.Password.eq(password)).unique();
        return userBean;
    }


}
