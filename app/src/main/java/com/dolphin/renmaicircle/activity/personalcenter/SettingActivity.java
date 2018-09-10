package com.dolphin.renmaicircle.activity.personalcenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.activity.register.LoginActivity;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.base.BaseDialog;
import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.DataCleanManager;
import com.kyleduo.switchbutton.SwitchButton;

public class SettingActivity extends BaseActivity {


    private View mChangePassword;
    private SwitchButton mSwitch;
    private View mBlacklist;
    private View mClean;
    private View mAbout;
    private View mExit;
    private BaseDialog mExitDialog;
    private TextView mClean_tv;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initData() {
        mClean_tv.setText(DataCleanManager.getTotalCacheSize(mContext));
    }

    private void setListener() {
        mChangePassword.setOnClickListener(listener);
        mBlacklist.setOnClickListener(listener);
        mClean.setOnClickListener(listener);
        mAbout.setOnClickListener(listener);
        mExit.setOnClickListener(listener);
    }

    private void initView() {
        mChangePassword = findView(R.id.change_password);
        mSwitch = ((SwitchButton) findView(R.id.switchs));
        mBlacklist = findView(R.id.blacklist);
        mClean = findView(R.id.clean);
        mClean_tv = ((TextView) findView(R.id.tv));
        mAbout = findView(R.id.about);
        mExit = findView(R.id.exit);
    }

    private void initTitle() {
        setTopTitle("设置", true);
        setLeftBtn(true, R.drawable.back, "   ", listener);
    }

    private AppSharedPreferences instance;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.change_password:
                    goActivity(ChangePasswordActivity.class);
                    break;
                case R.id.blacklist:
                    break;
                case R.id.clean:
                    DataCleanManager.clearAllCache(mContext);
                    showDialog("正在清除...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mClean_tv.setText(DataCleanManager.getTotalCacheSize(mContext));
                            closeDialog();
                        }
                    }, 2000);
                    break;
                case R.id.about:
                    break;
                case R.id.exit:
                    showExitDialog();
                    break;
                case R.id.tv1:
                    closeDeleteDialog();
                    break;
                case R.id.tv2:
                    mExitDialog.dismiss();
                    if (instance == null) {
                        instance = AppSharedPreferences.getInstance(mContext);
                    }
                    instance.clear();
                    Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void showExitDialog() {
        if (mExitDialog == null) {
            mExitDialog = new BaseDialog(mContext, R.style.MyDialogStyle, R.layout.custom_secondcheck_layout);
            TextView tv = (TextView) mExitDialog.findViewById(R.id.tv);
            tv.setText("确认退出登录吗？");
        }
        mExitDialog.findViewById(R.id.tv1).setOnClickListener(listener);
        mExitDialog.findViewById(R.id.tv2).setOnClickListener(listener);
        mExitDialog.show();
    }

    private void closeDeleteDialog() {
        if (mExitDialog != null && mExitDialog.isShowing()) {
            mExitDialog.dismiss();
        }
    }
}
