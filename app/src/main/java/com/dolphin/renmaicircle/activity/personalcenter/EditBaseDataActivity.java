package com.dolphin.renmaicircle.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.api.ContentApi;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.bean.InformationBean;
import com.dolphin.renmaicircle.bean.MessageEvent;
import com.dolphin.renmaicircle.utils.AddressPickTask;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class EditBaseDataActivity extends BaseActivity {


    private EditText mNickname;
    private ImageView mEdit;
    private View mBirthday_layout;
    private TextView mBirthday;
    private View mAddress_layout;
    private TextView mAddress;

    private ImageView mAvatar;

    private String address;
    private String birthday;
    private View mCommit;
    private RadioGroup mRadio;
    private EditText mAddressDetail;
    private InformationBean.DataBean data;
    private AppSharedPreferences instance;

    private File avatar = null;
    private String path = null;
    private int gender;
    private String detail_address;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_base_data;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = ((InformationBean.DataBean) extras.getSerializable("data"));
        }
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initData() {
        Glide.with(mContext)
                .load(data.getHeadPortrait())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatar);
        mNickname.setText(data.getNickName());
        switch (data.getSex()) {
            case 1:
                mRadio.check(R.id.btn_0);
                break;
            case 2:
                mRadio.check(R.id.btn_1);
                break;
        }
        mBirthday.setText(data.getBrithday());
        mAddress.setText(data.getRegion());
        mAddressDetail.setText(data.getAddress());

    }

    private void setListener() {
        mAvatar.setOnClickListener(listener);
        mNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mNickname.setBackgroundResource(R.color.white);
                    mNickname.setTextColor(getResources().getColor(R.color.themecolor));
                    mNickname.setSelection(mNickname.getText().length());
                } else {
                    mNickname.setBackgroundResource(R.color.themecolor);
                    mNickname.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        mEdit.setOnClickListener(listener);
        mBirthday_layout.setOnClickListener(listener);
        mAddress_layout.setOnClickListener(listener);
        mCommit.setOnClickListener(listener);
    }

    private void initView() {
        mAvatar = ((ImageView) findView(R.id.avatar));
        mNickname = ((EditText) findView(R.id.nickname));
        mEdit = ((ImageView) findView(R.id.edit));
        mRadio = ((RadioGroup) findView(R.id.radio));
        mBirthday_layout = findView(R.id.rl3);
        mBirthday = ((TextView) findView(R.id.birthday));
        mAddress_layout = findView(R.id.rl4);
        mAddress = ((TextView) findView(R.id.address));
        mAddressDetail = ((EditText) findView(R.id.address_detail));
        mCommit = findView(R.id.commit);
    }

    private void initTitle() {
        setTopTitle("编辑基本资料", true);
        setLeftBtn(true, R.drawable.ic_persona_editor_cha, "   ", listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.avatar:
                    PictureSelector.create(mContext)
                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                            .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                            .maxSelectNum(1)// 最大图片选择数量 int
//                            .minSelectNum(1)// 最小选择数量 int
                            .imageSpanCount(4)// 每行显示个数 int
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .previewImage(true)// 是否可预览图片 true or false
//                            .previewVideo()// 是否可预览视频 true or false
//                            .enablePreviewAudio() // 是否可播放音频 true or false
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .setOutputCameraPath("/RenmaiCircle")// 自定义拍照保存路径,可不填
                            .enableCrop(true)// 是否裁剪 true or false
                            .compress(false)// 是否压缩 true or false
//                            .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                            .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
//                            .isGif()// 是否显示gif图片 true or false
//                            .compressSavePath("/Compress")//压缩图片保存地址
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .openClickSound(false)// 是否开启点击声音 true or false
//                            .selectionMedia(true)// 是否传入已选图片 List<LocalMedia> list
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                            .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
//                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
//                            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                            .videoQuality(1)// 视频录制质量 0 or 1 int
//                            .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                            .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                            .recordVideoSecond(20)//视频秒数录制 默认60s int
                            .isDragFrame(false)// 是否可拖动裁剪框(固定)
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                    break;
                case R.id.edit:
                    if (mNickname.hasFocus()) {
                        mNickname.clearFocus();
                    } else {
                        mNickname.requestFocus();
                    }
                    break;
                case R.id.rl3:
                    final DatePicker picker = new DatePicker(mContext);
                    picker.setCanceledOnTouchOutside(true);
                    picker.setUseWeight(true);
                    picker.setTopPadding(ConvertUtils.toPx(mContext, 15));
                    picker.setDividerColor(getResources().getColor(R.color.linecolor));
                    picker.setTextColor(getResources().getColor(R.color.color_666666));
                    picker.setCancelTextColor(getResources().getColor(R.color.color_666666));
                    picker.setSubmitTextColor(getResources().getColor(R.color.secondcolor));
                    picker.setTopLineColor(getResources().getColor(R.color.linecolor));
                    picker.setLabelTextColor(getResources().getColor(R.color.color_666666));
                    picker.setBackgroundColor(getResources().getColor(R.color.white));
                    picker.setAnimationStyle(R.style.Animation_CustomPopup);
                    picker.setHeight((int) DensityHelper.pt2px(mContext, 700));
                    picker.setResetWhileWheel(true);
                    picker.setRangeEnd(2050, 12, 31);
                    picker.setRangeStart(1918, 1, 1);
                    picker.setSelectedItem(1990, 6, 15);
                    picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            birthday = year + "-" + month + "-" + day;
                            mBirthday.setText(birthday);
                        }
                    });
                    picker.show();
                    break;
                case R.id.rl4:
                    AddressPickTask task = new AddressPickTask(mContext);
                    task.setHideProvince(false);
                    task.setHideCounty(false);
                    task.setCallback(new AddressPickTask.Callback() {
                        @Override
                        public void onAddressInitFailed() {
                            shortToast("数据初始化失败");
                        }

                        @Override
                        public void onAddressPicked(Province province, City city, County county) {
                            if (county == null) {
                                address = province.getAreaName() + "-" + city.getAreaName();
                            } else {
                                address = province.getAreaName() + "-" + city.getAreaName() + "-" + county.getAreaName();
                            }
                            mAddress.setText(address);
                        }
                    });
                    task.execute("北京", "北京", "东城");
                    break;
                case R.id.commit:
                    updateData();
            }
        }
    };

    private void updateData() {
        gender = 1;
        switch (mRadio.getCheckedRadioButtonId()) {
            case R.id.btn_0:
                gender = 1;
                break;
            case R.id.btn_1:
                gender = 2;
                break;
        }
        detail_address = null;
        if (!TextUtils.isEmpty(mAddressDetail.getText().toString())) {
            detail_address = mAddressDetail.getText().toString();
        }
        if (path != null) {
            Luban.with(this)
                    .load(path)
                    .ignoreBy(100)
//                    .setTargetDir(getPath())
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            save(file);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();
        } else {
            save(null);
        }
    }

    private void save(File file) {
        ContentApi.updateInformation(mContext, file, mNickname.getText().toString(), gender, null, birthday,
                address, detail_address, null, null, null, null, new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        mCommit.setEnabled(false);
                        Log.e("QQ", "更新资料onStart：" + request.getParams().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("QQ", "保存资料oError:" + response.code() + "\r\n" + response.message());
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
                        Log.e("QQ", "更新资料：" + body);
                        if (response.code() >= 200 && response.code() < 300) {
                            JSONObject object = JSONObject.parseObject(body);
                            Integer code = object.getInteger("code");
                            String msg = object.getString("msg");
                            shortToast(msg);
                            if (code == Constant.code_200) {
                                JSONObject data = object.getJSONObject("data");
                                String headPortrait = data.getString("headPortrait");
                                String nickName = data.getString("nickName");
                                //更新sp中的数据
                                if (instance == null) {
                                    instance = AppSharedPreferences.getInstance(mContext);
                                }
                                instance.set(Constant.AVATAR, headPortrait);
                                instance.set(Constant.NICKNAME, nickName);
                                //更新其他页面的头像和昵称
                                EventBus.getDefault().post(new MessageEvent(Constant.UPDATE_AVATAR_NAME));
                                finish();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia localMedia = selectList.get(0);
                    path = localMedia.getCutPath();
                    Glide.with(mContext)
                            .load(path)
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(mAvatar);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
