package com.dolphin.renmaicircle.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolphin.renmaicircle.MainActivity;
import com.dolphin.renmaicircle.R;

/**
 * Created by Dolphin on 2018/8/1.
 */

public class Utils {


    public static TextView createNormalLabel(Context context, String str, int id) {
        Log.e("QQ", "标签str:" + str + "   id:" + id);
        TextView tv = new TextView(context);
        tv.setText(str);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) DensityHelper.pt2px(context, 15), (int) DensityHelper.pt2px(context, 25), (int) DensityHelper.pt2px(context, 15));
        tv.setLayoutParams(params);
        tv.setPadding((int) DensityHelper.pt2px(context, 40), (int) DensityHelper.pt2px(context, 10), (int) DensityHelper.pt2px(context, 40), (int) DensityHelper.pt2px(context, 10));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityHelper.pt2px(context, 52));
        switch (id % 3) {
            case 0:
                tv.setTextColor(context.getResources().getColor(R.color.color_F6A22A));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_f6a22a_44pt));
                break;
            case 1:
                tv.setTextColor(context.getResources().getColor(R.color.secondcolor));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_1c89fb_44pt));
                break;
            case 2:
                tv.setTextColor(context.getResources().getColor(R.color.color_A25AFD));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_a25afd_44pt));
                break;
        }
        return tv;
    }

    public static TextView createSelectLabel(Context context, String str, int id) {
        TextView tv = new TextView(context);
        tv.setText(str + "  ×");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) DensityHelper.pt2px(context, 15), (int) DensityHelper.pt2px(context, 25), (int) DensityHelper.pt2px(context, 15));
        tv.setLayoutParams(params);
        tv.setPadding((int) DensityHelper.pt2px(context, 20), (int) DensityHelper.pt2px(context, 10), (int) DensityHelper.pt2px(context, 20), (int) DensityHelper.pt2px(context, 10));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityHelper.pt2px(context, 40));
        switch (id % 3) {
            case 0:
                tv.setTextColor(context.getResources().getColor(R.color.color_F6A22A));
                tv.setBackground(context.getResources().getDrawable(R.drawable.selectlabel_f6a22a_44pt));
                break;
            case 1:
                tv.setTextColor(context.getResources().getColor(R.color.secondcolor));
                tv.setBackground(context.getResources().getDrawable(R.drawable.selectlabel_1c89fb_44pt));
                break;
            case 2:
                tv.setTextColor(context.getResources().getColor(R.color.color_A25AFD));
                tv.setBackground(context.getResources().getDrawable(R.drawable.selectlabel_a25afd_44pt));
                break;
        }
        return tv;
    }

    public static TextView createInformationLabel(Context context, String str, int id) {
        TextView tv = new TextView(context);
        tv.setText(str);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, (int) DensityHelper.pt2px(context, 15), 0);
        tv.setLayoutParams(params);
        tv.setPadding((int) DensityHelper.pt2px(context, 25), (int) DensityHelper.pt2px(context, 5), (int) DensityHelper.pt2px(context, 25), (int) DensityHelper.pt2px(context, 5));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityHelper.pt2px(context, 29));
        switch (id % 3) {
            case 0:
                tv.setTextColor(context.getResources().getColor(R.color.color_F6A22A));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_f6a22a_44pt));
                break;
            case 1:
                tv.setTextColor(context.getResources().getColor(R.color.secondcolor));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_1c89fb_44pt));
                break;
            case 2:
                tv.setTextColor(context.getResources().getColor(R.color.color_A25AFD));
                tv.setBackground(context.getResources().getDrawable(R.drawable.label_a25afd_44pt));
                break;
        }
        return tv;
    }

    public static View createInformationBottomLabel(Context context, String str, int id,String type) {
        View view = LayoutInflater.from(context).inflate(R.layout.information_bottom_label_layout, null);
        TextView tv1 = ((TextView) view.findViewById(R.id.tv1));
        TextView tv2 = ((TextView) view.findViewById(R.id.tv2));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,(int) DensityHelper.pt2px(context,35),0,0);
        view.setLayoutParams(params);
        tv1.setText(str);
        tv2.setText(type);
        switch (id % 3) {
            case 0:
                tv1.setTextColor(context.getResources().getColor(R.color.color_F6A22A));
                break;
            case 1:
                tv1.setTextColor(context.getResources().getColor(R.color.secondcolor));
                break;
            case 2:
                tv1.setTextColor(context.getResources().getColor(R.color.color_A25AFD));
                break;
        }
        return view;
    }


}
