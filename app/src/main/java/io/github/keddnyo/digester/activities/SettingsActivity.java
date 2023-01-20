package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.FOUR_PDA_PROFILE_URL;
import static io.github.keddnyo.digester.repositories.Constants.GITHUB_PROFILE_URL;
import static io.github.keddnyo.digester.repositories.Constants.GITHUB_REPOSITORY_URL;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.utils.Application;
import io.github.keddnyo.digester.utils.URLOpener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference aboutPreference = findPreference("settings_about");

            if (aboutPreference != null) {
                CharSequence title = aboutPreference.getTitle();
                try {
                    String appVersion = Application.getVersionName(requireActivity());
                    aboutPreference.setTitle(title + " " + appVersion);

                    Context context = requireActivity();

                    aboutPreference.setOnPreferenceClickListener(v -> {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle(title + " " + appVersion);
                        alertDialogBuilder.setMessage(R.string.about_message);
                        alertDialogBuilder.setPositiveButton(R.string.github, (dialog, which) -> new URLOpener().openUrl(context, GITHUB_PROFILE_URL));
                        alertDialogBuilder.setNegativeButton(R.string.four_pda, (dialog, which) -> new URLOpener().openUrl(context, FOUR_PDA_PROFILE_URL));
                        alertDialogBuilder.setNeutralButton(R.string.repository, (dialog, which) -> new URLOpener().openUrl(context, GITHUB_REPOSITORY_URL));
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.show();
                        return true;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}