package com.amator.htprinter.uitl;

import android.util.SparseArray;

import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.ui.fragment.BoxFragment;
import com.amator.htprinter.ui.fragment.MineFragment;
import com.amator.htprinter.ui.fragment.PrinterFragment;
import com.amator.htprinter.ui.fragment.VoiceFragment;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class FragmentFactory {


    public static final int BOX_FRAGMENT = 0;
    public static final int PRINTER_FRAGMENT = 1;
    public static final int MINE_FRAGMENT = 3;
    public static SparseArray<BaseFragment> fragmentMap = new SparseArray<>(3);
    public static final int VOICE_FRAGMENT = 2;

    public static BaseFragment create(int index) {
        BaseFragment fragment = fragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case BOX_FRAGMENT:
                    fragment = new BoxFragment();
                    break;
                case PRINTER_FRAGMENT:
                    fragment = new PrinterFragment();
                    break;
                case VOICE_FRAGMENT:
                    fragment = new VoiceFragment();
                    break;
                case MINE_FRAGMENT:
                    fragment = new MineFragment();
                    break;

            }
        }
        return fragment;
    }


}
