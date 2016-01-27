package com.serhii.strila.testproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.Utils;
import com.trello.rxlifecycle.ActivityEvent;

import org.testpackage.test_sdk.android.testlib.API;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import rx.Observable;
import rx.Subscriber;

public class GenerateActivity extends BaseActivity {

    private static final int TIME_INTERVAL = 2000;

    @Bind(R.id.btn_generate)
    Button mBtnGenerate;
    @Bind(R.id.progress_bar)
    MaterialProgressBar mProgressBar;

    private long mBackPressed;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, GenerateActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            ActivityCompat.finishAffinity(this);
            return;
        }
        Toast.makeText(this, R.string.generate_activity_again, Toast.LENGTH_SHORT).show();
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_generate)
    void generatePersons() {
        loading(true);
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                API.INSTANCE.refreshPersons(() -> subscriber.onNext(true));
            }
        })
                .compose(Utils.applySchedulers())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .doOnNext(loaded -> loading(!loaded))
                .subscribe(loaded -> startActivity(new Intent(this, MainActivity.class)));

    }

    private void loading(boolean loading) {
        mBtnGenerate.setEnabled(!loading);
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }
}
