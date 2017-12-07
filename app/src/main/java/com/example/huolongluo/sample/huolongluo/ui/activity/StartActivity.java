package com.example.huolongluo.sample.huolongluo.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.example.huolongluo.sample.R;
import com.example.huolongluo.sample.huolongluo.base.BaseActivity;
import com.example.huolongluo.sample.huolongluo.ui.activity.main.MainActivity;
import com.example.huolongluo.sample.util.DeviceUtils;
import com.example.huolongluo.sample.util.L;


/**
 * <p>
 * Created by 火龙裸 on 2017/9/3 0003.
 */

public class StartActivity extends BaseActivity
{
    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_start;
    }

    @Override
    protected void injectDagger()
    {

    }

    @Override
    protected void initViewsAndEvents()
    {
        L.d("分辨率：高 " + DeviceUtils.getScreenPix(this).heightPixels);
        L.d("分辨率：宽 " + DeviceUtils.getScreenPix(this).widthPixels);

        new Handler().postDelayed(() ->
        {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }, 1500);
    }
}
