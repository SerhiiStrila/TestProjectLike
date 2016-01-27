package com.serhii.strila.testproject.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.model.Person;
import com.serhii.strila.testproject.ui.adapter.viewholder.FooterProgressViewHolder;
import com.serhii.strila.testproject.ui.adapter.viewholder.PersonViewHolder;
import com.serhii.strila.testproject.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class PersonAdapter extends UltimateAdapter<PersonViewHolder> implements
        UltimateAdapter.FooterInterface {

    private List<Person> mPersons;
    @Nullable
    private OnItemClickListener mListener;

    public PersonAdapter() {
        mPersons = new ArrayList<>();
    }

    public PersonAdapter(List<Person> persons) {
        mPersons = persons;
    }

    public void setItems(List<Person> persons) {
        mPersons = persons;
    }

    public void addItem(Person person) {
        mPersons.add(person);
    }

    public Person getItem(int position) {
        return mPersons.get(position);
    }

    public void setOnClickListener(@NonNull OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getDataSize() {
        return mPersons.size();
    }

    @Override
    public int getDataViewResId(int viewType) {
        return R.layout.item_person;
    }

    @Override
    public long getDataId(int dataPosition) {
        return mPersons.get(dataPosition).hashCode();
    }

    @Override
    public int getDataViewType(int dataPosition) {
        return 0;
    }

    @NonNull
    @Override
    public PersonViewHolder getDataViewHolder(@NonNull View v, int dataViewType) {
        return new PersonViewHolder(v, mListener);
    }

    @Override
    public void bindDataVH(@NonNull PersonViewHolder vh, int dataPosition) {
        vh.bind(mPersons.get(dataPosition));
    }

    @Override
    public FooterVH getFooterVH(View v) {
        return new FooterProgressViewHolder(v);
    }

    @Override
    public int getFooterViewResId() {
        return R.layout.item_footer_progress;
    }

    @Override
    public void bindFooterVH(FooterVH vh) {

    }
}
