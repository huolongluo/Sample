package com.example.huolongluo.sample.huolongluo.ui.activity.main;

import com.example.huolongluo.sample.base.BaseView;
import com.example.huolongluo.sample.huolongluo.base.BasePresenter;

import java.util.Map;

import rx.Subscription;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public interface MainContract
{
    interface View extends BaseView
    {
        void setTime(String dateData);
    }

    interface Presenter extends BasePresenter<View>
    {
        Subscription getTime(Map<String, String> options);
    }
}
