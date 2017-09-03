package com.example.huolongluo.sample.huolongluo.injection.component;

import com.example.huolongluo.sample.base.PerActivity;
import com.example.huolongluo.sample.huolongluo.injection.model.ActivityModule;
import com.example.huolongluo.sample.huolongluo.net.okhttp.Api;
import com.example.huolongluo.sample.huolongluo.net.okhttp.ApiCache;
import com.example.huolongluo.sample.huolongluo.ui.activity.main.MainActivity;

import dagger.Component;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    Api getApi();

    ApiCache getApiCache();

    void inject(MainActivity activity);
}
