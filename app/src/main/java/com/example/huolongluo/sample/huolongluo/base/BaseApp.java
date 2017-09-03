package com.example.huolongluo.sample.huolongluo.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.huolongluo.sample.base.s;
import com.example.huolongluo.sample.huolongluo.injection.component.ApplicationComponent;
import com.example.huolongluo.sample.huolongluo.injection.component.DaggerApplicationComponent;
import com.example.huolongluo.sample.huolongluo.injection.model.ApiModule;
import com.example.huolongluo.sample.huolongluo.injection.model.ApplicationModule;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class BaseApp extends Application
{
    private static final String TAG = "BaseApp";
    private ApplicationComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        /**
         * 做一些初始化操作
         * */
        Log.e(TAG, "onCreate: " + "执行了BaseApp基类============== 做一些初始化操作 =============");

        s.Ext.init(this);
        s.Ext.setDebug(true);              //日志开关
    }

    public ApplicationComponent getAppComponent()
    {
        if (null == mAppComponent)
        {
            mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).apiModule(new ApiModule(this)).build();
        }
        return mAppComponent;
    }

    public static BaseApp get(Context context)
    {
        return (BaseApp) context.getApplicationContext();
    }

    public void setAppComponent(ApplicationComponent mAppComponent)
    {
        this.mAppComponent = mAppComponent;
    }
}
