package com.serhii.strila.testproject.ui.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.serhii.strila.testproject.App;
import com.serhii.strila.testproject.di.ApplicationComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Serhii Strila on 12/16/15
 */
abstract public class BaseFragment extends RxFragment {

    @Inject
    Gson mGson;
    @Inject
    Realm mRealm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public ApplicationComponent getComponent() {
        return ((App) getActivity().getApplication()).getComponent();
    }
}
