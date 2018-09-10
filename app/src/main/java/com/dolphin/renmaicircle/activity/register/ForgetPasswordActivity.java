package com.dolphin.renmaicircle.activity.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.StatusBarUtil;
import com.dolphin.renmaicircle.utils.StringUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class ForgetPasswordActivity extends BaseActivity {


    private LinearLayout mBack;
    private EditText mNumber;
    private EditText mCode;
    private TextView mGetcode;
    private EditText mPassword;
    private EditText mConfirm_Password;

    private CountDownTimer tiemr;
    private TextView mConfirm;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        mBack.setOnClickListener(listener);
        mGetcode.setOnClickListener(listener);
        mConfirm.setOnClickListener(listener);
    }

    private void initView() {
        mBack = ((LinearLayout) findView(R.id.back));
        mNumber = ((EditText) findView(R.id.number));
        mCode = ((EditText) findView(R.id.code));
        mGetcode = ((TextView) findView(R.id.getcode));
        mPassword = ((EditText) findView(R.id.password));
        mConfirm_Password = ((EditText) findView(R.id.confirm_password));
        mConfirm = ((TextView) findView(R.id.confirm));
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.getcode:
                    if(!StringUtils.isPhoneNumer(mNumber.getText().toString())){
                        shortToast("请输入正确手机号");
                        return;
                    }
                    ContentApi.getSMScode(mContext, mNumber.getText().toString(), new StringCallback() {
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("QQ","获取验证码："+response.code()+"  "+response.getException());
                            if(response.code()== Constant.code_500){
                                shortToast(Constant.SYSTEM_ERROR);
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String s = response.body();
                            if(response.code()>=200&&response.code()<300){
                                JSONObject object = JSONObject.parseObject(s);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if(code == Constant.code_200){
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
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                    break;
                case R.id.confirm://确认修改
                    if(TextUtils.isEmpty(mCode.getText().toString())){
                        shortToast("请输入验证码");
                        return;
                    }else if(!StringUtils.isLoginPassword(mPassword.getText().toString())){
                        shortToast("密码只允许大小写字母和数字组合的6-16位");
                        return;
                    }else if(!TextUtils.equals(mConfirm_Password.getText().toString(),mPassword.getText().toString())){
                        shortToast("两次密码不一致");
                        return;
                    }
                    ContentApi.findPassword(mContext, mNumber.getText().toString(), mCode.getText().toString(), mPassword.getText().toString(), new StringCallback() {

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if(response.code()== Constant.code_500){
                                shortToast(Constant.SYSTEM_ERROR);
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            if(response.code()>=200&&response.code()<300){
                                JSONObject object = JSONObject.parseObject(body);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");
                                shortToast(msg);
                                if(code == Constant.code_200){
                                    finish();
                                }
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tiemr !=null){
            tiemr.cancel();
            tiemr = null;
        }
    }
}
