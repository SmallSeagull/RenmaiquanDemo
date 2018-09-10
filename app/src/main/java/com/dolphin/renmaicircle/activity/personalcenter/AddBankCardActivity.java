package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.MessageEvent;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

public class AddBankCardActivity extends BaseActivity {


    private ArrayList<String> cardItem = new ArrayList<>();
    private View mSelectBank;
    private TextView mBank;
    private EditText mName;
    private EditText mNumber;
    private TextView mConfirm;
    private View mCommit;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }


    private void setListener() {
        mConfirm.setOnClickListener(listener);
        mCommit.setOnClickListener(listener);
//        mSelectBank.setOnClickListener(listener);

    }

    private void initView() {
        mName = ((EditText) findView(R.id.name));
        mNumber = ((EditText) findView(R.id.number));
        mConfirm = ((TextView) findView(R.id.tv_confirm));
        mBank = ((TextView) findView(R.id.bank));
        mCommit = findView(R.id.commit);
//        mSelectBank = findView(R.id.rl3);

    }

    private void initTitle() {
        setTopTitle("添加银行卡", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
    }

    private AppSharedPreferences instance;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.tv_confirm://获取手机开户行
                    if (TextUtils.isEmpty(mNumber.getText().toString())) {
                        shortToast("请输入银行卡号");
                        return;
                    }
                    ContentApi.getOpeningBank(mContext, mNumber.getText().toString(), new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            mConfirm.setEnabled(false);
                        }

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
                        public void onFinish() {
                            super.onFinish();
                            mConfirm.setEnabled(true);
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            Log.e("QQ", "获取银行卡开户行：" + body);
                            if (response.code() >= 200 && response.code() < 300) {
                                JSONObject object = JSONObject.parseObject(body);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if (code == 200) {
                                    String bcBankName = object.getString("bcBankName");
                                    mBank.setText(bcBankName);
                                }
                            } else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                    break;
                case R.id.commit://提交
                    if (TextUtils.isEmpty(mBank.getText().toString())) {
                        shortToast("请先获取开户行");
                        return;
                    }
                    ContentApi.addBankCard(mContext, mNumber.getText().toString(), mBank.getText().toString(), new StringCallback() {

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            mConfirm.setEnabled(false);
                            mCommit.setEnabled(false);
                        }

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
                        public void onFinish() {
                            super.onFinish();
                            mConfirm.setEnabled(true);
                            mCommit.setEnabled(true);
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            Log.e("QQ", "添加银行卡：" + body);
                            if (response.code() >= 200 && response.code() < 300) {
                                JSONObject object = JSONObject.parseObject(body);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if (code == Constant.code_200) {
                                    EventBus.getDefault().post(new MessageEvent(Constant.UPDATE_BANKCARD));
                                    finish();
                                } else if (code == Constant.code_210) {
                                    if (instance == null) {
                                        instance = AppSharedPreferences.getInstance(mContext);
                                    }
                                    instance.clear();
                                    Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            } else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                    break;
                case R.id.rl3:
                    OptionPicker picker = new OptionPicker(mContext, new String[]{
                            "工商银行", "建设银行", "农业银行", "中国银行", "中国邮政银行"
                    });
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
                    picker.setHeight((int) DensityHelper.pt2px(mContext, 700));
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mBank.setText(item);
                        }
                    });
                    picker.show();
                    break;
            }
        }
    };
}
