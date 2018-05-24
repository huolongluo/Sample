package com.example.huolongluo.sample.huolongluo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huolongluo.sample.R;
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

public abstract class BaseDialog extends DialogFragment implements BaseView
{
    /**
     * activity context of fragment
     */
    protected Activity mContext;
    public Subscription subscription;
    Unbinder unbinder;
    View view;
    protected int currentStyle = 0;
    public static final int ADD_GOODS = 0;
    public static final int SHOW_TIME = 3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyAlertDialog);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setDialog(currentStyle);

        if (getContentViewId() != 0)
        {
            return view = inflater.inflate(getContentViewId(), null);
        }
        else
        {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected View findViewById(int resId)
    {
        return view.findViewById(resId);
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
        unbinder.unbind();
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
     * set for choice witch dialog to show
     *
     * @return
     */
    public int setDialog(int style)
    {
        return currentStyle = style;
    }

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
