package com.example.huolongluo.sample.huolongluo.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.huolongluo.sample.R;
import com.example.huolongluo.sample.huolongluo.base.BaseDialog2;
import com.example.huolongluo.sample.util.RxCountDown;

import rx.Observer;

/**
 * Created by SLAN on 2017/04/13.
 */

public class TipDialog extends BaseDialog2
{
    public TipDialog(@NonNull Context context)
    {
        super(context, R.style.MyAlertDialog);
        initDialog();
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.dialog_tip;
    }

    @Override
    protected void injectDagger()
    {

    }

    protected void initViewsAndEvents()
    {
        setCancelable(false);
        // 设置触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(false);

        TextView tv_back_tip = (TextView) findViewById(R.id.tv_back_tip);

        RxCountDown.countdown(2).doOnSubscribe(() ->
        {
        }).subscribe(new Observer<Integer>()
        {
            @Override
            public void onCompleted()
            {
                dismiss();
            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onNext(Integer integer)
            {
                if (null != tv_back_tip)
                {
                    tv_back_tip.setText(integer + "S后自动返回...");
                }
            }
        });
    }
}
