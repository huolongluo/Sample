package com.example.huolongluo.sample.huolongluo.ui.activity.main;

import android.content.Context;

import com.example.huolongluo.sample.base.ActivityContext;
import com.example.huolongluo.sample.huolongluo.net.okhttp.Api;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public class MainPresent implements MainContract.Presenter
{
    private MainContract.View mView;
    private Context mContext;

    @Inject
    Api api;

    @Inject
    public MainPresent(@ActivityContext Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public void attachView(MainContract.View view)
    {
        mView = view;
    }

    @Override
    public void detachView()
    {
        mView = null;
    }

    @Override
    public Subscription getTime(Map<String, String> options)
    {
        return api.register(options, response ->
        {
            mView.setTime(response.toString());
        });
    }
}
