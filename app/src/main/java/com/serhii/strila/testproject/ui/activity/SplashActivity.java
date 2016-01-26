package com.serhii.strila.testproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.model.Person;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class SplashActivity extends BaseActivity {

    public static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable.just(mRealm.where(Person.class).findAll().isEmpty())
                .compose(bindToLifecycle())
                .delay(SPLASH_TIME, TimeUnit.MILLISECONDS)
                .doOnNext(empty -> startActivity(
                        new Intent(this, empty ? GenerateActivity.class : MainActivity.class)))
                .subscribe(empty -> finish());
    }
}
