package com.example.huolongluo.sample.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class RxCountDown
{

    public static Observable<Integer> countdown(int time)
    {
        if (time < 0)
        {
            time = 0;
        }

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).map
                (increaseTime -> countTime - increaseTime.intValue()).take(countTime + 1);

    }
}