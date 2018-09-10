package com.dolphin.renmaicircle.tabfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.SearchActivity;
import com.dolphin.renmaicircle.adapter.FragmentAdapter;
import com.dolphin.renmaicircle.base.BaseFragment;
import com.dolphin.renmaicircle.billfragment.RechargeRecordFrag;
import com.dolphin.renmaicircle.billfragment.WithdrawalRecordFrag;
import com.dolphin.renmaicircle.billfragment.XpenseTrackerFrag;
import com.dolphin.renmaicircle.dynamicfragment.DiscoverFragment;
import com.dolphin.renmaicircle.dynamicfragment.FriendsFragment;
import com.dolphin.renmaicircle.dynamicfragment.NearFragment;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.dolphin.renmaicircle.utils.MyLoader;
import com.example.zhouwei.library.CustomPopWindow;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dolphin on 2018/7/24.
 */

public class DynamicFrag extends BaseFragment {
    private TabLayout mTab;
    private ViewPager mVp;
    private List<String> tabTitle;//标题
    List<String> path;
    private List<Fragment> fragments;
    private Banner mBanner;
    private ImageView mSearch;
    private ImageView mEditor;
    private CustomPopWindow mCustomPopWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_dynamic;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTab = ((TabLayout) findView(R.id.tablayout));
        mBanner = ((Banner) findView(R.id.banner));
        mSearch = ((ImageView) findView(R.id.search));
        mEditor = ((ImageView) findView(R.id.edit));
        mVp = ((ViewPager) findView(R.id.viewpager));
    }

    @Override
    protected void initData() {
        tabTitle = new ArrayList<>();
        fragments = new ArrayList<>();
        path = new ArrayList<>();
        tabTitle.add("好友");
        tabTitle.add("发现");
        tabTitle.add("附近");
        path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534252620591&di=420b645738a9ce8165e1dc1d71e017bc&imgtype=0&src=http%3A%2F%2Fimg2015.zdface.com%2F20180718%2F143361dc499e766687d9c9dc2cec3f03.jpg");
        path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534252620589&di=de1d25a5f257862e1e41e9860a58748c&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2Fc48dfe3bb2cbbedbbd194bec444ae3ec23bf837b.jpg");
        path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534252620588&di=d9deec9c4502323520cd32b7fa9fe367&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201609%2F06%2F20160906172414_Ntn4Q.jpeg");
        fragments.add(new FriendsFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new NearFragment());
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(),tabTitle,fragments);
        mVp.setAdapter(adapter);
        mTab.setupWithViewPager(mVp);
        mBanner.setImages(path).setImageLoader(new MyLoader()).setOnBannerListener(bannerListener).start();
        setListener();
    }

    private void setListener() {
        mSearch.setOnClickListener(listener);
        mEditor.setOnClickListener(listener);
    }

    private OnBannerListener bannerListener = new OnBannerListener() {
        @Override
        public void OnBannerClick(int position) {
            shortToast(position+"");
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search:
                    goActivity(SearchActivity.class);
                    break;
                case R.id.edit:
                    showPopup();
                    break;
            }
        }
    };

    private void showPopup() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.custom_popup_dynamic, null);
        handleLogic(view);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mActivity)
                .setView(view)
                .size((int)DensityHelper.pt2px(mActivity,316), (int)DensityHelper.pt2px(mActivity,316))
                .setOutsideTouchable(true)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .create()
                .showAtLocation(mEditor, Gravity.RIGHT|Gravity.TOP,(int) DensityHelper.pt2px(mActivity,35),(int) DensityHelper.pt2px(mActivity,190));
    }

    private void handleLogic(View view) {
        View.OnClickListener listeners = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.tv1:
                    shortToast("发动态");
                        break;
                    case R.id.tv2:
                        shortToast("写文章");
                        break;
                }
            }
        };
        view.findViewById(R.id.tv1).setOnClickListener(listeners);
        view.findViewById(R.id.tv2).setOnClickListener(listeners);
    }
}
