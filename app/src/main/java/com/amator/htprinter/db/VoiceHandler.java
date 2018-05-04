package com.amator.htprinter.db;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.dao.VoiceBeanDao;
import com.amator.htprinter.module.VoiceBean;

import java.util.List;

/**
 * Created by AmatorLee on 2018/5/4.
 */
public class VoiceHandler implements DBHandler<VoiceBean> {


    public VoiceBeanDao getVoiceBeanDao() {
        return HtPrinterApplcation.getsApplicationComponent().getDaoSession()
                .getVoiceBeanDao();
    }

    @Override
    public void insertAll(List<VoiceBean> beans) {
        getVoiceBeanDao().insertOrReplaceInTx(beans);
    }

    @Override
    public void intsert(VoiceBean bean) {
        getVoiceBeanDao().insertOrReplace(bean);
    }

    @Override
    public void updateAll(List<VoiceBean> beans) {
        getVoiceBeanDao().updateInTx(beans);
    }

    @Override
    public List<VoiceBean> queryAll() {
        return getVoiceBeanDao().loadAll();
    }

    @Override
    public void deleteAll() {
        getVoiceBeanDao().deleteAll();
    }

    public void delete(VoiceBean bean){
        getVoiceBeanDao().delete(bean);
    }

}
