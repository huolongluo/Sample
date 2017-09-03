package com.example.huolongluo.sample.huolongluo.net.okhttp;

import android.content.Context;

import com.example.huolongluo.sample.base.ActivityContext;
import com.example.huolongluo.sample.huolongluo.net.UrlConstants;

import javax.inject.Named;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ApiCache，具有缓存功能的API
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public class ApiCache
{
    private ApiCacheService mApiCacheService;
    private Context mContext;

    public ApiCache(@Named("apiCache") OkHttpClient mOkHttpClient, @ActivityContext Context context)
    {
        mContext = context;
        Retrofit retrofit = new Retrofit.Builder()//
                .addConverterFactory(JacksonConverterFactory.create())//
                .client(mOkHttpClient)//
                .baseUrl(UrlConstants.DOMAIN)//
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .build();
        mApiCacheService = retrofit.create(ApiCacheService.class);
    }

    public Observable handle(Observable observable)
    {
        return observable.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
