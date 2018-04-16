package com.amator.htprinter.uitl;

import android.os.Bundle;

import com.dothantech.lpapi.LPAPI;

/**
 * Created by AmatorLee on 2018/4/16.
 */

public class PrinterInteractor {


    public static boolean print1dCode(String content, LPAPI api, Bundle bundle){
        // 开始绘图任务，传入参数(页面宽度, 页面高度)
        api.startJob(48, 48, 90);

        // 开始一个页面的绘制，绘制文本字符串
        // 传入参数(需要绘制的文本字符串, 绘制的文本框左上角水平位置, 绘制的文本框左上角垂直位置, 绘制的文本框水平宽度, 绘制的文本框垂直高度, 文字大小, 字体风格)
        api.drawText(content, 4, 4, 40, 20, 4);

        // 设置之后绘制的对象内容旋转180度
        api.setItemOrientation(180);

        // 绘制一维码，此一维码绘制时内容会旋转180度，
        // 传入参数(需要绘制的一维码的数据, 绘制的一维码左上角水平位置, 绘制的一维码左上角垂直位置, 绘制的一维码水平宽度, 绘制的一维码垂直高度)
        api.draw1DBarcode(content, LPAPI.BarcodeType.AUTO, 4, 25, 40, 15, 3);

        // 结束绘图任务提交打印
        return api.commitJobWithParam(bundle);
    }

    public static boolean print2DCode(String Content,LPAPI api,Bundle bundle){
        // 开始绘图任务，传入参数(页面宽度, 页面高度)
        api.startJob(48, 50, 0);

        // 开始一个页面的绘制，绘制二维码
        // 传入参数(需要绘制的二维码的数据, 绘制的二维码左上角水平位置, 绘制的二维码左上角垂直位置, 绘制的二维码的宽度(宽高相同))
        api.draw2DQRCode(Content, 9, 10, 30);

        // 结束绘图任务提交打印
        return api.commitJobWithParam(bundle);
    }


    public static boolean printText(String content,LPAPI api,Bundle bundle){
        // 开始绘图任务，传入参数(页面宽度, 页面高度)
        api.startJob(48, 50, 0);

        // 开始一个页面的绘制，绘制文本字符串
        // 传入参数(需要绘制的文本字符串, 绘制的文本框左上角水平位置, 绘制的文本框左上角垂直位置, 绘制的文本框水平宽度, 绘制的文本框垂直高度, 文字大小, 字体风格)
        api.drawText(content, 4, 5, 40, 40, 4);

        // 结束绘图任务提交打印
        return api.commitJobWithParam(bundle);
    }


}
