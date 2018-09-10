package com.dolphin.renmaicircle.activity.personalcenter;

import android.os.Bundle;
import android.view.View;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

public class WaitingSolveActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_waiting_solve;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
    }

    private void initTitle() {
        setTopTitle("待处理事件",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
            }
        }
    };
}
