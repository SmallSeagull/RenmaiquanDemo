package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

import java.util.List;

public class AuthCardDetailActivity extends BaseActivity {


    private ImageView mIDCard_front;
    private ImageView mIDCard_reverse;
    private ImageView mIDCard_hand;

    private int REQUEST_CODE_FRONT = 100;
    List<Uri> mFront;

    @Override
    protected int getContentView() {
        return R.layout.activity_auth_card_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mIDCard_front.setOnClickListener(listener);
        mIDCard_reverse.setOnClickListener(listener);
        mIDCard_hand.setOnClickListener(listener);
    }

    private void initView() {
        mIDCard_front = ((ImageView) findView(R.id.idcard_front));
        mIDCard_reverse = ((ImageView) findView(R.id.idcard_reverse));
        mIDCard_hand = ((ImageView) findView(R.id.idcard_hand));
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
                case R.id.idcard_front:

                    break;
                case R.id.idcard_reverse:
                    break;
                case R.id.idcard_hand:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FRONT && resultCode == RESULT_OK) {

        }
    }

}
