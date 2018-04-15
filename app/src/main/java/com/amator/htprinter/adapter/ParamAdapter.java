package com.amator.htprinter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.ui.activity.MainActivity;
import com.amator.htprinter.uitl.ViewUtil;

/**
 * Created by AmatorLee on 2018/4/15.
 */

public class ParamAdapter extends BaseAdapter{


    private TextView tv_param = null;
    private String[] paramArray = null;

    public ParamAdapter(String[] array) {
        this.paramArray = array;
    }

    @Override
    public int getCount() {
        return paramArray.length;
    }

    @Override
    public Object getItem(int position) {
        return paramArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ViewUtil.createView(R.layout.param_item);
        }
        tv_param = ViewUtil.bindView(convertView,R.id.tv_param);
        String text = "";
        if (paramArray != null && paramArray.length > position) {
            text = paramArray[position];
        }
        tv_param.setText(text);

        return convertView;
    }

}
