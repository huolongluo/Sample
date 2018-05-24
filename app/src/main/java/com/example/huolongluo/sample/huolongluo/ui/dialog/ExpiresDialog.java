package com.example.huolongluo.sample.huolongluo.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.huolongluo.sample.R;
import com.example.huolongluo.sample.huolongluo.base.BaseDialog2;
import com.example.huolongluo.sample.manager.AppManager;
import com.example.huolongluo.sample.util.RxCountDown;
import com.example.huolongluo.sample.util.ToastSimple;

import butterknife.BindView;
import rx.Observer;

/**
 * Created by SLAN on 2017/04/28.
 */

public class ExpiresDialog extends BaseDialog2
{
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_time_tip)
    TextView tv_time_tip;
    @BindView(R.id.view1)
    View view1;

    private int time;

    public ExpiresDialog(@NonNull Context context, int time)
    {
        super(context, R.style.MyAlertDialog);
        this.time = time;
        initDialog();
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.dialog_expires;
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

        if (0 < time && time < 8)
        {
            tv_time_tip.setText("剩余时间：" + time + " 天");

            RxCountDown.countdown(4).doOnSubscribe(() ->
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
                }
            });
        }
        else
        {
            tv_tip.setText("您的使用权限已经到期请尽快充值！");
            tv_time_tip.setText("到期提醒");
            view1.setVisibility(View.VISIBLE);
            eventClick(tv_time_tip).subscribe(aVoid ->
            {
                ToastSimple.show("点击了 tv_time_tip", 1);
            });
        }
    }


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (System.currentTimeMillis() - mExitTime > 2000)
            {
                showMessage("再按一次退出程序", 2);
                mExitTime = System.currentTimeMillis();
            }
            else
            {
                AppManager.get().AppExit(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
