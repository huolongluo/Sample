package com.example.huolongluo.sample.huolongluo.net.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by sll on 2016/2/23.
 */
public class CookieInterceptor implements Interceptor
{

    public CookieInterceptor()
    {
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        return null;
    }
}
