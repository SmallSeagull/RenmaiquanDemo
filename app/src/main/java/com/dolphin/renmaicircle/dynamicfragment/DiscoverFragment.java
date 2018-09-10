package com.dolphin.renmaicircle.dynamicfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseFragment;
import com.dolphin.renmaicircle.bean.FavoriteBean;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.SpacesItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dolphin on 2018/8/15.
 */

public class DiscoverFragment extends BaseFragment {

    private SmartRefreshLayout mRefresh;
    private RecyclerView mRv;
    private StaggeredGridLayoutManager mLayoutManager;
    private DiscoverAdapter mDiscoverAdapter;

    private List<FavoriteBean.DataBean> datas = new ArrayList<>();

    private int current_page = 1;
    private int total_page = -1;
    private AppSharedPreferences instance;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_discover;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRefresh = ((SmartRefreshLayout) findView(R.id.refresh));
        mRv = ((RecyclerView) findView(R.id.rv));
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(mLayoutManager);
        mDiscoverAdapter = new DiscoverAdapter(R.layout.favorite_item,datas);
        mRv.setAdapter(mDiscoverAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRv.addItemDecoration(decoration);
    }

    @Override
    protected void initData() {
        getData(Constant.FIRST_LOAD, mRefresh);
        setListener();
    }

    private void setListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(Constant.FIRST_LOAD, refreshLayout);
//                refreshLayout.finishRefresh(2000/*,false*/);
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(Constant.MORE_LOAD, refreshLayout);
//                refreshLayout.finishLoadMore(2000/*,false*/);
            }
        });
        mDiscoverAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                shortToast("第"+position+"个");
            }
        });
    }

    private void getData(String load, final RefreshLayout mRefresh) {
        switch (load) {
            case Constant.FIRST_LOAD:
                current_page = 1;
                ContentApi.getFavorite(mActivity, current_page, new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        mRefresh.autoRefresh();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response.code() == Constant.code_500) {
                            shortToast(Constant.SYSTEM_ERROR);
                        } else {
                            shortToast(Constant.REQUEST_FAILED);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Log.e("QQ", "我的收藏：" + body);
                        if (response.code() >= 200 && response.code() < 300) {
                            FavoriteBean favoriteBean = JSONObject.parseObject(body, FavoriteBean.class);
                            int code = favoriteBean.getCode();
                            String msg = favoriteBean.getMsg();
                            total_page = favoriteBean.getTotalPageCount();
                            if (code == Constant.code_200) {
                                List<FavoriteBean.DataBean> data = favoriteBean.getData();
                                if (data != null && data.size() != 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setIv_height(350 + (int) (Math.random() * (600 - 350 + 1)));//60-100随机数
                                    }
                                }
                                mDiscoverAdapter.setNewData(data);
                                mDiscoverAdapter.notifyDataSetChanged();

                            } else if (code == Constant.code_210) {//身份验证过期
                                shortToast(msg);
                                if (instance == null) {
                                    instance = AppSharedPreferences.getInstance(mActivity);
                                }
                                instance.clear();
                                Intent intent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                shortToast(msg);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mRefresh.finishRefresh();
                    }
                });
                break;
            case Constant.MORE_LOAD:
                current_page++;
                ContentApi.getFavorite(mActivity, current_page, new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response.code() == Constant.code_500) {
                            shortToast(Constant.SYSTEM_ERROR);
                        } else {
                            shortToast(Constant.REQUEST_FAILED);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Log.e("QQ", "我的收藏：" + body);
                        if (response.code() >= 200 && response.code() < 300) {
                            if (total_page != -1 && current_page > total_page) {
                                current_page--;
                                mRefresh.finishLoadMore(1500, true, false);
                                return;
                            }
                            FavoriteBean favoriteBean = JSONObject.parseObject(body, FavoriteBean.class);
                            int code = favoriteBean.getCode();
                            String msg = favoriteBean.getMsg();
                            total_page = favoriteBean.getTotalPageCount();
                            if (code == Constant.code_200) {
                                List<FavoriteBean.DataBean> data = favoriteBean.getData();
                                if (data != null && data.size() != 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setIv_height(350 + (int) (Math.random() * (600 - 350 + 1)));//60-100随机数
                                    }
                                }
                                mDiscoverAdapter.addData(data);
                                mDiscoverAdapter.notifyDataSetChanged();
                                mRefresh.finishLoadMore(0,true,true);
                            } else if (code == Constant.code_210) {//身份验证过期
                                shortToast(msg);
                                if (instance == null) {
                                    instance = AppSharedPreferences.getInstance(mActivity);
                                }
                                instance.clear();
                                Intent intent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                shortToast(msg);
                                mRefresh.finishLoadMore(true);
                            }
                        }else {
                            shortToast(Constant.REQUEST_FAILED);
                            mRefresh.finishLoadMore(true);
                        }
                    }
                });
                break;
        }

    }

    private class DiscoverAdapter extends BaseQuickAdapter<FavoriteBean.DataBean,BaseViewHolder> {

        private DiscoverAdapter(@LayoutRes int layoutResId, @Nullable List<FavoriteBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FavoriteBean.DataBean item) {
            ((SimpleDraweeView) helper.getView(R.id.iv)).setImageURI(item.getDynamicImg());
            ((ImageView)helper.getView(R.id.iv)).getLayoutParams().height = item.getIv_height();
            Glide.with(mContext)
                    .load(item.getUserHeadPortrait())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into((ImageView) helper.getView(R.id.avatar));
            helper.setText(R.id.time,new SimpleDateFormat("yyyy-MM-dd").format(new Date(item.getTime())))
                    .setText(R.id.content,item.getHeadLine())
                    .setText(R.id.nickname,item.getUserNickName());
        }
    }
}
