package com.serhii.strila.testproject;


import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

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

    @Nullable
    public static LatLng getCoordinates(String coordinates) {
        String[] coord = coordinates.split(",");
        if (coord.length == 2) {
            try {
                return new LatLng(Double.parseDouble(coord[0]),
                        Double.parseDouble(coord[1]));
            } catch (ClassCastException exp) {
                exp.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
