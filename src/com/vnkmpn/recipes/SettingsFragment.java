package com.vnkmpn.recipes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {


	public static final String KEY_PREF_FTPPW = "pref_FTPPassword";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        getActivity().setResult(Activity.RESULT_CANCELED);
		Preference ftpPasswordPref = findPreference(KEY_PREF_FTPPW);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        ftpPasswordPref.setSummary(prefs.getString(KEY_PREF_FTPPW, ""));
    }
	
	public void onResume(){
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
		.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
		.unregisterOnSharedPreferenceChangeListener(this);
		getActivity().finish();
	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_FTPPW)) {
        	Log.d("Settings Fragment", "detected password change");
            Preference ftpPasswordPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            ftpPasswordPref.setSummary(sharedPreferences.getString(key, ""));
            getActivity().setResult(Activity.RESULT_OK);
        }
    }
	
	
	
}
