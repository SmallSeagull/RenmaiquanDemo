package com.dolphin.renmaicircle.activity.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dolphin.renmaicircle.MainActivity;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.LoginBean;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.StringUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class LoginActivity extends BaseActivity {


    private EditText mNumber;
    private EditText mPassword;
    private TextView mForget;
    private TextView mLogin;
    private TextView mRegister;
    private AppSharedPreferences instance;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        instance = AppSharedPreferences.getInstance(mContext);
        if (instance.getLoginStatus()) {
            goActivityAndFinish(MainActivity.class);
        }else {
            initView();
            setListener();
        }

    }

    private void setListener() {
        mForget.setOnClickListener(listener);
        mLogin.setOnClickListener(listener);
        mRegister.setOnClickListener(listener);
    }

    private void initView() {
        mNumber = ((EditText) findView(R.id.number));
        mPassword = ((EditText) findView(R.id.password));
        mForget = ((TextView) findView(R.id.forget_password));
        mLogin = ((TextView) findView(R.id.login));
        mRegister = ((TextView) findView(R.id.register));
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.forget_password:
                    goActivity(ForgetPasswordActivity.class);
                    break;
                case R.id.login:
                    if(!StringUtils.isPhoneNumer(mNumber.getText().toString())){
                        shortToast("请输入正确手机号");
                        return;
                    }else if(TextUtils.isEmpty(mPassword.getText().toString())){
                        shortToast("请输入密码");
                        return;
                    }
                    ContentApi.login(mContext, mNumber.getText().toString(), mPassword.getText().toString(), new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            showDialog("请稍后");
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("QQ","登录oError:"+response.code());
                            if(response.code()== Constant.code_500){
                                shortToast(Constant.SYSTEM_ERROR);
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            Log.e("QQ","登录："+body);
                            if(response.code()>=200&&response.code()<300){
                                LoginBean loginBean = JSONObject.parseObject(body, LoginBean.class);
                                int code = loginBean.getCode();
                                String msg = loginBean.getMsg();
                                if(code == Constant.code_200){
                                    String token = loginBean.getToken();
                                    LoginBean.DataBean data = loginBean.getData();
                                    String phoneNumber = data.getPhoneNumber();
                                    String nickName = data.getNickName();
                                    int integral = data.getIntegral();//影响力
                                    String headPortrait = data.getHeadPortrait();//头像
                                    int identityStatus = data.getIdentityStatus();//身份认证状态
                                    instance.set(Constant.ISLOGIN,true);
                                    instance.set(Constant.TOKEN,token);
                                    instance.set(Constant.NUMBER,phoneNumber);
                                    instance.set(Constant.NICKNAME,nickName);
                                    instance.set(Constant.INTEGRAL,integral);//影响力
                                    instance.set(Constant.AVATAR,headPortrait);
                                    instance.set(Constant.IDENTITY_STATUS,identityStatus);
                                    goActivityAndFinish(MainActivity.class);
                                }else {
                                    shortToast(msg);
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
                case R.id.register:
                    goActivity(RegisterActivity.class);
                    break;
            }
        }
    };
}
