package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.base.BaseDialog;
import com.dolphin.renmaicircle.bean.InformationBean;
import com.dolphin.renmaicircle.bean.MessageEvent;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.Utils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class PersonInformationActivity extends BaseActivity {


    private TextView mEdit_Data;
    private ImageView mAvatar;
    private TextView mName;
    private ImageView mGender_iv;
    private LinearLayout mTopLabel_container;
    private TextView mLocation;
    private ImageView mEdit_company;
    private TextView mCompany;
    private TextView mPosition;
    private TextView mSchool;
    private TextView mEducation;
    private ImageView mEdit_school;
    private LinearLayout mBottomLabel_container;
    private TextView mInfluence;
    private AppSharedPreferences instance;

    private InformationBean.DataBean data;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (Constant.UPDATE_AVATAR_NAME.equals(event.getMessage())) {
            Log.e("QQ", "Mycenter:event.getMessage():" + event.getMessage());
            initData();
//            changeAvatarAndName();
        }
    }

    private void changeAvatarAndName() {
        if (instance == null) {
            instance = AppSharedPreferences.getInstance(mContext);
        }
        Glide.with(mContext).
                load(instance.get(Constant.AVATAR))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatar);
        mName.setText(instance.get(Constant.NICKNAME));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_person_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initData() {
        ContentApi.viewInformation(mContext, 0, -1, new StringCallback() {

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("QQ", "获取个人资料onError：" + response.getException());
                if (response.code() == Constant.code_500) {
                    shortToast(Constant.SYSTEM_ERROR);
                } else {
                    shortToast(Constant.REQUEST_FAILED);
                }
            }

            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                Log.e("QQ", "个人资料：" + body);
                if (response.code() >= 200 && response.code() < 300) {
                    InformationBean informationBean = JSONObject.parseObject(body, InformationBean.class);
                    int code = informationBean.getCode();
                    String msg = informationBean.getMsg();
                    if (code == Constant.code_200) {
                        data = informationBean.getData();
                        setData(data);
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

    private void setData(InformationBean.DataBean data) {
        Glide.with(mContext)
                .load(data.getHeadPortrait())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatar);
        switch (data.getSex()) {
            case 0:
                mGender_iv.setBackground(null);
                break;
            case 1:
                mGender_iv.setBackgroundResource(R.drawable.ic_persona_user_male);
                break;
            case 2:
                mGender_iv.setBackgroundResource(R.drawable.ic_persona_user_female);
                break;
        }
        mName.setText(data.getNickName());
        mInfluence.setText("影响力：" + data.getIntegral());
        List<InformationBean.DataBean.LabelsBean> labels = data.getLabels();
        mTopLabel_container.removeAllViews();//先清除，避免多次添加
        mBottomLabel_container.removeAllViews();
        if (labels != null && labels.size() != 0) {
            for (int i = 0; i < labels.size(); i++) {
                TextView informationLabel = Utils.createInformationLabel(mContext, labels.get(i).getUserLabelName(), labels.get(i).getLabelId());
                //添加上面的标签
                mTopLabel_container.addView(informationLabel);
                View view = Utils.createInformationBottomLabel(mContext, labels.get(i).getUserLabelName(), labels.get(i).getLabelId(), "认证");
                View tv2 = view.findViewById(R.id.tv2);
                tv2.setTag(labels.get(i).getLabelId());
                tv2.setOnClickListener(labelListener);
                //添加下面的标签
                mBottomLabel_container.addView(view);
            }
        }
        mLocation.setText(data.getRegion());
        mCompany.setText(data.getCompany());
        mPosition.setText(data.getPosition());
        mSchool.setText(data.getSchool());
        mEducation.setText(data.getEducation());
    }


    private void setListener() {
        mEdit_Data.setOnClickListener(listener);
        mEdit_company.setOnClickListener(listener);
        mEdit_school.setOnClickListener(listener);
    }

    private void initView() {
        mAvatar = ((ImageView) findView(R.id.avatar));
        mName = ((TextView) findView(R.id.name));
        mGender_iv = ((ImageView) findView(R.id.gender_iv));
        mTopLabel_container = ((LinearLayout) findView(R.id.tv_container));
        mInfluence = ((TextView) findView(R.id.influence));
        mLocation = ((TextView) findView(R.id.location));
        mEdit_Data = ((TextView) findView(R.id.edit_data));
        mCompany = ((TextView) findView(R.id.company));//公司
        mPosition = ((TextView) findView(R.id.position));//职位
        mEdit_company = ((ImageView) findView(R.id.edit_company));
        mSchool = ((TextView) findView(R.id.school));
        mEducation = ((TextView) findView(R.id.education));//学历
        mEdit_school = ((ImageView) findView(R.id.edit_school));
        mBottomLabel_container = ((LinearLayout) findView(R.id.label_container));


    }

    private void initTitle() {
        setTopTitle("用户个人信息", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
        setRightBtn(true, R.drawable.ic_persona_user_bj, "编辑", listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.right_btn://编辑
                    if ("编辑".equals(wRight_tv.getText().toString())) {
                        wRight_iv.setVisibility(View.GONE);
                        wRight_tv.setText("完成");
                        mEdit_Data.setVisibility(View.VISIBLE);
                        mEdit_company.setVisibility(View.VISIBLE);
                        mEdit_school.setVisibility(View.VISIBLE);
                    } else {
                        updateInformation();
                    }
                    break;
                case R.id.edit_data:
                    wRight_tv.setText("编辑");
                    wRight_iv.setVisibility(View.VISIBLE);
                    mEdit_Data.setVisibility(View.GONE);
                    mEdit_company.setVisibility(View.GONE);
                    mEdit_school.setVisibility(View.GONE);
                    Bundle b = new Bundle();
                    b.putSerializable("data",data);
                    goActivity(EditBaseDataActivity.class,b);
                    break;
                case R.id.edit_company:
                    showEditCompanyDialog();
                    break;
                case R.id.edit_school:
                    showEditSchoolDialog();
                    break;

            }
        }
    };

    private void updateInformation() {
        if(TextUtils.isEmpty(mCompany.getText().toString())){
            shortToast("请填写公司名称");
            return;
        }else if(TextUtils.isEmpty(mEducation.getText().toString())){
            shortToast("请填写职位");
            return;
        }else if(TextUtils.isEmpty(mSchool.getText().toString())){
            shortToast("请填写毕业学校");
            return;
        }else if(TextUtils.isEmpty(mEducation.getText().toString())){
            shortToast("请填写学历");
            return;
        }
        ContentApi.updateInformation(mContext, null, null, -1, null, null, null, null, mCompany.getText().toString(),
                mPosition.getText().toString(), mSchool.getText().toString(), mEducation.getText().toString(),
                new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        mRight_btn.setEnabled(false);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mRight_btn.setEnabled(true);
                        Log.e("QQ","修改个人信息：onError:"+response.code()+"\r\n"+response.getException());
                        if (response.code() == Constant.code_500) {
                            shortToast(Constant.SYSTEM_ERROR);
                        } else {
                            shortToast(Constant.REQUEST_FAILED);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Log.e("QQ","修改资料："+body);
                        if(response.code()>=200&&response.code()<300){
                            JSONObject object = JSONObject.parseObject(body);
                            Integer code = object.getInteger("code");
                            String msg = object.getString("msg");
                            shortToast(msg);
                            if(code == Constant.code_200){
                                finish();

                            }else if(code == Constant.code_210){
                                if (instance == null) {
                                    instance = AppSharedPreferences.getInstance(mContext);
                                }
                                instance.clear();
                                Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                mRight_btn.setEnabled(true);
                            }
                        }else {
                            mRight_btn.setEnabled(true);
                            shortToast(Constant.REQUEST_FAILED);
                        }
                    }
                });
    }

    private void showEditSchoolDialog() {
        final BaseDialog mEditSchoolDialog = new BaseDialog(mContext, R.style.MyDialogStyle, R.layout.custom_edit_dialog_layout);
        ((TextView) mEditSchoolDialog.findViewById(R.id.title)).setText("教育信息");
        ((TextView) mEditSchoolDialog.findViewById(R.id.tv_1)).setText("毕业学校");
        ((TextView) mEditSchoolDialog.findViewById(R.id.tv_2)).setText("学历");
        final EditText et1 = (EditText) mEditSchoolDialog.findViewById(R.id.et1);
        et1.setText(mSchool.getText().toString());
        et1.setSelection(mSchool.getText().length());
        final EditText et2 = (EditText) mEditSchoolDialog.findViewById(R.id.et2);
        et2.setText(mEducation.getText());
        et2.setSelection(mEducation.getText().length());
        mEditSchoolDialog.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSchoolDialog.dismiss();
            }
        });
        mEditSchoolDialog.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchool.setText(et1.getText().toString());
                mEducation.setText(et2.getText().toString());
                mEditSchoolDialog.dismiss();
            }
        });
        mEditSchoolDialog.show();
    }

    private void showEditCompanyDialog() {
        final BaseDialog mEditCompanyDialog = new BaseDialog(mContext, R.style.MyDialogStyle, R.layout.custom_edit_dialog_layout);
        ((TextView) mEditCompanyDialog.findViewById(R.id.title)).setText("职业信息");
        ((TextView) mEditCompanyDialog.findViewById(R.id.tv_1)).setText("公司名称");
        ((TextView) mEditCompanyDialog.findViewById(R.id.tv_2)).setText("职位");
        final EditText et1 = (EditText) mEditCompanyDialog.findViewById(R.id.et1);
        et1.setText(mCompany.getText().toString());
        et1.setSelection(mCompany.getText().length());
        final EditText et2 = (EditText) mEditCompanyDialog.findViewById(R.id.et2);
        et2.setText(mPosition.getText());
        et2.setSelection(mPosition.getText().length());
        mEditCompanyDialog.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditCompanyDialog.dismiss();
            }
        });
        mEditCompanyDialog.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompany.setText(et1.getText().toString());
                mPosition.setText(et2.getText().toString());
                mEditCompanyDialog.dismiss();
            }
        });
        mEditCompanyDialog.show();
    }

    private View.OnClickListener labelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            shortToast("标签ID-" + tag);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
