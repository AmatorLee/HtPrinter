package com.amator.htprinter.uitl;

import android.content.Context;

import com.amator.htprinter.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AmatorLee on 2018/4/14.
 */

public class DialogUtil {

    private static DialogUtil dialogUtil = new DialogUtil();
    private SweetAlertDialog dialog = null;

    private DialogUtil() {

    }

    public static DialogUtil newInstance() {
        return dialogUtil;
    }

    public void normal(Context context, String content) {
        dismissDialog(dialog);
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle("");
        dialog.setContentText(content);
        dialog.setCancelable(true);
        dialog.getProgressHelper().setBarColor(context.getColor(R.color.text_value));
        dialog.show();
    }

    public void dismissDialog(SweetAlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public void succeed(Context context, String content) {
        dismissDialog(dialog);
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitle("");
        dialog.setContentText(content);
        dialog.setCancelable(true);
        dialog.show();
    }


    public void error(Context context, String content) {
        dismissDialog(dialog);
        dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitle("");
        dialog.setContentText(content);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dialogWithConfirmAndCancel(Context context, String content, SweetAlertDialog.OnSweetClickListener confirmListener,
                                           SweetAlertDialog.OnSweetClickListener cancelListener) {
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText(content);
        dialog.setTitle("");
        dialog.setConfirmText(context.getString(R.string.ok));
        dialog.setConfirmClickListener(confirmListener);
        dialog.setCancelText(context.getString(R.string.cancel));
        dialog.setCancelClickListener(cancelListener);
        dialog.show();
    }


}
