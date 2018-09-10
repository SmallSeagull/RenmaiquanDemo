package com.dolphin.renmaicircle.activity.personalcenter;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.base.BaseDialog;
import com.dolphin.renmaicircle.widget.PayPsdInputView;

public class RechargeActivity extends BaseActivity {


    private RadioGroup mRadioGroup;
    private View mNext;
    private BaseDialog mPayDialog;
    private PayPsdInputView mPsdInputView;
    private EditText mMoney;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
        mNext.setOnClickListener(listener);
    }

    private void initView() {
        mMoney = ((EditText) findView(R.id.et_money));
        mMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
        mRadioGroup = ((RadioGroup) findView(R.id.radio_group));
        mRadioGroup.check(R.id.rb1);
        mNext = findView(R.id.next);
    }

    private void initTitle() {
        setTopTitle("充值",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.next:
                    showPayDialog();
                    break;
                case R.id.tv_cancel:
                    closePayDialog();
                    break;
                case R.id.tv_confirm:
                    break;
            }
        }
    };

    private void showPayDialog() {
        if(mPayDialog == null){
            mPayDialog = new BaseDialog(mContext, R.style.MyDialogStyle,R.layout.custom_paypassword_layout);
        }
        mPsdInputView = ((PayPsdInputView) mPayDialog.findViewById(R.id.password));
        mPayDialog.findViewById(R.id.tv_cancel).setOnClickListener(listener);
        mPayDialog.findViewById(R.id.tv_confirm).setOnClickListener(listener);
        mPayDialog.show();

    }

    private void closePayDialog(){
        if(mPayDialog!=null&&mPayDialog.isShowing()){
            mPsdInputView.cleanPsd();
            mPayDialog.dismiss();
        }
    }
}
