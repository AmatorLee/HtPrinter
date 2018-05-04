package com.amator.htprinter.presenter;

import com.amator.htprinter.base.BasePresenter;
import com.amator.htprinter.base.BaseView;

/**
 * Created by AmatorLee on 2018/5/2.
 */

public interface VoicePresenter<T extends BaseView> extends BasePresenter<T> {

    public void readVoice();

    public void resolveVoice();


}
