package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.StringUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class ChangePasswordActivity extends BaseActivity {


    private EditText mPassword;
    private EditText mPassword_again;
    private EditText mCode;
    private TextView mGetcode;

    private CountDownTimer tiemr;
    private View mConfirm;
    private String number;

    private AppSharedPreferences instance;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mGetcode.setOnClickListener(listener);
        mConfirm.setOnClickListener(listener);
    }

    private void initView() {
        mPassword = ((EditText) findView(R.id.password));
        mPassword_again = ((EditText) findView(R.id.password_again));
        mCode = ((EditText) findView(R.id.code));
        mGetcode = ((TextView) findView(R.id.getcode));
        mConfirm = findView(R.id.confirm);
    }

    private void initTitle() {
        setTopTitle("密码修改", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.getcode:
                    if (instance == null) {
                        instance = AppSharedPreferences.getInstance(mContext);
                    }
                    number = instance.get(Constant.NUMBER);
                    ContentApi.getSMScode(mContext, number, new StringCallback() {
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("QQ", "获取验证码：" + response.code() + "  " + response.getException());
                            if (response.code() == Constant.code_500) {
                                shortToast(Constant.SYSTEM_ERROR);
                            } else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String s = response.body();
                            if (response.code() >= 200 && response.code() < 300) {
                                JSONObject object = JSONObject.parseObject(s);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if (code == Constant.code_200) {
                                    tiemr = new CountDownTimer(60000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            //处理后的倒计时数值
                                            int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
                                            mGetcode.setEnabled(false);
                                            mGetcode.setTextColor(getResources().getColor(R.color.textcolor_hint));
                                            mGetcode.setText(String.valueOf(time) + "s后获取");
                                        }

                                        @Override
                                        public void onFinish() {
                                            mGetcode.setEnabled(true);
                                            mGetcode.setTextColor(getResources().getColor(R.color.secondcolor));
                                            mGetcode.setText("获取验证码");
                                        }
                                    }.start();
                                }
                            } else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                    break;
                case R.id.confirm:
                    if (!StringUtils.isLoginPassword(mPassword.getText().toString())) {
                        shortToast("密码只允许大小写字母和数字组合的6-16位");
                        return;
                    } else if (!TextUtils.equals(mPassword_again.getText().toString(), mPassword.getText().toString())) {
                        shortToast("两次密码不一致");
                        return;
                    } else if (TextUtils.isEmpty(mCode.getText().toString())) {
                        shortToast("请输入验证码");
                        return;
                    }
                    ContentApi.changePassword(mContext, mPassword.getText().toString(), mCode.getText().toString(), number, new StringCallback() {
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
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            if (response.code() >= 200 && response.code() < 300) {
                                JSONObject object = JSONObject.parseObject(body);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if (code == Constant.code_200) {
                                    if (instance == null) {
                                        instance = AppSharedPreferences.getInstance(mContext);
                                    }
                                    instance.clear();
                                    Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
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

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            mConfirm.setEnabled(true);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tiemr != null) {
            tiemr.cancel();
            tiemr = null;
        }
    }
}
