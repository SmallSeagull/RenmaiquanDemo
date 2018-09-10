package com.dolphin.renmaicircle.activity.personalcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

public class AuthIDCardActivity extends BaseActivity {


    private EditText mName;
    private EditText mIDNumer;
    private View mNext;

    @Override
    protected int getContentView() {
        return R.layout.activity_authidcard;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
    }

    private void initView() {
        mName = ((EditText) findView(R.id.name));
        mIDNumer = ((EditText) findView(R.id.idnumber));
        mNext = findView(R.id.next);
        mNext.setOnClickListener(listener);
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
                case R.id.next:
                    goActivity(AuthCardDetailActivity.class);
                    break;
            }
        }
    };
}
