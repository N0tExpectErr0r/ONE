package com.nullptr.one.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/7
 * @DESCRIPTION 所有Activity的基类，将Activity的onCreate划分为三个子部分 1.initVariables 用于初始化变量，包括Intent带的数据及Activity内的变量 2.initViews
 * 加载layout布局文件，初始化控件，为控件挂上事件方法 3.loadData 调用MobileAPI获取数据
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    protected abstract void initVariables();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
