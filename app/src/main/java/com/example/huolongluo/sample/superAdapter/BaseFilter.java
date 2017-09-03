package com.example.huolongluo.sample.superAdapter;

import android.text.TextUtils;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SLAN on 2016/9/20.
 */

public abstract class BaseFilter<T> extends Filter
{
    private List<T> mUnfilteredData;

    public BaseFilter(List<T> data)
    {
        this.mUnfilteredData = new ArrayList<>(data);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
        FilterResults results = new FilterResults();
        if (!TextUtils.isEmpty(constraint))
        {
            String prefixString = constraint.toString().toLowerCase();
            ArrayList<T> newValues = (ArrayList<T>) onFilterData(prefixString, mUnfilteredData);
            results.values = newValues;
            results.count = newValues.size();
        }
        return results;
    }

    /**
     * 如果存在动态添加过滤数据，重新调用该方法，set数据即可
     */
    public void setUnfilteredData(List<T> data)
    {
        this.mUnfilteredData = new ArrayList<>(data);
    }


    /**
     * 因为筛选规则不是完全确定的，所以公开一个抽象方法，让子类去实现
     */
    public abstract List<T> onFilterData(String prefixString, List<T> unfilteredValues);
}
