package com.example.huolongluo.sample.huolongluo.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;

import com.example.huolongluo.sample.base.BaseView;
import com.example.huolongluo.sample.huolongluo.manager.DialogManager;
import com.example.huolongluo.sample.util.ToastSimple;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscription;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public abstract class BaseDialog2 extends Dialog implements BaseView
{
    public Activity mContext = null; //context
    public Subscription subscription;
    Unbinder unbinder;

    public BaseDialog2(@NonNull Context context)
    {
        super(context);
        this.mContext = (Activity) context;
    }

    public BaseDialog2(@NonNull Context context, @StyleRes int themeResId)
    {
        super(context, themeResId);
        this.mContext = (Activity) context;
    }

    public void initDialog()
    {
        if (getContentViewId() != 0)
        {
            setContentView(getContentViewId());
        }

        unbinder = ButterKnife.bind(this);
        // dagger2注解
        injectDagger();
        initViewsAndEvents();
    }

    public void startActivity(Class<?> clazz)
    {
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mContext.startActivity(intent);
    }

    public void startActivity(Class<?> clazz, Bundle bundle)
    {
        Intent intent = new Intent(mContext, clazz);
        if (null != bundle)
        {
            intent.putExtra("bundle", bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mContext.startActivity(intent);
    }

    public Observable<Void> eventClick(View view)
    {
        return eventClick(view, 1000);
    }

    public Observable<Void> eventClick(View view, int milliseconds)
    {
        return RxView.clicks(view).throttleFirst(milliseconds, TimeUnit.MILLISECONDS);
    }


    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener)
    {
        super.setOnDismissListener(listener);
        unbinder.unbind();
        unSubscription();
    }

    private void unSubscription()
    {
        if (null != subscription && !subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewId();

    protected abstract void injectDagger();

    /**
     * override this method to do operation in the fragment
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
        mContext.finish();
    }

    @Override
    public void showProgress(String message)
    {
        DialogManager.INSTANCE.showProgressDialog(mContext, message);
    }

    @Override
    public void showProgress(String message, int progress)
    {
        DialogManager.INSTANCE.showProgressDialog(mContext, message, progress);
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
