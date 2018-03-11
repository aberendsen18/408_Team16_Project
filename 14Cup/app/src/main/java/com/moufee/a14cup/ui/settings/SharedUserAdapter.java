package com.moufee.a14cup.ui.settings;


import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.SimpleStringListItemBinding;

/**
 * Created by Ben on 3/8/18.
 */

public class SharedUserAdapter extends ListAdapter<String, EmailAddressViewHolder> {

    protected SharedUserAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EmailAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EmailAddressViewHolder(SimpleStringListItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmailAddressViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
