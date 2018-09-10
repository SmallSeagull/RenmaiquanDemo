package com.dolphin.renmaicircle.activity.personalcenter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;
import com.dolphin.renmaicircle.base.BaseDialog;
import com.dolphin.renmaicircle.bean.WalletItemBean;
import com.dolphin.renmaicircle.utils.DensityHelper;
import com.dolphin.renmaicircle.utils.DividerGridItemDecoration;
import com.dolphin.renmaicircle.widget.PayPsdInputView;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends BaseActivity {


    private RecyclerView mRv;
    private RecyclerView.LayoutManager mLayoutManager;
    List<WalletItemBean> walletItemBeanList;
    private WalletAdapter adapter;
    private int[] imgs = {R.drawable.ic_persona_jiaoliu,R.drawable.ic_persona_hotwallet,R.drawable.ic_persona_coldwallet};
    private String[] texts = {"交流中心","热钱包","冷钱包"};
    private View mCard;
    private View mRmb;
    private View mRmc;
    private BaseDialog mSetPassword;
    private PayPsdInputView psw1;
    private PayPsdInputView psw2;

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        mCard.setOnClickListener(listener);
        mRmb.setOnClickListener(listener);
        mRmc.setOnClickListener(listener);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        shortToast("交流中心");
                        break;
                    case 1:
                        if(mSetPassword == null){
                            mSetPassword = new BaseDialog(mContext, R.style.MyDialogStyle, R.layout.set_wallet_password_dialog_layout);
                        }
                        mSetPassword.findViewById(R.id.tv_cancel).setOnClickListener(listener);
                        mSetPassword.findViewById(R.id.tv_confirm).setOnClickListener(listener);
                        psw1 = (PayPsdInputView) mSetPassword.findViewById(R.id.password_1);
                        psw2 = (PayPsdInputView) mSetPassword.findViewById(R.id.password_2);
                        mSetPassword.show();
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    private void initData() {
        walletItemBeanList = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            WalletItemBean bean = new WalletItemBean();
            bean.tv = texts[i];
            bean.imgId = imgs[i];
            walletItemBeanList.add(bean);
        }
        adapter = new WalletAdapter(R.layout.wallet_item_layout,walletItemBeanList);
        mRv.setAdapter(adapter);
    }

    private void initView() {
        mCard = findView(R.id.card);
        mRmb = findView(R.id.rmb);
        mRmc = findView(R.id.rmc);
        mRv = ((RecyclerView) findView(R.id.rv));
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRv.setLayoutManager(mLayoutManager);
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext,(int) DensityHelper.pt2px(mContext,1),R.color.linecolor);
        mRv.addItemDecoration(itemDecoration);

    }

    private void initTitle() {
        setTopTitle("我的钱包",true);
        setLeftBtn(true,R.drawable.back,"   ",listener);
        setRightBtn(true,R.drawable.ic_persona_q_sys,"",listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_btn:
                    finish();
                    break;
                case R.id.right_btn:
                    goActivity(MyBillActivity.class);
                    break;
                case R.id.card:
                    goActivity(BankCardActivity.class);
                    break;
                case R.id.rmb:
                    goActivity(ChangeActivity.class);
                    break;
                case R.id.rmc:
                    break;
                case R.id.tv_cancel:
                    if(mSetPassword!=null&&mSetPassword.isShowing()){
                        mSetPassword.dismiss();
                    }
                    break;
                case R.id.tv_confirm:
                    checkPassword();
                    break;
            }
        }
    };

    private void checkPassword() {
        if(TextUtils.isEmpty(psw1.getPasswordString())){
            shortToast("请设置密码");
            cleanPsd();
        }else if(psw1.getPasswordString().length()<6) {
            shortToast("请设置6位数密码");
            cleanPsd();
        }else if(!TextUtils.equals(psw1.getPasswordString(), psw2.getPasswordString())){
            shortToast("两次输入密码不一致");
            cleanPsd();
        }
    }

    private void cleanPsd() {
        if(psw1!=null){
            psw1.cleanPsd();
        }
        if(psw2!=null){
            psw2.cleanPsd();
        }
    }

    private class WalletAdapter extends BaseQuickAdapter<WalletItemBean,BaseViewHolder> {

        private WalletAdapter(@LayoutRes int layoutResId, @Nullable List<WalletItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WalletItemBean item) {
            helper.setText(R.id.tv,item.tv);
            Glide.with(mContext).load(item.imgId).into(((ImageView) helper.getView(R.id.iv)));

        }
    }
}
