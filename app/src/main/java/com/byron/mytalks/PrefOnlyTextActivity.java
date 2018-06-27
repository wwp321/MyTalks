package com.byron.mytalks;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class PrefOnlyTextActivity extends AppCompatActivity {
    static public final String DISPLAY_NAME = "Title";
    static public final String DISPLAY_CONTENT = "Content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses_detail);

        Intent intent = getIntent();

        String licenseName = intent.getStringExtra(DISPLAY_NAME);
        String licenseDetail = intent.getStringExtra(DISPLAY_CONTENT);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(licenseName);
        }

        TextView licenseTextView = findViewById(R.id.licenses_detail);
        licenseTextView.setText((licenseDetail));
    }
}
