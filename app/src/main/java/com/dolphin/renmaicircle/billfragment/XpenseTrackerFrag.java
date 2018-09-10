package com.dolphin.renmaicircle.billfragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseFragment;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.dolphin.renmaicircle.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dolphin on 2018/8/4.
 * 消费记录
 */

public class XpenseTrackerFrag extends BaseFragment {
    private RecyclerView mRv;
    private XpenTrackerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.bill_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRv = ((RecyclerView) findView(R.id.rv));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mActivity);
        mRv.setLayoutManager(manager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration((int) DensityHelper.pt2px(mActivity, 20));
        mRv.addItemDecoration(itemDecoration);

    }

    @Override
    protected void initData() {
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add(i);
        }
        mAdapter = new XpenTrackerAdapter(R.layout.bill_xiaofei_item_layout, datas);
        mRv.setAdapter(mAdapter);
    }


    class XpenTrackerAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {

        public XpenTrackerAdapter(@LayoutRes int layoutResId, @Nullable List<Integer> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {

        }
    }
}
