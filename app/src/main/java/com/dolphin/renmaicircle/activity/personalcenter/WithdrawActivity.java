package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.BankCardBean;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class WithdrawActivity extends BaseActivity {


    private View mSelect_Bank;
    private TextView mBank_tv;
    private ImageView mBank_logo;
    private EditText mMoney;
    private TextView mSurplus_money;
    private TextView mWithdraw_all;
    private int select_id;
    private List<BankCardBean.DataBean> data;


    @Override
    protected int getContentView() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initData() {
        ContentApi.getBankCardList(mContext, new StringCallback() {
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (response.code() == Constant.code_500) {
                    shortToast(Constant.SYSTEM_ERROR);
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }

            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                Log.e("QQ", "银行卡列表：" + body);
                if (response.code() >= 200 && response.code() < 300) {
                    BankCardBean cardBean = JSONObject.parseObject(body, BankCardBean.class);
                    int code = cardBean.getCode();
                    String msg = cardBean.getMsg();
                    if (code == Constant.code_200) {
                        data = cardBean.getData();
                        if(data.size()>0){
                            String bankCardNumber = data.get(0).getBankCardNumber();
                            String substring = bankCardNumber.substring(bankCardNumber.length() - 4, bankCardNumber.length());
                            mBank_tv.setText(data.get(0).getBankName()+"("+ substring+")");
                        }else {//提示用户绑定银行卡

                        }

                    } else if (code == Constant.code_210) {
                        shortToast(msg);
                        if (instance == null) {
                            instance = AppSharedPreferences.getInstance(mContext);
                        }
                        instance.clear();
                        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        shortToast(msg);
                    }
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }
        });
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
        mSelect_Bank.setOnClickListener(listener);
        mWithdraw_all.setOnClickListener(listener);
    }

    private void initView() {
        mSelect_Bank = findView(R.id.rl1);
        mBank_tv = ((TextView) findView(R.id.bank));
        mBank_logo = ((ImageView) findView(R.id.bank_logo));
        mMoney = ((EditText) findView(R.id.et_money));
        mMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
        mSurplus_money = ((TextView) findView(R.id.tv_surplus));
        mWithdraw_all = ((TextView) findView(R.id.withdraw_all));
    }

    private void initTitle() {
        setTopTitle("提现",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
    }

    private AppSharedPreferences instance;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.rl1://选择银行
                    initOptionPicker(data);
                    break;
                case R.id.withdraw_all://全部提现
                    break;

            }
        }
    };

    private void initOptionPicker(List<BankCardBean.DataBean> data) {
        List<String> stringList = new ArrayList<>();
        final List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BankCardBean.DataBean dataBean = data.get(i);
            String bankName = dataBean.getBankName();
            String bankCardNumber = dataBean.getBankCardNumber();
            String substring = bankCardNumber.substring(bankCardNumber.length() - 4, bankCardNumber.length());
            stringList.add(bankName +"("+substring+")");
            idList.add(dataBean.getId());
        }
        OptionPicker picker = new OptionPicker(mContext, stringList);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(mContext, 15));
        picker.setDividerColor(getResources().getColor(R.color.linecolor));
        picker.setTextColor(getResources().getColor(R.color.color_666666));
        picker.setCancelTextColor(getResources().getColor(R.color.color_666666));
        picker.setSubmitTextColor(getResources().getColor(R.color.secondcolor));
        picker.setTopLineColor(getResources().getColor(R.color.linecolor));
        picker.setLabelTextColor(getResources().getColor(R.color.color_666666));
        picker.setBackgroundColor(getResources().getColor(R.color.white));
        picker.setAnimationStyle(R.style.Animation_CustomPopup);
        picker.setHeight((int) DensityHelper.pt2px(mContext,700));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                mBank_tv.setText(item);
                select_id = idList.get(index);//提现银行Id
            }
        });
        picker.show();
    }
}
