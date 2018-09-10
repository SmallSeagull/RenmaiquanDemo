package com.dolphin.renmaicircle.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;


import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.base.BaseDialog;
import com.dolphin.renmaicircle.widget.PayPsdInputView;


/*发现、附近详情*/

public class DiscoverAndNearDetailActivity extends BaseActivity {


    private View mBack;
    private View mMore;
    private PopupWindow popupWindow;
    private View mDaShang;
    private BaseDialog mDaShangDialog;
    private EditText mDiamondCount;
    private PayPsdInputView mPassword;


    @Override
    protected int getContentView() {
        return R.layout.activity_discover_and_near_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        mBack.setOnClickListener(listener);
        mMore.setOnClickListener(listener);
        mDaShang.setOnClickListener(listener);
    }

    private void initView() {
        mBack = findView(R.id.back);
        mMore = findView(R.id.more);
        mDaShang = findView(R.id.renmai_diamond);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.more://右上角点击事件
                    if (popupWindow == null) {
                        showPopup();
                    } else {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    break;
                case R.id.share:
                    shortToast("分享");
                    popupWindow.dismiss();
                    popupWindow = null;
                    break;
                case R.id.collect:
                    shortToast("收藏");
                    popupWindow.dismiss();
                    popupWindow = null;
                    break;
                case R.id.report:
                    shortToast("举报");
                    popupWindow.dismiss();
                    popupWindow = null;
                    break;
                case R.id.renmai_diamond:
                    showDaShangDialog();
                    break;
                case R.id.cancel://取消
                    closeDaShangDialog();
                    break;
                case R.id.confirm://确定
                    break;
            }
        }
    };

    private void showDaShangDialog() {
        if(mDaShangDialog == null){
            mDaShangDialog = new BaseDialog(mContext, R.style.MyDialogStyle,R.layout.custom_dashang_dialog_layout);
        }
        mDiamondCount = ((EditText) mDaShangDialog.findViewById(R.id.count));
        mPassword = ((PayPsdInputView) mDaShangDialog.findViewById(R.id.password));
        mDaShangDialog.findViewById(R.id.cancel).setOnClickListener(listener);
        mDaShangDialog.findViewById(R.id.confirm).setOnClickListener(listener);
        mDaShangDialog.show();
    }

    private void closeDaShangDialog(){
        mPassword.cleanPsd();
        mDaShangDialog.dismiss();
    }

    private void showPopup() {
        BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_popup_detail_layout, null);
        popupWindow = BubblePopupHelper.create(mContext, bubbleLayout);
        bubbleLayout.findViewById(R.id.share).setOnClickListener(listener);
        bubbleLayout.findViewById(R.id.collect).setOnClickListener(listener);
        bubbleLayout.findViewById(R.id.report).setOnClickListener(listener);
        popupWindow.showAtLocation(mMore, Gravity.RIGHT | Gravity.TOP, 35, 165);
    }
}
