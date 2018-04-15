package com.amator.htprinter.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.amator.htprinter.adapter.viewholder.PrinterViewHolder;
import com.amator.htprinter.refreshRecyclerView.adapter.Action;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.refreshRecyclerView.adapter.RecyclerAdapter;
import com.amator.htprinter.ui.activity.PrinterListActivity;
import com.dothantech.printer.IDzPrinter;

/**
 * Created by AmatorLee on 2018/4/14.
 */

public class PrinterAdapter extends RecyclerAdapter<IDzPrinter.PrinterAddress> {

    private PrinterListActivity activity;

    public PrinterAdapter(Context context) {
        super(context);
        activity = (PrinterListActivity) context;
    }

    @Override
    public BaseViewHolder<IDzPrinter.PrinterAddress> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new PrinterViewHolder(activity,parent);
    }
}
