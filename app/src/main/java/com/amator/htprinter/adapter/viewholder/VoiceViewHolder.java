package com.amator.htprinter.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.amator.htprinter.R;
import com.amator.htprinter.db.VoiceHandler;
import com.amator.htprinter.db.factory.DbHandlerFactory;
import com.amator.htprinter.dialog.ActionSheetDialog;
import com.amator.htprinter.module.VoiceBean;
import com.amator.htprinter.refreshRecyclerView.adapter.BaseViewHolder;
import com.amator.htprinter.ui.activity.MainActivity;
import com.amator.htprinter.uitl.PrinterType;

import java.lang.ref.WeakReference;

/**
 * Created by AmatorLee on 2018/5/3.
 */
public class VoiceViewHolder extends BaseViewHolder<VoiceBean> {

    private TextView mTxtVoiceName;
    private WeakReference<MainActivity> mActivityWeakReference = null;

    public VoiceViewHolder(MainActivity activity, ViewGroup itemView) {
        super(itemView, R.layout.layout_voice_item);
        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onInitializeView() {
        mTxtVoiceName = findViewById(R.id.txt_voice_item_name);
    }

    @Override
    public void setData(VoiceBean data) {
        mTxtVoiceName.setText(data.getMessage());
    }

    @Override
    public void onItemViewClick(VoiceBean data) {
        new ActionSheetDialog(itemView.getContext())
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("打印一维码", ActionSheetDialog.SheetItemColor.Blue, new MyListener(data))
                .addSheetItem("打印二维码", ActionSheetDialog.SheetItemColor.Blue, new MyListener(data))
                .addSheetItem("打印详细信息", ActionSheetDialog.SheetItemColor.Blue, new MyListener(data))
                .addSheetItem("删除", ActionSheetDialog.SheetItemColor.Blue, new MyListener(data))
                .show();
    }

    private class MyListener implements ActionSheetDialog.OnSheetItemClickListener {

        private VoiceBean mVoiceBean;

        public MyListener(VoiceBean data) {
            mVoiceBean = data;
        }

        @Override
        public void onClick(int which) {
            try {
                PrinterType type = null;
                switch (which) {
                    case 1:
                        type = PrinterType.ONE;
                        break;
                    case 2:
                        type = PrinterType.TWO;
                        break;
                    case 3:
                        type = PrinterType.TEXT;
                        break;
                    case 4:
                        ((VoiceHandler) DbHandlerFactory.create(DbHandlerFactory.VOICE)).delete(mVoiceBean);
                        if (mActivityWeakReference.get() != null) {
                            mActivityWeakReference.get().notifyVoice();
                        }
                        return;
                }
                if (mActivityWeakReference.get() != null) {
                    mActivityWeakReference.get().printVoiceMessage(mVoiceBean, type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onClick(int which) {
//        JSONObject res = new JSONObject();
//        try {
//            res.put("url", homePahe.getLink());
//            PrinterType type = null;
//            switch (which) {
//                case 1:
//                    type = PrinterType.ONE;
//                    break;
//                case 2:
//                    res.put("title", homePahe.getTitle());
//                    type = PrinterType.TWO;
//                    break;
//                case 3:
//                    res.put("title", homePahe.getTitle());
//                    type = PrinterType.TEXT;
//                    break;
//            }
//            res.put("type", type.toString());
//            EventBus.getDefault().post(res.toString());
//    }


}
