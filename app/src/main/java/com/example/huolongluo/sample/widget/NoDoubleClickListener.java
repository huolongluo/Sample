package com.example.huolongluo.sample.widget;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * <p>
 * Created by 火龙裸先生 on 2017/8/28 0028.
 */
/** 用于防止View1秒内重复点击的现象，此处只是用于自定义练手，本项目已有Rxjava关键字做到了防重复点击事件的功能 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_TIME = 1000;
    private long lastClickTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClickListener(v);
        }
    }

    public abstract void onNoDoubleClickListener(View v);
}
