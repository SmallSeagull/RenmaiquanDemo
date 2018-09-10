package com.dolphin.renmaicircle.activity.personalcenter;

import android.os.Bundle;
import android.view.View;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

public class BillDetailActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_bill_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
    }

    private void initTitle() {
        setTopTitle("零钱明细",true);
        setLeftBtn(true,R.drawable.ic_persona_editor_cha,"   ",listener);
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
