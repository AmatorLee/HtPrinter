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
        dismissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(content);
        dialog.setCancelable(true);
        dialog.getProgressHelper().setBarColor(ViewUtil.getColor(R.color.text_value));
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public void succeed(Context context, String content) {
        dismissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("");
        dialog.setContentText(content);
        dialog.setCancelable(true);
        dialog.show();
    }


    public void error(Context context, String content) {
        dismissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("");
        dialog.setContentText(content);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dialogWithConfirmAndCancel(Context context, String content, SweetAlertDialog.OnSweetClickListener confirmListener,
                                           SweetAlertDialog.OnSweetClickListener cancelListener) {
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(content);
        ;
        dialog.setConfirmText(context.getString(R.string.ok));
        dialog.setConfirmClickListener(confirmListener);
        dialog.setCancelText(context.getString(R.string.cancel));
        dialog.setCancelClickListener(cancelListener);
        dialog.show();
    }

    public boolean isDialogShowing() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        }
        return false;
    }

}
