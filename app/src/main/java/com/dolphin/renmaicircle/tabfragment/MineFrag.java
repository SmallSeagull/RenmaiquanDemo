package com.dolphin.renmaicircle.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.personalcenter.AuthActivity;
import com.dolphin.renmaicircle.activity.personalcenter.FavoriteActivity;
import com.dolphin.renmaicircle.activity.personalcenter.FeedbackActivity;
import com.dolphin.renmaicircle.activity.personalcenter.LabelActivity;
import com.dolphin.renmaicircle.activity.personalcenter.PersonInformationActivity;
import com.dolphin.renmaicircle.activity.personalcenter.SettingActivity;
import com.dolphin.renmaicircle.activity.personalcenter.SystemMessageActivity;
import com.dolphin.renmaicircle.activity.personalcenter.WaitingSolveActivity;
import com.dolphin.renmaicircle.activity.personalcenter.WalletActivity;
import com.dolphin.renmaicircle.base.BaseFragment;
import com.dolphin.renmaicircle.bean.MessageEvent;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Dolphin on 2018/7/24.
 */

public class MineFrag extends BaseFragment {

    private ImageView mQRCode;
    private ImageView mSetting;
    private ImageView mAvatar;
    private TextView mNickname;
    private TextView mInfluence;
    private View mAutn;
    private View mPersonalData;
    private View mDynamic;
    private View mLabel;
    private View mWallet;
    private View mCollect;
    private View mMessage;
    private View mWaiting_Solve;
    private View mInvite;
    private View mFeedback;
    private AppSharedPreferences instance;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (Constant.UPDATE_AVATAR_NAME.equals(event.getMessage())) {
            Log.e("QQ", "Mycenter:event.getMessage():" + event.getMessage());
            changeAvatarAndName();
        }
    }

    private void changeAvatarAndName() {
        if (instance == null) {
            instance = AppSharedPreferences.getInstance(mActivity);
        }
        Glide.with(mActivity).
                load(instance.get(Constant.AVATAR))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatar);
        mNickname.setText(instance.get(Constant.NICKNAME));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mQRCode = ((ImageView) findView(R.id.qrcode));
        mSetting = ((ImageView) findView(R.id.setting));
        mAvatar = ((ImageView) findView(R.id.avatar));
        mNickname = ((TextView) findView(R.id.nickname));
        mInfluence = ((TextView) findView(R.id.influence));
        mAutn = findView(R.id.rl1);
        mPersonalData = findView(R.id.rl2);
        mDynamic = findView(R.id.rl3);
        mLabel = findView(R.id.rl4);
        mWallet = findView(R.id.wallet);
        mCollect = findView(R.id.collect);
        mMessage = findView(R.id.message);
        mWaiting_Solve = findView(R.id.waiting_solve);
        mInvite = findView(R.id.invite);
        mFeedback = findView(R.id.feedback);
        setListener();
    }

    private void setListener() {
        mQRCode.setOnClickListener(listener);
        mSetting.setOnClickListener(listener);
        mAutn.setOnClickListener(listener);
        mPersonalData.setOnClickListener(listener);
        mDynamic.setOnClickListener(listener);
        mLabel.setOnClickListener(listener);
        mWallet.setOnClickListener(listener);
        mCollect.setOnClickListener(listener);
        mMessage.setOnClickListener(listener);
        mWaiting_Solve.setOnClickListener(listener);
        mInvite.setOnClickListener(listener);
        mFeedback.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.qrcode:
                    break;
                case R.id.setting:
                    goActivity(SettingActivity.class);
                    break;
                case R.id.rl1://认证
                    goActivity(AuthActivity.class);
                    break;
                case R.id.rl2://个人资料
                    goActivity(PersonInformationActivity.class);
                    break;
                case R.id.rl3://动态
                    break;
                case R.id.rl4://标签
                    goActivity(LabelActivity.class);
                    break;
                case R.id.wallet:
                    goActivity(WalletActivity.class);
                    break;
                case R.id.collect:
                    goActivity(FavoriteActivity.class);
                    break;
                case R.id.message:
                    goActivity(SystemMessageActivity.class);
                    break;
                case R.id.waiting_solve:
                    goActivity(WaitingSolveActivity.class);
                    break;
                case R.id.invite:
                    break;
                case R.id.feedback:
                    goActivity(FeedbackActivity.class);
                    break;

            }
        }
    };



    @Override
    protected void initData() {
        changeAvatarAndName();
        mInfluence.setText("影响力:"+instance.getInt(Constant.INTEGRAL));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
