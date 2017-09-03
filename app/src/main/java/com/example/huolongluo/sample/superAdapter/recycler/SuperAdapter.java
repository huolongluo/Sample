package com.example.huolongluo.sample.superAdapter.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * adapter that should be used.
 * Created by Cheney on 15/11/27.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T, BaseViewHolder>
{

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View itemView, int viewType, int position);
    }

    /**
     * Constructor for single itemView type.
     */
    public SuperAdapter(Context context, List<T> list, int layoutResId)
    {
        super(context, list, layoutResId);
    }

    /**
     * Constructor for multiple itemView types.
     */
    public SuperAdapter(Context context, List<T> list, IMultiItemViewType<T> multiItemViewType)
    {
        super(context, list, multiItemViewType);
    }

    @Override
    public BaseViewHolder onCreate(ViewGroup parent, final int viewType)
    {
        final BaseViewHolder holder;
        if (mMultiItemViewType != null)
        {
            holder = new BaseViewHolder(mLayoutInflater.inflate(mMultiItemViewType.getLayoutId(viewType), parent, false));
        }
        else
        {
            holder = new BaseViewHolder(mLayoutInflater.inflate(mLayoutResId, parent, false));
        }

        holder.getItemView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    mOnItemClickListener.onItemClick(v, viewType, holder.getPosition());
                }
            }
        });
        return holder;
    }
}
