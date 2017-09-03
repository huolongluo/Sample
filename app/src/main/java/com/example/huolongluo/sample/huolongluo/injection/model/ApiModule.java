package com.example.huolongluo.sample.huolongluo.injection.model;

import android.content.Context;

import com.example.huolongluo.sample.base.s;
import com.example.huolongluo.sample.huolongluo.net.okhttp.HttpLoggingInterceptor;
import com.example.huolongluo.sample.huolongluo.net.okhttp.HttpUtils;
import com.example.huolongluo.sample.huolongluo.net.okhttp.OkHttpHelper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@Module
public class ApiModule
{
    Context mContext;

    public ApiModule(Context mContext)
    {
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    OkHttpHelper provideOkHttpHelper(@Named("api") OkHttpClient mOkHttpClient)
    {
        return new OkHttpHelper(mOkHttpClient);
    }

    @Provides
    @Singleton
    @Named("api")
    OkHttpClient provideApiOkHttpClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) //
                .readTimeout(15, TimeUnit.SECONDS) //
                .writeTimeout(15, TimeUnit.SECONDS);//
        if (s.isDebug())
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    @Named("apiCache")
    OkHttpClient provideApiOkHttpClientCache()
    {
        File cacheFile = new File(mContext.getExternalCacheDir(), "FileCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain ->
        {
            Request request = chain.request();
            if (!HttpUtils.isNetworkConnected(mContext))
            {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (HttpUtils.isNetworkConnected(mContext))
            {
                int maxAge = 0;// 有网络时 设置缓存超时时间0个小时
                response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            }
            else
            {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader("Pragma").build();
            }

            return response;
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache).addInterceptor(cacheInterceptor)// 缓存
                .connectTimeout(15, TimeUnit.SECONDS) //
                .readTimeout(15, TimeUnit.SECONDS) //
                .writeTimeout(15, TimeUnit.SECONDS);

        if (s.isDebug())
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        return builder.build();
    }

}
