package com.example.huolongluo.sample.base;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 * <p/>
 * Class Note:
 * MVP中所有 View的接口
 */

public interface BaseView
{
    void showMessage(String msg, double seconds);

    void close();

    void showProgress(String msg);

    void showProgress(String msg, int progress);

    void hideProgress();

    void showErrorMessage(String msg, String content);
}
