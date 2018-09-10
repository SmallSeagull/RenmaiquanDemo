package com.dolphin.renmaicircle.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * Created by 48143 on 2017/9/20.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View mFragmentView;
    private View mRootView;
    public TextView wTitle;
    private ImageView wLeft_iv;
    private TextView wLeft_tv;
    private LinearLayout mLeft_btn;
    private ImageView wRight_iv;
    private TextView wRight_tv;
    public RelativeLayout mRight_btn;
    private FrameLayout viewContent;
    private RelativeLayout topBar;
    private ZLoadingDialog loadingDialog;

    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {

        mRootView = LayoutInflater.from(mActivity)
                .inflate(R.layout.activity_basic, container, false);
        StatusBarUtil.immersive(mActivity);
        wTitle = (TextView) mRootView.findViewById(R.id.title);
        wLeft_iv = (ImageView) mRootView.findViewById(R.id.left_iv);
        wLeft_tv = ((TextView) mRootView.findViewById(R.id.left_tv));
        mLeft_btn = ((LinearLayout) mRootView.findViewById(R.id.left_btn));
//        wRight_iv = (ImageView) mRootView.findViewById(R.id.right_iv);
//        wRight_tv = ((TextView) mRootView.findViewById(R.id.right_tv));
        mRight_btn = ((RelativeLayout) mRootView.findViewById(R.id.right_btn));
        viewContent = (FrameLayout) mRootView.findViewById(R.id.viewContent);
        topBar = (RelativeLayout) mRootView.findViewById(R.id.topbar);
        mFragmentView = LayoutInflater.from(mActivity).inflate(getLayoutId(), viewContent);
        initView(mFragmentView, savedInstanceState);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    protected <T extends View> T findView(int id)
    {
        if (mFragmentView == null)
        {
            return null;
        }

        return (T) mFragmentView.findViewById(id);
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData();

    //设置标题名称
    protected void setTopTitle(String topName , boolean show){
        if (show){
            topBar.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(topName)){
                wTitle.setText(topName);
            }
        }else {
            topBar.setVisibility(View.GONE);
        }
    }
    //设置标题栏左侧的按钮
    protected void setLeftBtn(boolean show , int ResId , String content, View.OnClickListener leftClick){
        if (show){
            wLeft_tv.setText(content);
            wLeft_iv.setBackgroundResource(ResId);
            mLeft_btn.setOnClickListener(leftClick);
        }else {
            mLeft_btn.setVisibility(View.GONE);
        }
    }
    //设置标题栏右侧的按钮
    protected void setRightBtn(boolean show, int ResId, String content, View.OnClickListener rightClick) {
        if (show) {
            if(ResId!=0){
                mRight_btn.setBackgroundResource(ResId);
            }
            if(!content.trim().isEmpty()){
                TextView tv = new TextView(mActivity);
                tv.setText(content);
                tv.setTextColor(getResources().getColor(R.color.white));
                mRight_btn.addView(tv);
            }
//            wRight_tv.setText(content);
            mRight_btn.setOnClickListener(rightClick);
        } else {
            mRight_btn.setVisibility(View.GONE);
        }
    }

    //设置标题栏右侧的按钮
    protected void setRightBtn(boolean show, View childView, View.OnClickListener rightClick) {
        if (show) {
            if(childView!=null){
                mRight_btn.addView(childView);
            }
            mRight_btn.setOnClickListener(rightClick);
        } else {
            mRight_btn.setVisibility(View.GONE);
        }
    }
    protected void goActivity(Class<?> cls){
        Intent intent = new Intent(mActivity,cls);
        startActivity(intent);
    }

    protected void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void goActivityAndFinish(Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        startActivity(intent);
        mActivity.finish();
    }
    protected void goActivityForResult(Class<?> cls, Bundle bundle, int requestCode){
        Intent intent = new Intent(mActivity,cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    protected void goActivityForResult(Class<?> cls, int requestCode){
        Intent intent = new Intent(mActivity,cls);
        startActivityForResult(intent,requestCode);
    }

    public void shortToast(String str) {
        Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String str) {
        Toast.makeText(mActivity, str, Toast.LENGTH_LONG).show();
    }

    //显示对话框
    protected void showDialog(String msg) {
        loadingDialog = DialogUtil.createLoadingDialog(mActivity, msg);
    }

    //关闭对话框
    protected void closeDialog() {
        DialogUtil.closeLoadingDialog(loadingDialog);
    }

}
