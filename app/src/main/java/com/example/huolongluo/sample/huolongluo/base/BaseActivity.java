package com.example.huolongluo.sample.huolongluo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.huolongluo.sample.base.BaseView;
import com.example.huolongluo.sample.huolongluo.injection.component.ActivityComponent;
import com.example.huolongluo.sample.huolongluo.injection.component.DaggerActivityComponent;
import com.example.huolongluo.sample.huolongluo.injection.model.ActivityModule;
import com.example.huolongluo.sample.huolongluo.manager.DialogManager;
import com.example.huolongluo.sample.manager.AppManager;
import com.example.huolongluo.sample.util.ToastSimple;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 火龙裸先生 on 2017/8/10.
 * Class Note:
 * 1 所有的activity继承于这个类
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView
{
    private Context mContext = null; //context
    private ActivityComponent mActivityComponent;

    public Subscription subscription;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mContext = this;

        AppManager.get().addActivity(this);

        if (getContentViewId() != 0)
        {
            setContentView(getContentViewId());
        }

        unbinder = ButterKnife.bind(this);

        // dagger2注解
        injectDagger();

        initViewsAndEvents();
    }

    public ActivityComponent activityComponent()
    {
        if (null == mActivityComponent)
        {
            mActivityComponent = DaggerActivityComponent.builder().applicationComponent(BaseApp.get(this).getAppComponent()).activityModule(new 
                    ActivityModule(this)).build();
        }
        return mActivityComponent;
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        AppManager.get().finishActivity(this);

        unbinder.unbind();
        unSubscription();
    }

    public void unSubscription()
    {
        if (null != subscription && !subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }

    public void startActivity(Class<?> clazz)
    {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void startActivity(Class<?> clazz, Bundle bundle)
    {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
        {
            intent.putExtra("bundle", bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public Observable<Void> eventClick(View view)
    {
        return eventClick(view, 1000);
    }

    public Observable<Void> eventClick(View view, int milliseconds)
    {
        return RxView.clicks(view).throttleFirst(milliseconds, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
    }

    public Bundle getBundle()
    {
        return getIntent().getBundleExtra("bundle");
    }

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewId();

    /**
     * Dagger2 use in your application module(not used in 'base' module)
     */
    protected abstract void injectDagger();

    /**
     * init views and events here
     */
    protected abstract void initViewsAndEvents();


    /**
     * implements methods in BaseView
     */
    @Override
    public void showMessage(String msg, double seconds)
    {
        ToastSimple.show(msg, seconds);
    }

    @Override
    public void close()
    {
        finish();
    }

    @Override
    public void showProgress(String msg)
    {
        DialogManager.INSTANCE.showProgressDialog(mContext, msg);
    }

    @Override
    public void showProgress(String msg, int progress)
    {
        DialogManager.INSTANCE.showProgressDialog(mContext, msg, progress);
    }

    @Override
    public void hideProgress()
    {
        DialogManager.INSTANCE.dismiss();
    }

    @Override
    public void showErrorMessage(String msg, String content)
    {
        DialogManager.INSTANCE.showErrorDialog(mContext, msg, content, SweetAlertDialog::dismissWithAnimation);
    }

}
