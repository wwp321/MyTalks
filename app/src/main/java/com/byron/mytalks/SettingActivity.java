package com.byron.mytalks;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class SettingActivity extends AppCompatActivity {
    private final String TAG = "Byron:SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getFragmentManager().beginTransaction().replace(R.id.setting_content, new SettingFragment()).commit();

        Toolbar toolbar = findViewById(R.id.setting_toolbar);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
