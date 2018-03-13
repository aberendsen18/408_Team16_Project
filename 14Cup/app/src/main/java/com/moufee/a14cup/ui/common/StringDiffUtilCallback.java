package com.moufee.a14cup.ui.common;

import android.support.v7.util.DiffUtil;

/**
 * Created by Ben on 3/8/18.
 */

public class StringDiffUtilCallback extends DiffUtil.ItemCallback<String> {
    @Override
    public boolean areItemsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }
}
