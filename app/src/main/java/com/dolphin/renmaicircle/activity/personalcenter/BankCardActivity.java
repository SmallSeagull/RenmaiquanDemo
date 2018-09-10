package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.BankCardBean;
import com.dolphin.renmaicircle.bean.MessageEvent;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.dolphin.renmaicircle.utils.SpacesItemDecoration;
import com.dolphin.renmaicircle.widget.SwipeItemLayout;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class BankCardActivity extends BaseActivity {


    private RecyclerView mRv;
    private List<BankCardBean.DataBean> datas;
    private BankCardAdapter mAdapter;
    private AppSharedPreferences instance;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (Constant.UPDATE_BANKCARD.equals(event.getMessage())) {
            Log.e("QQ", "Mycenter:event.getMessage():" + event.getMessage());
            initData();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bankcard;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        int id = ((List<BankCardBean.DataBean>) adapter.getData()).get(position).getId();
                        ContentApi.deleteBankCard(mContext, id, new StringCallback() {

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
                                if (response.code() >= 200 && response.code() < 300) {
                                    JSONObject object = JSONObject.parseObject(body);
                                    Integer code = object.getInteger("code");
                                    String msg = object.getString("msg");
                                    shortToast(msg);
                                    if (code == Constant.code_200) {
                                        mAdapter.getData().remove(position);
                                        mAdapter.notifyItemRemoved(position);
                                    } else if (code == Constant.code_210) {
                                        if (instance == null) {
                                            instance = AppSharedPreferences.getInstance(mContext);
                                        }
                                        instance.clear();
                                        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                } else {
                                    shortToast(Constant.REQUEST_FAILED);
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    private void initData() {
        ContentApi.getBankCardList(mContext, new StringCallback() {

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
                Log.e("QQ", "银行卡列表：" + body);
                if (response.code() >= 200 && response.code() < 300) {
                    BankCardBean cardBean = JSONObject.parseObject(body, BankCardBean.class);
                    int code = cardBean.getCode();
                    String msg = cardBean.getMsg();
                    if (code == Constant.code_200) {
                        List<BankCardBean.DataBean> data = cardBean.getData();
                        mAdapter.setNewData(data);
                        mAdapter.notifyDataSetChanged();

                    } else if (code == Constant.code_210) {
                        shortToast(msg);
                        if (instance == null) {
                            instance = AppSharedPreferences.getInstance(mContext);
                        }
                        instance.clear();
                        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        shortToast(msg);
                    }
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }
        });
    }

    private void initView() {
        datas = new ArrayList<>();
        mRv = ((RecyclerView) findView(R.id.rv));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        mRv.setLayoutManager(manager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration((int) DensityHelper.pt2px(mContext, 20));
        mRv.addItemDecoration(itemDecoration);
        mRv.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mAdapter = new BankCardAdapter(R.layout.bankcard_item_layout, datas);
        mRv.setAdapter(mAdapter);
    }

    private void initTitle() {
        setTopTitle("银行卡", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
        setRightBtn(true, R.drawable.add, "", listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.right_btn:
                    goActivity(AddBankCardActivity.class);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private class BankCardAdapter extends BaseQuickAdapter<BankCardBean.DataBean, BaseViewHolder> {

        private BankCardAdapter(@LayoutRes int layoutResId, @Nullable List<BankCardBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BankCardBean.DataBean item) {
            String substring = item.getBankCardNumber().substring(item.getBankCardNumber().length() - 4, item.getBankCardNumber().length());
            helper.setText(R.id.tv1, item.getBankName())
                    .setText(R.id.tv3, "****    ****    ****    " + substring);
            helper.addOnClickListener(R.id.delete);
        }
    }
}
