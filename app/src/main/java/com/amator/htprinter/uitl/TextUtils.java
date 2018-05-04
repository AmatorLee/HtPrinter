package com.amator.htprinter.uitl;

import android.text.Html;
import android.widget.TextView;

import com.amator.htprinter.R;

import java.util.regex.Pattern;

/**
 * Created by AmatorLee on 2018/4/14.
 */

public class TextUtils {

    public static void setText(TextView textView, String text1, String text2) {
        StringBuilder result = new StringBuilder();
        result.append("<font color='");
        result.append(ViewUtil.getContext().getResources().getColor(R.color.tab_normal_color));
        result.append("'>");
        result.append(text1);
        result.append("</font>");
        result.append("<font color='");
        result.append(ViewUtil.getContext().getResources().getColor(R.color.text_value));
        result.append("'>");
        result.append(text2);
        result.append("</font>");
        textView.setText(Html.fromHtml(result.toString()));
    }

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        return Pattern.matches(REGEX_MOBILE, cellphone);
    }

    public static boolean checkEmail(String email) {

        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

}
