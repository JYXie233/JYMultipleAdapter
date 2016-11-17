package com.xjy.jymultipleadapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 15:10
 * FIXME
 */
public class MultipleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private SparseArray<View> mViews;

    private OnMultipleItemClickListener mOnMultipleItemClickListener;

    private OnMultipleItemLongClickListener mOnMultipleItemLongClickListener;


    public MultipleViewHolder(View itemView) {
        super(itemView);
        this.itemView.setOnClickListener(this);
        this.itemView.setOnLongClickListener(this);
        mViews = new SparseArray<View>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public MultipleViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public MultipleViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public MultipleViewHolder setClickListener(int viewId, final OnMultipleItemClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, MultipleViewHolder.this.getAdapterPosition() );
            }
        });
        return this;
    }

    public MultipleViewHolder setLongClickListener(int viewId, final OnMultipleItemLongClickListener listener) {
        View view = findViewById(viewId);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return listener.onItemLongClick(view, MultipleViewHolder.this.getAdapterPosition());
            }

        });
        return this;
    }

    @Override
    public void onClick(View view) {
        if (mOnMultipleItemClickListener != null){
            mOnMultipleItemClickListener.onClick(view, this.getAdapterPosition() );
        }
    }

    public void setOnMultipleItemClickListener(OnMultipleItemClickListener onMultipleItemClickListener) {
        mOnMultipleItemClickListener = onMultipleItemClickListener;
    }

    public void setOnMultipleItemLongClickListener(OnMultipleItemLongClickListener onMultipleItemLongClickListener) {
        mOnMultipleItemLongClickListener = onMultipleItemLongClickListener;
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnMultipleItemLongClickListener != null){
            return mOnMultipleItemLongClickListener.onItemLongClick(view, getAdapterPosition());
        }
        return false;
    }


}