package com.serhii.strila.testproject.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serhii.strila.testproject.EndlessListener;
import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.ui.adapter.UsersAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListUsersFragment extends Fragment {

    @Bind(R.id.rv_users)
    RecyclerView mRvUsers;

    private UsersAdapter mAdapter;
    private EndlessListener mScrollListener;

    public static ListUsersFragment newInstance() {
        ListUsersFragment fragment = new ListUsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_users, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initList() {
//        API.INSTANCE.getPersons();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new UsersAdapter();
//        mAdapter.setOnItemClickListener(this);
        mRvUsers.setHasFixedSize(true);
        mRvUsers.setLayoutManager(layoutManager);
        mRvUsers.setAdapter(mAdapter);
        mScrollListener = new EndlessListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {

            }
        };
        mRvUsers.addOnScrollListener(mScrollListener);
    }
}
