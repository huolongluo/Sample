package com.example.huolongluo.sample.huolongluo.injection.model;

import android.app.Application;
import android.content.Context;

import com.example.huolongluo.sample.base.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@Module
public class ApplicationModule
{
    protected final Application mApplication;

    public ApplicationModule(Application mApplication)
    {
        this.mApplication = mApplication;
    }

    @Provides
    Application provodeApplication()
    {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext()
    {
        return mApplication;
    }
}
