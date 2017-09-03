package com.example.huolongluo.sample.huolongluo.net.okhttp;

/**
 * Created by SLAN on 2016/8/10.
 */

public interface HttpOnNextListener<T>
{
    void onNext(T response);
}

