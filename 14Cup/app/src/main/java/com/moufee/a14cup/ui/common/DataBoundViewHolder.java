package com.moufee.a14cup.ui.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ben on 3/8/18.
 */

public abstract class DataBoundViewHolder<B extends ViewDataBinding, O> extends RecyclerView.ViewHolder {
    public final B mBinding;

    public DataBoundViewHolder(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    abstract public void bind(O object);
}
