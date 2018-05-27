package com.example.huolongluo.sample.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ${火龙裸} on 2017/10/27.
 * 邮箱：791335000@qq.com
 */
public class ImmersedStatusbarUtils
{
    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup)
    {
        if (activity == null)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = activity.getWindow();
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (titleViewGroup == null)
        {
            return;
        }
        // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
        int statusBarHeight = getStatusBarHeight(activity);
        titleViewGroup.setPadding(0, statusBarHeight, 0, 0);

    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        L.e("=============状态栏高度====：" + result);
        return result;
    }
}
