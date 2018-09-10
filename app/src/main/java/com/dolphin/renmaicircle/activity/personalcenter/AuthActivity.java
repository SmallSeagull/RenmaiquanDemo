package com.dolphin.renmaicircle.activity.personalcenter;

import android.os.Bundle;
import android.view.View;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

public class AuthActivity extends BaseActivity {


    private View mAuth_IDCard;

    @Override
    protected int getContentView() {
        return R.layout.activity_auth;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mAuth_IDCard.setOnClickListener(listener);
    }

    private void initView() {
        mAuth_IDCard = findView(R.id.auth_idcard);
    }

    private void initTitle() {
        setTopTitle("认证中心",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.auth_idcard:
                    goActivity(AuthIDCardActivity.class);
                    break;
            }
        }
    };
}
