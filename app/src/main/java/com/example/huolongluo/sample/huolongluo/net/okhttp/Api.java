package com.example.huolongluo.sample.huolongluo.net.okhttp;

import android.content.Context;

import com.example.huolongluo.sample.base.ActivityContext;
import com.example.huolongluo.sample.huolongluo.net.UrlConstants;
import com.example.huolongluo.sample.huolongluo.share.Share;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public class Api
{
    private ApiService mApiService;
    private Context mContext;

    public Api(@Named("api") OkHttpClient mOkHttpClient, @ActivityContext Context context)
    {
        mContext = context;

        Retrofit retrofit = new Retrofit.Builder()//
                .addConverterFactory(JacksonConverterFactory.create())//
                .client(mOkHttpClient)//
                .baseUrl(UrlConstants.DOMAIN)//
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    /**
     * .do接口统一请求参数
     *
     * @return
     */
    private Map<String, String> common()
    {
        Map<String, String> map = new HashMap<>();
        map.put("userId", Share.get().getUserName());
        map.put("deviceId", Share.get().getPassWord());

        return map;
    }

    private Observable handle(Observable observable)
    {
        return observable.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
    

    public Subscription register(Map<String, String> options, HttpOnNextListener next)
    {
        return handle(mApiService.register(options, Share.get().getUserName())).subscribe(new ProgressSubscriber(next, mContext, true));
    }
}
