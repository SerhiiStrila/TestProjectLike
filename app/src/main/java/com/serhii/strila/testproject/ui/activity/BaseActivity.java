package com.serhii.strila.testproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.serhii.strila.testproject.App;
import com.serhii.strila.testproject.di.ApplicationComponent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by Serhii Strila on 12/16/15
 */
abstract public class BaseActivity extends RxAppCompatActivity {

    @Inject
    Realm mRealm;

    public ApplicationComponent getComponent() {
        return ((App) getApplication()).getComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

}
