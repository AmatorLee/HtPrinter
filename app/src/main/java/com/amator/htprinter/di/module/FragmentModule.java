package com.amator.htprinter.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerActivity;
import com.amator.htprinter.di.PerApp;
import com.amator.htprinter.di.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AmatorLee on 2018/4/6.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @ContextLife("Activity")
    @PerFragment
    @Provides
    public Context provideFragmentContext(){
        return mFragment.getContext();
    }

    @Provides
    @PerFragment
    public Activity provideActivity(){
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment(){
        return mFragment;
    }

}
