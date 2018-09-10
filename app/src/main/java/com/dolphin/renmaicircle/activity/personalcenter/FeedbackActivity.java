package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class FeedbackActivity extends BaseActivity {


    private TextView mNum_tv;
    private EditText mSuggestion;

    private int MaxCount = 200;
    private View mCommit;

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mCommit.setOnClickListener(listener);
        mSuggestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNum_tv.setText(s.length()+"/"+MaxCount);
            }
        });
    }

    private void initView() {
        mSuggestion = ((EditText) findView(R.id.suggestion));
        mNum_tv = ((TextView) findView(R.id.num_tv));
        mCommit = findView(R.id.commit);
    }

    private void initTitle() {
        setTopTitle("意见反馈",true);
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
                case R.id.commit:
                    if(TextUtils.isEmpty(mSuggestion.getText().toString())){
                        shortToast("请输入您的意见或建议");
                        return;
                    }
                    ContentApi.publishFeedback(mContext, mSuggestion.getText().toString(), new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            mCommit.setEnabled(false);
                        }

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
                                }else if(code==Constant.code_210){
                                    if (instance == null) {
                                        instance = AppSharedPreferences.getInstance(mContext);
                                    }
                                    instance.clear();
                                    Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            mCommit.setEnabled(true);
                        }
                    });
                    break;
            }
        }
    };
}
