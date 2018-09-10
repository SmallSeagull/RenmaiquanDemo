package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.AllLebelBean;
import com.dolphin.renmaicircle.bean.UserLabelBean;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.Utils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabelActivity extends BaseActivity {


    private FlowLayout mLabel_container;
    private FlowLayout mLabel_select_container;
    private AllLebelBean allLebelBean;
    private UserLabelBean userLabelBean;
    private AppSharedPreferences instance;

    private List<Integer> userSelectIds = new ArrayList<>();//用户已经有的标签id
    private View mCommit;


    @Override
    protected int getContentView() {
        return R.layout.activity_label;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        mCommit.setOnClickListener(listener);
    }

    private void initData() {
        //获取所有标签列表
        ContentApi.getAllLebel(mContext, new StringCallback() {
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
                Log.e("QQ", "所有标签：" + body);
                if (response.code() >= 200 && response.code() < 300) {
                    allLebelBean = JSONObject.parseObject(body, AllLebelBean.class);
                    int code = allLebelBean.getCode();
                    if (code == Constant.code_200) {
                        List<AllLebelBean.DataBean> data = allLebelBean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            AllLebelBean.DataBean dataBean = data.get(i);
                            TextView normalLabel = Utils.createNormalLabel(mContext, dataBean.getTagName(), dataBean.getId());
                            normalLabel.setTag(i);
                            normalLabel.setOnClickListener(allLabelListener);
                            mLabel_container.addView(normalLabel);
                        }
                    } else if (code == Constant.code_210) {
                        shortToast(allLebelBean.getMsg());
                        if (instance == null) {
                            instance = AppSharedPreferences.getInstance(mContext);
                        }
                        instance.clear();
                        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        shortToast(allLebelBean.getMsg());
                    }
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }
        });
        //获取我的标签列表
        ContentApi.getUserLebel(mContext, new StringCallback() {

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
                Log.e("QQ", "用户标签：" + body);
                if (response.code() >= 200 && response.code() < 300) {
                    userLabelBean = JSONObject.parseObject(body, UserLabelBean.class);
                    int code = userLabelBean.getCode();
                    if (code == Constant.code_200) {
                        List<UserLabelBean.DataBean> data = userLabelBean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            UserLabelBean.DataBean dataBean = data.get(i);
                            userSelectIds.add(dataBean.getLabelId());
                            TextView selectLabelLabel = Utils.createSelectLabel(mContext, dataBean.getUserLabelName(), dataBean.getLabelId());
                            selectLabelLabel.setTag(dataBean.getLabelId());
                            selectLabelLabel.setOnClickListener(userLabelListener);
                            mLabel_select_container.addView(selectLabelLabel);
                        }
                    } else if (code == Constant.code_210) {
                        shortToast(userLabelBean.getMsg());
                        if (instance == null) {
                            instance = AppSharedPreferences.getInstance(mContext);
                        }
                        instance.clear();
                        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        shortToast(allLebelBean.getMsg());
                    }
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }
        });
    }

    private void initView() {
        mLabel_container = ((FlowLayout) findView(R.id.label_container));
        mLabel_select_container = ((FlowLayout) findView(R.id.label_select_container));
        mCommit = findView(R.id.commit);
    }

    private void initTitle() {
        setTopTitle("我的标签", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.commit:
                    String s="";
                    for (int i = 0; i < userSelectIds.size(); i++) {
                        if (s == "") {
                            s = userSelectIds.get(i) + "";
                        } else {
                            s = s + "," + userSelectIds.get(i);
                        }
                    }
                    Log.e("QQ","标签ID："+s);
                    ContentApi.labelConfirm(mContext, s, new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            mCommit.setEnabled(false);
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
                        public void onFinish() {
                            super.onFinish();
                            mCommit.setEnabled(true);
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            if(response.code()>=200&&response.code()<300){
                                JSONObject object = JSONObject.parseObject(body);
                                Integer code = object.getInteger("code");
                                String msg = object.getString("msg");

                                if(code == Constant.code_200){
                                    shortToast("提交成功");
                                    finish();
                                }else if(code==Constant.code_210){
                                    shortToast(msg);
                                    if (instance == null) {
                                        instance = AppSharedPreferences.getInstance(mContext);
                                    }
                                    instance.clear();
                                    Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else {
                                    shortToast(msg);
                                }
                            }else {
                                shortToast(Constant.REQUEST_FAILED);
                            }
                        }
                    });
                        break;
            }
        }
    };
    private View.OnClickListener allLabelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            AllLebelBean.DataBean dataBean = allLebelBean.getData().get(tag);
            if (userSelectIds.size() > 2) {
                shortToast("最多只能选3个标签");
                return;
            } else if(userSelectIds.size()>0&&userSelectIds.size()<=2){
                for (int i = 0; i < userSelectIds.size(); i++) {
                    if (dataBean.getId() == userSelectIds.get(i)) {
                        shortToast("此标签已经添加");
                        return;
                    }
                }
                userSelectIds.add(dataBean.getId());
                TextView selectLabel = Utils.createSelectLabel(mContext, dataBean.getTagName(), dataBean.getId());
                selectLabel.setTag(dataBean.getId());
                selectLabel.setOnClickListener(userLabelListener);
                mLabel_select_container.addView(selectLabel);
            }else {
                userSelectIds.add(dataBean.getId());
                TextView selectLabel = Utils.createSelectLabel(mContext, dataBean.getTagName(), dataBean.getId());
                selectLabel.setTag(dataBean.getId());
                selectLabel.setOnClickListener(userLabelListener);
                mLabel_select_container.addView(selectLabel);
            }

        }
    };

    private View.OnClickListener userLabelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = (int) v.getTag();
            for (int i = 0; i < userSelectIds.size(); i++) {
                if (id == userSelectIds.get(i)) {
                    userSelectIds.remove(i);
                    mLabel_select_container.removeViewAt(i);
                }
            }
        }
    };
}
