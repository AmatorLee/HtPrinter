package com.amator.htprinter.di.component;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerFragment;
import com.amator.htprinter.di.module.FragmentModule;
import com.amator.htprinter.ui.fragment.BoxFragment;
import com.amator.htprinter.ui.fragment.MineFragment;
import com.amator.htprinter.ui.fragment.PrinterFragment;

import dagger.Component;

/**
 * Created by AmatorLee on 2018/4/6.
 */
@PerFragment
@Component(modules = FragmentModule.class,dependencies = ApplicationComponent.class)
public interface FragmentComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    SharedPreferences getSharePreference();

    void inject(BoxFragment fragment);

    void inject(PrinterFragment fragment);

    void inject(MineFragment fragment);

}
