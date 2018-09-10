package com.dolphin.renmaicircle.dynamicfragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.DiscoverAndNearDetailActivity;
import com.dolphin.renmaicircle.adapter.FriendsItemRvAdapter;
import com.dolphin.renmaicircle.base.BaseFragment;
import com.dolphin.renmaicircle.bean.BankCardBean;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.dolphin.renmaicircle.utils.SpacesItemDecoration;
import com.dolphin.renmaicircle.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dolphin on 2018/8/15.
 */

public class FriendsFragment extends BaseFragment {
    private RecyclerView mRv;
    private List<Integer> datas = new ArrayList<>();
    private FriendsAdapter mFriendsAdapter;
    private boolean isCollect;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_friends;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRv = ((RecyclerView) findView(R.id.rv));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mActivity);
        mRv.setLayoutManager(manager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration((int) DensityHelper.pt2px(mActivity, 1));
        mRv.addItemDecoration(itemDecoration);
        mFriendsAdapter = new FriendsAdapter(R.layout.dynamic_item_layout_1,datas);
        mRv.setAdapter(mFriendsAdapter);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            datas.add(i+1);
        }
        mFriendsAdapter.notifyDataSetChanged();
        setListener();
    }

    private void setListener() {
        //子view点击事件
        mFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.collect:
                        shortToast("收藏");
                        isCollect = !isCollect;
                        view.findViewById(R.id.collect_iv).setSelected(isCollect);
                        break;
                    case R.id.shang:
                        shortToast("打赏");
                        break;
                    case R.id.praise:
                        shortToast("点赞");
                        break;
                    case R.id.comment:
                        shortToast("评论");
                        break;
                }
            }
        });
        //item点击事件
        mFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goActivity(DiscoverAndNearDetailActivity.class);
            }
        });

    }

    private class FriendsAdapter extends BaseQuickAdapter<Integer, BaseViewHolder>{
        List<Integer> integers = new ArrayList<>();

        public FriendsAdapter(@LayoutRes int layoutResId, @Nullable List<Integer> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
            if(item%2==0){
                RecyclerView rv = (RecyclerView) helper.itemView.findViewById(R.id.rv);
                LinearLayoutManager ms= new LinearLayoutManager(mActivity);
                ms.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv.setLayoutManager(ms);
                integers.clear();
                for (int i = 0; i < 4; i++) {
                    integers.add(i);
                }
                FriendsItemRvAdapter adapter = new FriendsItemRvAdapter(R.layout.friends_item_imagelayout,integers);
                rv.setAdapter(adapter);
            }else {
                LinearLayout label_container = (LinearLayout) helper.itemView.findViewById(R.id.tv_container);
                label_container.removeAllViews();
                for (int i = 0; i < 3; i++) {
                    TextView label = Utils.createInformationLabel(mContext, "随遇而安", i);
                    label_container.addView(label);
                }
            }

            helper.addOnClickListener(R.id.collect)
                    .addOnClickListener(R.id.shang)
                    .addOnClickListener(R.id.praise)
                    .addOnClickListener(R.id.comment);
        }
    }
}
