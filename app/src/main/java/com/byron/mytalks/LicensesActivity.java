package com.byron.mytalks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class LicensesActivity extends AppCompatActivity {


    @BindView(R.id.licenses_list)
    ListView LicesenseListView;
    @BindView(R.id.licenses_toolbar)
    Toolbar licensesToolbar;
    private String[] licenseList = {"RxJava", "RxAndroid", "Picasso", "Butter Knife", "Timber",
            "OkHttp", "Dagger 2", "Calligraphy", "Android SQLiteAssetHelper", "ExoPlayer"};

    private String[] licenseDetail = {
            "Copyright (c) 2016-present, RxJava Contributors." +
                    "\n\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");" +
                    "you may not use this file except in compliance with the License." +
                    "You may obtain a copy of the License at" +
                    "\n\n" +
                    "http://www.apache.org/licenses/LICENSE-2.0" +
                    "\n\n" +
                    "Unless required by applicable law or agreed to in writing, software" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS," +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." +
                    "See the License for the specific language governing permissions and" +
                    "limitations under the License.",
            "RxAndroid...",
            "Picasso...",
            "Butter Knife...",
            "Timber...",
            "OkHttp...",
            "Dagger 2...",
            "Calligraphy...",
            "Android SQLiteAssetHelper...",
            "ExoPlayer..."
    };
    private final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
        ButterKnife.bind(this);

        setSupportActionBar(licensesToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.pref_about_licenses);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LicensesActivity.this, android.R.layout.simple_list_item_1, licenseList);
        LicesenseListView.setAdapter(adapter);

    }

    @OnItemClick(R.id.licenses_list)
    public void onItemClickListener(int position) {
        Intent intent = new Intent(LicensesActivity.this, PrefOnlyTextActivity.class);
        intent.putExtra(PrefOnlyTextActivity.DISPLAY_NAME, licenseList[position]);
        intent.putExtra(PrefOnlyTextActivity.DISPLAY_CONTENT, licenseDetail[position]);
        startActivity(intent);
    }
}
