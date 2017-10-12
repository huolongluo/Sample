package com.example.huolongluo.sample.superAdapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Base of QuickAdapter.
 * Created by Cheney on 15/11/28.
 */
public abstract class BaseSuperAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H>
{
    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mList;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected LayoutInflater mLayoutInflater;

    public BaseSuperAdapter(Context context, List<T> list, int layoutResId)
    {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<T>() : new ArrayList<T>(list);
        this.mLayoutResId = layoutResId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseSuperAdapter(Context context, List<T> data, IMultiItemViewType<T> multiItemViewType)
    {
        this.mContext = context;
        this.mList = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mMultiItemViewType = multiItemViewType;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mMultiItemViewType != null)
        {
            return mMultiItemViewType.getItemViewType(position, mList.get(position));
        }
        return 0;
    }

    @Override
    public int getItemCount()
    {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(H holder, int position)
    {
        onBind(getItemViewType(position), holder, position, mList.get(position));
    }

    public abstract H onCreate(ViewGroup parent, int viewType);

    /**
     * Abstract method for binding view and data.
     *
     * @param viewType {@link #getItemViewType}
     * @param holder   ViewHolder
     * @param position position
     * @param item     data
     */
    public abstract void onBind(int viewType, H holder, int position, T item);

    public void add(T item)
    {
        add(item, true);
    }

    public void add(T item, boolean isChanged)
    {
        if (mList == null)
        {
            mList = new ArrayList<T>();
        }
        mList.add(item);
        if (isChanged)
        {
            notifyDataSetChanged();
        }
    }

    public void add(int index, T item)
    {
        mList.add(index, item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> items)
    {
        if (items == null || items.size() == 0)
        {
            return;
        }
        if (mList == null)
        {
            mList = new ArrayList<T>();
        }
        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(T item)
    {
        mList.remove(item);
        notifyDataSetChanged();
    }

    public void remove(int index)
    {
        mList.remove(index);
        notifyDataSetChanged();
    }

    public void remove(int index, boolean isChange)
    {
        mList.remove(index);
        if (isChange)
        {
            notifyDataSetChanged();
        }
    }

    public void set(T oldItem, T newItem)
    {
        set(mList.indexOf(oldItem), newItem);
    }

    public void set(int index, T item)
    {
        mList.set(index, item);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> items)
    {
        mList.clear();
        addAll(items);
    }

    public boolean contains(T item)
    {
        return mList.contains(item);
    }

    public void clear()
    {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<T> getAllData()
    {
        return mList;
    }

    public Observable<Void> eventClick(View view)
    {
        return eventClick(view, 1000);
    }

    public Observable<Void> eventClick(View view, int milliseconds)
    {
        return RxView.clicks(view).throttleFirst(milliseconds, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
    }

}
