package com.serhii.strila.testproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.serhii.strila.testproject.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MatchActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.img_photo)
    ImageView mImgPhoto;

    public static void startActivity(Context context, String photoUrl) {
        Intent intent = new Intent(context, MatchActivity.class);
        intent.setData(Uri.parse(photoUrl));
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        ButterKnife.bind(this);
        initToolbar();
        initBase();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initBase() {
        Picasso.with(this)
                .load(getIntent().getData())
                .placeholder(R.drawable.ic_person)
                .into(mImgPhoto);
    }
}
