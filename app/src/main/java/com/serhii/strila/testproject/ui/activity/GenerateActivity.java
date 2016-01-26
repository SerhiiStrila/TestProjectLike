package com.serhii.strila.testproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.serhii.strila.testproject.R;

import org.testpackage.test_sdk.android.testlib.API;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenerateActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_generate)
    void generatePersons() {
        API.INSTANCE.refreshPersons(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
