package com.amator.htprinter.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.amator.htprinter.adapter.viewholder.HomePageViewHolder;
import com.amator.htprinter.module.HomePage;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.refreshRecyclerView.adapter.RecyclerAdapter;

import java.util.List;

/**
 * Created by AmatorLee on 2018/4/11.
 */

public class BoxAdapter extends RecyclerAdapter<HomePage> {


    public BoxAdapter(Context context, List<HomePage> data) {
        super(context, data);
    }

    @Override
    public BaseViewHolder<HomePage> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new HomePageViewHolder(parent);
    }



}
