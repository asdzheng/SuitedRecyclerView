package com.asdzheng.suitedrecyclerview.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by asdzheng on 2016/1/6.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(setLayout() != 0) {
            setContentView(setLayout());
            ButterKnife.bind(this);
        }

        initViews();
        initData(savedInstanceState);
    }

    /**
     * 设置布局,如果子类不想设置，返回0就可以
     */
    protected abstract int setLayout();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 为控件设置内容或者监听器
     */
    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelTag(this);
        ButterKnife.unbind(this);
    }

}
