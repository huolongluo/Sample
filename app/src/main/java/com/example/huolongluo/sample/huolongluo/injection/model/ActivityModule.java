package com.example.huolongluo.sample.huolongluo.injection.model;

import android.app.Activity;
import android.content.Context;

import com.example.huolongluo.sample.base.ActivityContext;
import com.example.huolongluo.sample.base.PerActivity;
import com.example.huolongluo.sample.huolongluo.net.okhttp.Api;
import com.example.huolongluo.sample.huolongluo.net.okhttp.ApiCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@Module
public class ActivityModule
{
    private Activity mActivity;
    
    public ActivityModule(Activity mActivity)
    {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity()
    {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext()
    {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Api provideApi(@Named("api") OkHttpClient okHttpClient, @ActivityContext Context mContext)
    {
        return new Api(okHttpClient, mContext);
    }

    @Provides
    @PerActivity
    public ApiCache provideApiCache(@Named("apiCache") OkHttpClient okHttpClient, @ActivityContext Context mContext)
    {
        return new ApiCache(okHttpClient, mContext);
    }
}
