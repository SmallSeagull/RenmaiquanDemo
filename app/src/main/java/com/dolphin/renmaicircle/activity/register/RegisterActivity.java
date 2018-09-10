package com.dolphin.renmaicircle.activity.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RegisterActivity extends BaseActivity {


    private ImageView mAgree;
    private boolean isSelect;
    private LinearLayout mBack;
    private EditText mNumber;
    private EditText mCode;
    private TextView mGetcode;
    private EditText mPassword;
    private EditText mConfirm_Password;
    private EditText mInvite_code;
    private TextView mRegister;

    private CountDownTimer tiemr;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        setListener();
    }

    private void setListener() {
        mBack.setOnClickListener(listener);
        mGetcode.setOnClickListener(listener);
        mAgree.setOnClickListener(listener);
        mRegister.setOnClickListener(listener);
    }

    private void initView() {
        mBack = ((LinearLayout) findView(R.id.back));
        mNumber = ((EditText) findView(R.id.number));
        mCode = ((EditText) findView(R.id.code));
        mGetcode = ((TextView) findView(R.id.getcode));
        mPassword = ((EditText) findView(R.id.password));
        mConfirm_Password = ((EditText) findView(R.id.confirm_password));
        mInvite_code = ((EditText) findView(R.id.invite_code));
        mAgree = ((ImageView) findView(R.id.agree));
        mRegister = ((TextView) findView(R.id.register));
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.agree:
                    isSelect = !isSelect;
                    mAgree.setSelected(isSelect);
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
                            Log.e("QQ","获取验证码："+s);
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
                case R.id.register://注册
                    if(TextUtils.isEmpty(mCode.getText().toString())){
                        shortToast("请输入验证码");
                        return;
                    }else if(!StringUtils.isLoginPassword(mPassword.getText().toString())){
                        shortToast("密码只允许大小写字母和数字组合的6-16位");
                        return;
                    }else if(!TextUtils.equals(mConfirm_Password.getText().toString(),mPassword.getText().toString())){
                        shortToast("两次密码不一致");
                        return;
                    }else if(!mAgree.isSelected()){
                        shortToast("请先同意用户服务条款与用户隐私协议");
                        return;
                    }
                    ContentApi.register(mContext, mNumber.getText().toString(), mCode.getText().toString(),
                            mPassword.getText().toString(), mInvite_code.getText().toString(), new StringCallback() {
                                @Override
                                public void onStart(Request<String, ? extends Request> request) {
                                    super.onStart(request);
                                    showDialog("提交中");
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    if(response.code()==Constant.code_500){
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

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    closeDialog();
                                }
                            });
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tiemr!=null){
            tiemr.cancel();
            tiemr = null;
        }
    }
}
