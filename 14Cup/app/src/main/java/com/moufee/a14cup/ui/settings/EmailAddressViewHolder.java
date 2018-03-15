package com.moufee.a14cup.ui.settings;

import com.moufee.a14cup.databinding.SimpleStringListItemBinding;
import com.moufee.a14cup.ui.common.DataBoundViewHolder;

/**
 * Created by Ben on 3/8/18.
 */

public class EmailAddressViewHolder extends DataBoundViewHolder<SimpleStringListItemBinding, String> {

    public EmailAddressViewHolder(SimpleStringListItemBinding binding) {
        super(binding);
    }

    @Override
    public void bind(String title) {
        mBinding.setString(title);
        mBinding.executePendingBindings();
    }
}
