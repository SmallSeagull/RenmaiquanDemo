package com.dolphin.renmaicircle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.tabfragment.CircleFrag;
import com.dolphin.renmaicircle.tabfragment.DynamicFrag;
import com.dolphin.renmaicircle.tabfragment.MineFrag;
import com.dolphin.renmaicircle.tabfragment.NewsFrag;
import com.dolphin.renmaicircle.tabfragment.ShopFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Fragment mCurrentFrag;
    private Fragment fragment_circle,fragment_dynamic,fragment_news,fragment_shop,fragment_mine;
    private BottomBarLayout mBottomBarLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        setCount();
        setListener();
    }
    //设置导航未读数量
    private void setCount() {
        mBottomBarLayout.showNotify(0);
        mBottomBarLayout.setUnread(1,101);
        mBottomBarLayout.setUnread(2,101);
        mBottomBarLayout.setMsg(3,"NEW");
    }

    private void setListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                changeFragment(currentPosition);
            }
        });
    }

    private void initView() {
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.btn_container);
        fragment_circle = new CircleFrag();
        fragment_dynamic = new DynamicFrag();
        fragment_news = new NewsFrag();
        fragment_shop = new ShopFrag();
        fragment_mine = new MineFrag();
        mFragmentList.add(fragment_circle);
        mFragmentList.add(fragment_dynamic);
        mFragmentList.add(fragment_news);
        mFragmentList.add(fragment_shop);
        mFragmentList.add(fragment_mine);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frag_container, fragment_circle).commit();
        mCurrentFrag = fragment_circle;
        changeFragment(0); //默认显示第一页
    }

    private void changeFragment(int currentPosition) {
        Fragment fragment = mFragmentList.get(currentPosition);
        //判断当前显示的Fragment是不是切换的Fragment
        if(mCurrentFrag != fragment){
            //判断切换的Fragment是否已经添加过
            if(!fragment.isAdded()){
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mCurrentFrag)
                        .add(R.id.frag_container, fragment).commit();
            }else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mCurrentFrag).show(fragment).commit();
            }
            mCurrentFrag = fragment;
        }
    }
}
