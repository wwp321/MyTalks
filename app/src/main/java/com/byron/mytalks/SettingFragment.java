package com.byron.mytalks;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment {

    private final String TAG = "Byron:SettingFragment";
    private final String LAST_POSITION = "LAST_POSITION";

    private ListPreference prefAboutLicense;
    private ListPreference prefAboutCredits;
    private ListPreference prefAboutTed;
    private ListPreference prefAppLanguage;


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        PreferenceClickListener clickListener = new PreferenceClickListener();
        PreferenceChangeListener changeListener = new PreferenceChangeListener();

        prefAboutLicense = (ListPreference) findPreference(getResources().getString(R.string.pref_about_licenses_key));
        prefAboutCredits = (ListPreference) findPreference(getResources().getString(R.string.pref_about_credits_key));
        prefAboutTed = (ListPreference) findPreference(getResources().getString(R.string.pref_about_ted_key));

        prefAppLanguage = (ListPreference) findPreference(getResources().getString(R.string.pref_category_language_app_language_key));
        findPreference(getResources().getString(R.string.general_background_playback_key)).setOnPreferenceChangeListener(changeListener);
        findPreference(getResources().getString(R.string.pref_privacy_clr_watch_history_key)).setOnPreferenceClickListener(clickListener);
        findPreference(getResources().getString(R.string.pref_privacy_clear_search_history_key)).setOnPreferenceClickListener(clickListener);



        prefAboutLicense.setOnPreferenceClickListener(clickListener);
        prefAboutCredits.setOnPreferenceClickListener(clickListener);
        prefAboutTed.setOnPreferenceClickListener(clickListener);

        prefAppLanguage.setOnPreferenceChangeListener(changeListener);
    }

    class PreferenceChangeListener implements Preference.OnPreferenceChangeListener {

        /**
         * Called when a Preference has been changed by the user. This is
         * called before the state of the Preference is about to be updated and
         * before the state is persisted.
         *
         * @param preference The changed Preference.
         * @param newValue   The new value of the Preference.
         * @return True to update the state of the Preference with the new value.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference == prefAppLanguage){
                preference.setSummary(String.valueOf(newValue));
            } else if(preference == findPreference(getResources().getString(R.string.general_background_playback_key))){
                String stringValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(index >= 0? listPreference.getEntries()[index]:null);
            }

            return true;
        }
    }

    class PreferenceClickListener implements Preference.OnPreferenceClickListener {

        /**
         * Called when a Preference has been clicked.
         *
         * @param preference The Preference that was clicked.
         * @return True if the click was handled.
         */
        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference == prefAboutLicense) {
                prefAboutLicense.getDialog().hide();

                Intent intent = new Intent(getActivity(), LicensesActivity.class);
                startActivity(intent);

            } else if(preference == prefAboutCredits) {
                prefAboutCredits.getDialog().hide();
                Intent intent = new Intent(getActivity(), PrefOnlyTextActivity.class);
                intent.putExtra(PrefOnlyTextActivity.DISPLAY_NAME, prefAboutCredits.getTitle());
                intent.putExtra(PrefOnlyTextActivity.DISPLAY_CONTENT,prefAboutCredits.getSummary());
                startActivity(intent);
            } else if(preference == prefAboutTed) {
                prefAboutTed.getDialog().hide();
            } else if(preference == findPreference(getResources().getString(R.string.pref_privacy_clr_watch_history_key))) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.getDialog().hide();
                Snackbar.make(getView(),"Watch history cleared", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            } else if(preference == findPreference(getResources().getString(R.string.pref_privacy_clear_search_history_key))) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.getDialog().hide();
                Snackbar.make(getView(),"Search history cleared", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }

            return true;
        }
    }
}
