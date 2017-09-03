package com.example.huolongluo.sample.huolongluo.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.huolongluo.sample.base.ApplicationContext;
import com.example.huolongluo.sample.huolongluo.base.BaseApp;
import com.example.huolongluo.sample.huolongluo.injection.model.ApiModule;
import com.example.huolongluo.sample.huolongluo.injection.model.ApplicationModule;
import com.example.huolongluo.sample.huolongluo.net.okhttp.OkHttpHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent
{
    void inject(BaseApp application);

    @ApplicationContext
    Context context();

    Application application();

    @Named("api")
    OkHttpClient getOkHttpClient();

    @Named("apiCache")
    OkHttpClient getOkHttpClientCache();

    OkHttpHelper getOkHttpHelper();
}
