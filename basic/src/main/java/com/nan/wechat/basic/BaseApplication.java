package com.nan.wechat.basic;

import android.app.Application;

import com.nan.wechat.router.Router;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.getInstance().init(this);
    }
}
