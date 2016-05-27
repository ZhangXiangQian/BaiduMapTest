package com.github.zxq.test;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by zhang on 2016/5/27.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
