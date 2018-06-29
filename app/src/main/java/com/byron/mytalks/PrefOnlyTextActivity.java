package com.byron.mytalks;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrefOnlyTextActivity extends AppCompatActivity {
    static public final String DISPLAY_NAME = "Title";
    static public final String DISPLAY_CONTENT = "Content";

    @BindView(R.id.pref_only_text_toolbar)
    Toolbar toolbar;

    @BindView(R.id.licenses_detail)
    TextView licenseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        String licenseName = intent.getStringExtra(DISPLAY_NAME);
        String licenseDetail = intent.getStringExtra(DISPLAY_CONTENT);

        toolbar.setTitle(licenseName);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        licenseTextView.setText((licenseDetail));
    }
}
