package com.example.huolongluo.sample.huolongluo.net.okhttp;

import com.example.huolongluo.sample.huolongluo.net.BaseResponse;

/**
 * Created by SLAN on 2016/11/16.
 */

public abstract class HttpOnNextListener2<T> {
    public abstract void onNext(T response);

    public void onFail(BaseResponse errResponse) {
    }

    public void onError(Throwable error) {
    }
}
