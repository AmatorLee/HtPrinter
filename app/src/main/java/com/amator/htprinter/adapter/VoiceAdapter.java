package com.amator.htprinter.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.amator.htprinter.adapter.viewholder.VoiceViewHolder;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.refreshRecyclerView.adapter.RecyclerAdapter;
import com.amator.htprinter.ui.activity.MainActivity;

import java.util.List;

/**
 * Created by AmatorLee on 2018/5/3.
 */
public class VoiceAdapter extends RecyclerAdapter<VoiceBean> {

    private MainActivity mActivity;

    public VoiceAdapter(Context context, List<VoiceBean> data) {
        super(context, data);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public BaseViewHolder<VoiceBean> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new VoiceViewHolder(mActivity,parent);
    }
}
