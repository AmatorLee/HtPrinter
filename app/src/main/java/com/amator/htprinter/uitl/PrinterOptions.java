package com.amator.htprinter.uitl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.amator.htprinter.R;
import com.amator.htprinter.adapter.ParamAdapter;
import com.dothantech.printer.IDzPrinter;



/**
 * Created by AmatorLee on 2018/4/15.
 */

public class PrinterOptions {

    public static String[] getArrays(Context context,int array_id){
        return context.getResources().getStringArray(array_id);
    }

    // 设置打印质量
    public static int getPrintQuality(Context context, IDzPrinter.PrinterAddress address) {
        final int[] quality = {-1};
        if (address != null && address.isValid()) {
            new AlertDialog.Builder(context)
                    .setTitle(ViewUtil.getString(R.string.setprintquality))
                    .setAdapter(new ParamAdapter(getArrays(context,R.array.print_quality)), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            anInterface.dismiss();
                            quality[0] = i - 1;
                        }
                    })
                    .show();
        }
        return quality[0];
    }


    public static int getPrintType(Context context, IDzPrinter.PrinterAddress address){
        final int[] gapType = {-1};
        if (address != null && address.isValid()) {
            new AlertDialog.Builder(context)
                    .setTitle(ViewUtil.getString(R.string.setgaptype))
                    .setAdapter(new ParamAdapter(getArrays(context,R.array.gap_type)), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            anInterface.dismiss();
                            gapType[0] = i - 1;
                        }
                    })
                    .show();
        }
        return gapType[0];
    }

    // 设置打印浓度
    public static int getDensity(Context context, IDzPrinter.PrinterAddress address){
        final int[] density = {-1};
        if (address != null && address.isValid()) {
            new AlertDialog.Builder(context)
                    .setTitle(ViewUtil.getString(R.string.setprintdensity))
                    .setAdapter(new ParamAdapter(getArrays(context,R.array.print_density)), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            anInterface.dismiss();
                            density[0] = i - 1;
                        }
                    })
                    .show();
        }
        return density[0];
    }


    public static int getSpeed(Context context, IDzPrinter.PrinterAddress address){
        final int[] speed = {-1};
        if (address != null && address.isValid()) {
            new AlertDialog.Builder(context)
                    .setTitle(ViewUtil.getString(R.string.setprintspeed))
                    .setAdapter(new ParamAdapter(getArrays(context,R.array.print_speed)), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            anInterface.dismiss();
                            speed[0] = i - 1;
                        }
                    })
                    .show();
        }
        return speed[0];
    }

}
