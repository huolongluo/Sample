package com.example.huolongluo.sample.superAdapter.list;

/**
 * Interface for multiple types.
 */
public interface IMultiItemViewType<T>
{
    int getViewTypeCount();

    int getItemViewType(int position, T t);

    int getLayoutId(int viewType);
}