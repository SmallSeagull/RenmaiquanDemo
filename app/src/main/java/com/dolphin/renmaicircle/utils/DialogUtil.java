package com.dolphin.renmaicircle.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by Dolphin on 2018/7/23.
 */

public class DialogUtil {
    public static ZLoadingDialog createLoadingDialog(Context context, String msg){
        ZLoadingDialog loadingDialog = new ZLoadingDialog(context);
        loadingDialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)//设置类型
                .setLoadingColor(Color.WHITE)//颜色
                .setHintText(msg)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .show();
        return loadingDialog;
    }

    public static void closeLoadingDialog(ZLoadingDialog loadingDialog) {
        if (loadingDialog != null ) {
            loadingDialog.dismiss();
        }
    }
}
