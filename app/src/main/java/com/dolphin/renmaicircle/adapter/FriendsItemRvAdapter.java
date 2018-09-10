package com.dolphin.renmaicircle.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Dolphin on 2018/8/17.
 */

public class FriendsItemRvAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public FriendsItemRvAdapter(@LayoutRes int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {

    }
}
