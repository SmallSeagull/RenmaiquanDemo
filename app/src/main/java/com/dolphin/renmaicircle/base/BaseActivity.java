package com.dolphin.renmaicircle.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.utils.DialogUtil;
import com.dolphin.renmaicircle.utils.StatusBarUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


public abstract class BaseActivity extends CheckPermissionsActivity {
    protected Activity mContext;
    public static final int NO_STATUE_BAR = 0;
    public static final int ALPHA_STATUE_BAR = 1;
    public static final int NOMAL_STATUE_BAR = 2;
    private TextView wTitle;
    private RelativeLayout topBar;
    private ImageView wLeft_iv;
    protected ImageView wRight_iv;
    private FrameLayout viewContent;
    private TextView wLeft_tv;
    protected TextView wRight_tv;
    private LinearLayout mLeft_btn;
    protected RelativeLayout mRight_btn;
    private ZLoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_basic);
        StatusBarUtil.immersive(this);
        mContext = this;
        wTitle = (TextView) findView(R.id.title);
        wLeft_iv = (ImageView) findView(R.id.left_iv);
        wLeft_tv = ((TextView) findView(R.id.left_tv));
        mLeft_btn = (LinearLayout) findView(R.id.left_btn);
        wRight_iv = (ImageView) findView(R.id.right_iv);
        wRight_tv = ((TextView) findView(R.id.right_tv));
        mRight_btn = ((RelativeLayout) findView(R.id.right_btn));
        viewContent = (FrameLayout) findView(R.id.viewContent);
        topBar = (RelativeLayout) findViewById(R.id.topbar);
        LayoutInflater.from(BaseActivity.this).inflate(getContentView(), viewContent);
        init(savedInstanceState);
    }

    protected abstract int getContentView();

    protected abstract void init(Bundle savedInstanceState);

    //设置状态栏样式
    protected void setStatueBar(int style) {
        switch (style) {
            case NO_STATUE_BAR:
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ALPHA_STATUE_BAR:
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    //设置标题名称
    protected void setTopTitle(String topName, boolean show) {
        if (show) {
            topBar.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(topName)) {
                wTitle.setText(topName);
            }
        } else {
            topBar.setVisibility(View.GONE);
        }
    }

    //设置标题栏左侧的按钮
    protected void setLeftBtn(boolean show, int ResId, String content, View.OnClickListener leftClick) {
        if (show) {
            if (ResId != 0) {
                wLeft_iv.setBackgroundResource(ResId);
            }
            wLeft_tv.setText(content);
            mLeft_btn.setOnClickListener(leftClick);
        } else {
            mLeft_btn.setVisibility(View.GONE);
        }
    }

    //设置标题栏右侧的按钮
    protected void setRightBtn(boolean show, int ResId, String content, View.OnClickListener rightClick) {
        if (show) {
            if (ResId != 0) {
                wRight_iv.setBackgroundResource(ResId);
            }
            wRight_tv.setText(content);
            mRight_btn.setOnClickListener(rightClick);
        } else {
            mRight_btn.setVisibility(View.GONE);
        }
    }

    protected void setRightBtnTvSize(int size){
        wRight_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
//            overridePendingTransition(R.anim.left_fadein,R.anim.left_fadeout);
        }
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.left_fadein,R.anim.left_fadeout);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    protected void goActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    protected void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    protected void goActivityAndFinish(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
        finish();

    }

    protected void goActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void goActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void shortToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
    }

    //显示对话框
    protected void showDialog(String msg) {
        loadingDialog = DialogUtil.createLoadingDialog(mContext, msg);
    }

    //关闭对话框
    protected void closeDialog() {
        DialogUtil.closeLoadingDialog(loadingDialog);
    }
}
