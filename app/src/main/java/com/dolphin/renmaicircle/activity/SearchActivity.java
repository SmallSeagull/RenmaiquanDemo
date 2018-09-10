package com.dolphin.renmaicircle.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.dolphin.renmaicircle.R;
import com.dolphin.renmaicircle.base.BaseActivity;

public class SearchActivity extends BaseActivity {


    private View mBack;
    private EditText mSearch;
    private View mDelete;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    mDelete.setVisibility(View.GONE);
                }else {
                    mDelete.setVisibility(View.VISIBLE);
                }
            }
        });
        mBack.setOnClickListener(listener);
        mDelete.setOnClickListener(listener);
    }

    private void initData() {

    }

    private void initView() {
        mBack = findView(R.id.back);
        mSearch = ((EditText) findView(R.id.search));
        mDelete = findView(R.id.delete);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.delete:
                    mSearch.getText().clear();
                    break;
            }
        }
    };
}
