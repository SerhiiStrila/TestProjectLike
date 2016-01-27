package com.serhii.strila.testproject.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.serhii.strila.testproject.EndlessListener;
import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.Utils;
import com.serhii.strila.testproject.model.Person;
import com.serhii.strila.testproject.ui.adapter.PersonAdapter;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonsListFragment extends BaseFragment {

    @Bind(R.id.rv_users)
    RecyclerView mRvUsers;

    private PersonAdapter mAdapter;
    private EndlessListener mScrollListener;

    public static PersonsListFragment newInstance() {
        PersonsListFragment fragment = new PersonsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_persons_list, container, false);
        ButterKnife.bind(this, rootView);
        initList();
        loadPersons(0);
        return rootView;
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new PersonAdapter(mRealm.where(Person.class).findAll());
        mRvUsers.setHasFixedSize(true);
        mRvUsers.setLayoutManager(layoutManager);
        mRvUsers.setAdapter(mAdapter);
        mScrollListener = new EndlessListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadPersons(currentPage);
            }
        };
        mRvUsers.addOnScrollListener(mScrollListener);
    }

    private void loadPersons(int page) {
        Utils.getPersons(page)
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .compose(Utils.applySchedulers())
                .map(json -> mGson.<List<Person>>fromJson(json, new TypeToken<ArrayList<Person>>() {
                }.getType()))
                .subscribe(persons -> {
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(persons);
                    mRealm.commitTransaction();
                }, Throwable::printStackTrace);
    }
}
