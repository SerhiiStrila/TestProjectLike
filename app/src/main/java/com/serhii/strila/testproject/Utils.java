package com.serhii.strila.testproject;


import org.testpackage.test_sdk.android.testlib.API;
import org.testpackage.test_sdk.android.testlib.interfaces.PersonsExtendedCallback;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Serhii Strila on 1/27/16
 */
public class Utils {

    public static Observable<String> getPersons(int page) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                API.INSTANCE.getPersons(page, new PersonsExtendedCallback() {
                    @Override
                    public void onResult(String persons) {
                        subscriber.onNext(persons);
                    }

                    @Override
                    public void onFail(String reason) {
                        subscriber.onError(new Throwable(reason));
                    }
                });
            }
        });
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
