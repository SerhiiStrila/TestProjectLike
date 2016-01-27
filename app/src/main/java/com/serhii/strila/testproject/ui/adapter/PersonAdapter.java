package com.serhii.strila.testproject.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.model.Person;
import com.serhii.strila.testproject.ui.adapter.viewholder.PersonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class PersonAdapter extends UltimateAdapter<PersonViewHolder> {

    private List<Person> mPersons;

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
        return new PersonViewHolder(v);
    }

    @Override
    public void bindDataVH(@NonNull PersonViewHolder vh, int dataPosition) {
        vh.bind(mPersons.get(dataPosition));
    }
}
