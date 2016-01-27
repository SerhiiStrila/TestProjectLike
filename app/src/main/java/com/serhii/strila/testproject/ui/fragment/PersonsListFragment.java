package com.serhii.strila.testproject.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.Utils;
import com.serhii.strila.testproject.model.Person;
import com.serhii.strila.testproject.ui.activity.GenerateActivity;
import com.serhii.strila.testproject.ui.activity.MatchActivity;
import com.serhii.strila.testproject.ui.adapter.PersonAdapter;
import com.serhii.strila.testproject.ui.listener.EndlessListener;
import com.serhii.strila.testproject.ui.listener.OnItemClickListener;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;

public class PersonsListFragment extends BaseFragment implements RealmChangeListener,
        OnItemClickListener {

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
        mRealm.addChangeListener(this);
        return rootView;
    }

    @Override
    public void onChange() {
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getDataSize() == 0) {
            GenerateActivity.startActivity(getContext());
            mScrollListener.reLoad();
        }
    }

    @Override
    public void onDestroyView() {
        mRealm.removeChangeListener(this);
        super.onDestroyView();
    }

    @Override
    public void onLikeClick(int position) {
        Person person = mAdapter.getItem(position);
        if (Person.Status.valueOf(person.getStatus().toUpperCase()) == Person.Status.LIKE) {
            MatchActivity.startActivity(getContext(), person.getPhoto());
        }
        mRealm.executeTransaction(realm -> mAdapter.getItem(position).removeFromRealm());
    }

    @Override
    public void onDislikeClick(int position) {
        mRealm.executeTransaction(realm -> mAdapter.getItem(position).removeFromRealm());
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new PersonAdapter(mRealm.where(Person.class).findAllSorted("id"));
        mAdapter.setOnClickListener(this);
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
        mAdapter.setFooterVisibility(true);
        Utils.getPersons(page)
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .compose(Utils.applySchedulers())
                .map(json -> mGson.<List<Person>>fromJson(json, new TypeToken<ArrayList<Person>>() {
                }.getType()))
                .doOnNext(per -> mAdapter.setFooterVisibility(false))
                .doOnError(per -> mAdapter.setFooterVisibility(false))
                .subscribe(persons -> {
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(persons);
                    mRealm.commitTransaction();
                }, Throwable::printStackTrace);
    }
}
