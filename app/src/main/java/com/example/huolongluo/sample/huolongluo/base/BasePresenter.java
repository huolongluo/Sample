package com.example.huolongluo.sample.huolongluo.base;

import com.example.huolongluo.sample.base.BaseView;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 * <p>
 * Class Note:
 * MVP中所有Presenter的接口，完成view的绑定和解除
 */

public interface BasePresenter<T extends BaseView>
{
    /**
     * 注入View，使之能够与View相互响应
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 释放资源
     */
    void detachView();
}
