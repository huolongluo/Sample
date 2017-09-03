package com.example.huolongluo.sample.util;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.huolongluo.sample.base.s;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SLAN on 2016/6/1.
 * <p>
 * 使用示例1 ToastSimple.show("简单Toast，执行时间为：" + time, time).show();
 * <p>
 * 使用示例2 ToastSimple.makeText("简单Toast，执行时间为：" + time, time, view).show();
 */
public class ToastSimple
{

    private double time;
    private static Handler handler;
    private Timer showTimer;
    private Timer cancelTimer;

    private Toast toast;
    private static int mGravity;

    private ToastSimple()
    {
        showTimer = new Timer();
        cancelTimer = new Timer();
        mGravity = Gravity.CENTER;
    }

    private void setTime(double time)
    {
        this.time = time;
    }

    private void setToast(Toast toast)
    {
        this.toast = toast;
    }

    private void setToast(Toast toast, View view)
    {
        this.toast = toast;
        toast.setView(view);
        toast.setGravity(mGravity, 0, 0);
    }

    /**
     * 只显示文字内容的Toast
     *
     * @param text    文字内容
     * @param time    时间，单位为s
     * @return
     */
    private static ToastSimple makeText(CharSequence text, double time)
    {
        ToastSimple toast1 = new ToastSimple();
        toast1.setTime(time);
        toast1.setToast(Toast.makeText(s.app(), text, Toast.LENGTH_SHORT));
        handler = new Handler(s.app().getMainLooper());
        return toast1;
    }

    /**
     * 可以自定义view的Toast
     *
     * @param gravity 显示的位置
     * @param time    时间，单位为s
     * @param view    要加载的自定义的view，默认显示在中间
     * @return
     */
    public static ToastSimple makeText(int gravity, double time, View view)
    {
        mGravity = gravity;
        ToastSimple toast1 = new ToastSimple();
        toast1.setTime(time);
        toast1.setToast(Toast.makeText(s.app(), "", Toast.LENGTH_SHORT), view);
        handler = new Handler(s.app().getMainLooper());
        return toast1;
    }

    /**
     * Toast显示
     */
    public void show()
    {
        toast.show();
        if (time > 2)
        {
            showTimer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    handler.post(new ShowRunnable());
                }
            }, 0, 1900);
        }
        cancelTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new CancelRunnable());
            }
        }, (long) (time * 1000));
    }

    private class CancelRunnable implements Runnable
    {
        @Override
        public void run()
        {
            showTimer.cancel();
            toast.cancel();
        }
    }

    private class ShowRunnable implements Runnable
    {
        @Override
        public void run()
        {
            toast.show();
        }
    }

    public static void show(CharSequence text, double time)
    {
        ToastSimple.makeText(text, time).show();
    }
}