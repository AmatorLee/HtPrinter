package com.amator.htprinter.uitl;

import android.text.Html;
import android.widget.TextView;

import com.amator.htprinter.R;

/**
 * Created by AmatorLee on 2018/4/14.
 */

public class TextUtils {

    public static void setText(TextView textView, String text1, String text2) {
        StringBuilder result = new StringBuilder();
        result.append("<font color='");
        result.append(ViewUtil.getContext().getColor(R.color.tab_normal_color));
        result.append("'>");
        result.append(text1);
        result.append("</font>");
        result.append("<font color='");
        result.append(ViewUtil.getContext().getColor(R.color.text_value));
        result.append("'>");
        result.append(text2);
        result.append("</font>");
        textView.setText(Html.fromHtml(result.toString()));
    }

}
