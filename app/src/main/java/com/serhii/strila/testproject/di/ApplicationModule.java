package com.serhii.strila.testproject.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serhii.strila.testproject.App;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Serhii Strila on 1/26/16
 */
@Module
public class ApplicationModule {

    private App mApp;

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @PerApp
    App provideApplication() {
        return mApp;
    }

    @Provides
    Realm provideRealm(App app) {
        RealmConfiguration configuration = new RealmConfiguration.Builder(app)
                .name("TestData")
                .schemaVersion(1)
                .build();
        Realm realm = Realm.getInstance(configuration);
        realm.setAutoRefresh(true);
        return realm;
    }

    @Provides
    @PerApp
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
