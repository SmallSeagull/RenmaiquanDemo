package com.dolphin.renmaicircle.activity.personalcenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.adapter.FragmentAdapter;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.billfragment.RechargeRecordFrag;
import com.dolphin.renmaicircle.billfragment.WithdrawalRecordFrag;
import com.dolphin.renmaicircle.billfragment.XpenseTrackerFrag;

import java.util.ArrayList;
import java.util.List;

public class MyBillActivity extends BaseActivity {


    private TabLayout mTab;
    private ViewPager mVp;

    private List<String> tabTitle;//标题
    private List<Fragment> fragments;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_bill;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        initData();
    }

    private void initData() {
        tabTitle = new ArrayList<>();
        fragments = new ArrayList<>();
        tabTitle.add("消费记录");
        tabTitle.add("充值记录");
        tabTitle.add("提现记录");
        fragments.add(new XpenseTrackerFrag());
        fragments.add(new RechargeRecordFrag());
        fragments.add(new WithdrawalRecordFrag());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),tabTitle,fragments);
        mVp.setAdapter(adapter);
        mTab.setupWithViewPager(mVp);
    }

    private void initView() {
        mTab = ((TabLayout) findView(R.id.tablayout));
        mVp = ((ViewPager) findView(R.id.view_pager));
    }

    private void initTitle() {
        setTopTitle("我的账单",true);
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

//    class BillFragmentAdapter extends FragmentStatePagerAdapter {
//        private List<String> tabTitle;
//        private List<Fragment> fragments;
//
//        public BillFragmentAdapter(FragmentManager fm, List<String> tabTitle, List<Fragment> fragments) {
//            super(fm);
//            this.tabTitle = tabTitle;
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitle.get(position);
//        }
//
//    }
}
