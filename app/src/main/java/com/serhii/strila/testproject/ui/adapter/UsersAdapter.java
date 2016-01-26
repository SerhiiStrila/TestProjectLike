package com.serhii.strila.testproject.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.serhii.strila.testproject.ui.adapter.viewholder.PersonViewHolder;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class UsersAdapter extends UltimateAdapter<PersonViewHolder> {

    @Override
    public int getDataSize() {
        return 0;
    }

    @Override
    public int getDataViewResId(int viewType) {
        return 0;
    }

    @Override
    public long getDataId(int dataPosition) {
        return 0;
    }

    @Override
    public int getDataViewType(int dataPosition) {
        return 0;
    }

    @NonNull
    @Override
    public PersonViewHolder getDataViewHolder(@NonNull View v, int dataViewType) {
        return null;
    }

    @Override
    public void bindDataVH(@NonNull PersonViewHolder vh, int dataPosition) {

    }
}
