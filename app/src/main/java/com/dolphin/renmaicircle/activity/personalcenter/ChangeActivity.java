package com.dolphin.renmaicircle.activity.personalcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.utils.DensityHelper;

public class ChangeActivity extends BaseActivity {


    private TextView mMoney;
    private View mRecharge;
    private View mWithdraw;
    private View mQuestion;

    @Override
    protected int getContentView() {
        return R.layout.activity_change;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        setListener();
    }

    private void setListener() {
        mRecharge.setOnClickListener(listener);
        mWithdraw.setOnClickListener(listener);
        mQuestion.setOnClickListener(listener);
    }

    private void initView() {
        mMoney = ((TextView) findView(R.id.money));
        mRecharge = findView(R.id.recharge);
        mWithdraw = findView(R.id.withdraw);
        mQuestion = findView(R.id.question);
    }

    private void initTitle() {
        setTopTitle("零钱",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
        setRightBtn(true,0,"零钱明细",listener);
        setRightBtnTvSize((int) DensityHelper.pt2px(mContext,35));
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.right_btn://账单明细
                    goActivity(MyBillActivity.class);
                    break;
                case R.id.recharge://充值
                    goActivity(RechargeActivity.class);
                    break;
                case R.id.withdraw://提现
                    goActivity(WithdrawActivity.class);
                    break;
                case R.id.question://常见问题
                    break;

            }
        }
    };
}
