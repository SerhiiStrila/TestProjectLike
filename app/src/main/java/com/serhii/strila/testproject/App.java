package com.serhii.strila.testproject;

import android.app.Application;

import com.serhii.strila.testproject.di.ApplicationComponent;
import com.serhii.strila.testproject.di.ApplicationModule;
import com.serhii.strila.testproject.di.DaggerApplicationComponent;

import org.testpackage.test_sdk.android.testlib.API;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class App extends Application {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        API.INSTANCE.init(this);
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
