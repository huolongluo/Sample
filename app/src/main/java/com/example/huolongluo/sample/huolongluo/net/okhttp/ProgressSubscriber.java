package com.example.huolongluo.sample.huolongluo.net.okhttp;

import android.content.Context;

import com.example.huolongluo.sample.huolongluo.manager.DialogManager;
import com.example.huolongluo.sample.huolongluo.net.BaseResponse;
import com.example.huolongluo.sample.util.L;
import com.example.huolongluo.sample.util.ToastSimple;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * <br>
 * HttpOnNextListener 无自定义出错处理 ，HttpOnNextListener2 有自定义出错处理
 * <br>
 * 在Http请求结束是，关闭ProgressDialog
 * <br>
 * 调用者自己对请求数据进行处理
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

public class ProgressSubscriber<T> extends Subscriber<T>
{
    //    回调接口  
    private HttpOnNextListener mSubscriberOnNextListener;
    private HttpOnNextListener2 mSubscriberOnNextListener2;
    private Context mContext;
    private boolean isShowDialog;

    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context mContext, boolean isShowDialog)
    {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
    }

    public ProgressSubscriber(HttpOnNextListener2 mSubscriberOnNextListener2, Context mContext, boolean isShowDialog)
    {
        this.mSubscriberOnNextListener2 = mSubscriberOnNextListener2;
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
    }


    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted()
    {
        DialogManager.INSTANCE.dismiss();
        L.d("--- onCompleted");
    }

    @Override
    public void onError(Throwable e)
    {
        if (e instanceof HttpException)
        {
            ToastSimple.show("服务繁忙，请稍后重试", 2);
        }
        else if (e instanceof SocketTimeoutException)
        {
            ToastSimple.show("网络出问题了，请稍后重试", 2);
        }
        else if (e instanceof ConnectException)
        {
            ToastSimple.show("连接出问题了，请稍后重试", 2);
        }
        else
        {
            L.e("错误: " + e.getMessage());
        }

        if (null != mSubscriberOnNextListener2)
        {
            mSubscriberOnNextListener2.onError(e);
        }
        DialogManager.INSTANCE.dismiss();
    }

    @Override
    public void onStart()
    {
        if (isShowDialog)
        {
            DialogManager.INSTANCE.showProgressDialog(mContext, "加载中");
        }
    }

    @Override
    public void onNext(T t)
    {
        L.d(t.toString());

        if (t instanceof BaseResponse)
        {
            BaseResponse response = (BaseResponse) t;

            if (response.getCode() == 0 && response.isSuccess())
            {
                if (null != mSubscriberOnNextListener)
                {
                    // noinspection unchecked
                    mSubscriberOnNextListener.onNext(response.getData());
                }

                if (null != mSubscriberOnNextListener2)
                {
                    // noinspection unchecked
                    mSubscriberOnNextListener2.onNext(response.getData());
                }
            }
            else
            {
                switch (response.getCode())
                {
                    case 10001:
                    case 10002:
                    case 10003:
                        ToastSimple.show(response.getMsg(), 2);
                        break;
                    case 3001:
                        ToastSimple.show("请重新登录授权", 2);
                        break;
                    case 3002:
                        ToastSimple.show("参数错误", 2);
                        break;
                    case 3003:
//                        ToastSimple.show("Token已过期，请重新登录饿了么", 3); //token认证失败
                        ToastSimple.show(response.getMsg(), 3); //token认证失败
                        break;
                    case 3004:
                        ToastSimple.show(response.getMsg(), 2);
                        break;
                    case 2001:
                        ToastSimple.show("服务器错误", 2);
                        break;
                    default:
                        ToastSimple.show(response.getMsg(), 2);
                        break;
                }
                if (null != mSubscriberOnNextListener2)
                {
                    // noinspection unchecked
                    mSubscriberOnNextListener2.onFail(response);
                }
            }

        }
    }
}
