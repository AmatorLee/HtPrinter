package com.amator.htprinter.uitl;

import android.util.SparseArray;

import com.amator.htprinter.base.BaseFragment;
import com.amator.htprinter.ui.fragment.BoxFragment;
import com.amator.htprinter.ui.fragment.PrinterFragment;

/**
 * Created by AmatorLee on 2018/4/10.
 */

public class FragmentFactory {


    public static final int BOX_FRAGMENT = 0;
    public static final int PRINTER_FRAGMENT = 1;
    public static SparseArray<BaseFragment> fragmentMap = new SparseArray<>(2);


    public static BaseFragment create(int index){
        BaseFragment fragment = fragmentMap.get(index);
        if (fragment == null){
            switch (index){
                case BOX_FRAGMENT:
                    fragment = new BoxFragment();
                    break;
                case PRINTER_FRAGMENT:
                    fragment = new PrinterFragment();
            }
        }
        return fragment;
    }


}
