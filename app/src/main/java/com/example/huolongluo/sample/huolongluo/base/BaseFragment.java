package com.example.huolongluo.sample.huolongluo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Class Note:
 * <p/>
 * Base Fragment for all the Fragment defined in the project
 * 1 extended from {@link BaseFragment} to do
 * some base operation.
 * 2 do operation in initViewAndEvents(){@link #initViewsAndEvents(View rootView)}
 */

public abstract class BaseFragment extends Fragment implements BaseView
{
    /**
     * activity context of fragment
     */
    protected Activity mContext;
    Unbinder unbinder;
    public Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        initDagger();
    }

    protected abstract void initDagger();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (getContentViewId() != 0)
        {
            return inflater.inflate(getContentViewId(), null);
        }
        else
        {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        initViewsAndEvents(view);
    }

    public void startActivity(Class<?> clazz)
    {
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void startActivity(Class<?> clazz, Bundle bundle)
    {
        Intent intent = new Intent(mContext, clazz);
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
        return RxView.clicks(view).throttleFirst(milliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (null != unbinder)
        {
            unbinder.unbind();
        }
        unSubscription();
    }

    public void unSubscription()
    {
        if (null != subscription && !subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViewsAndEvents(View rootView);


    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewId();

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
