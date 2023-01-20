package io.github.keddnyo.digester.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.adapters.RecyclerViewAdapter;
import io.github.keddnyo.digester.repositories.ForumSections;
import io.github.keddnyo.digester.requests.UpdateRequest;
import io.github.keddnyo.digester.utils.AsyncTask;
import io.github.keddnyo.digester.utils.DownloadUtil;

public class MainActivity extends AppCompatActivity implements AsyncTask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.select_forum_section));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.addForums(ForumSections.getForumArrayList());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.getBoolean("settings_check_app_updates", true)) {
            new UpdateRequest(this).checkForUpdates();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menuMainSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    public void showUpdateDialog(String updateUrl, String name, String updateVersion) {
        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        String label = getString(R.string.update_is_available, updateVersion);
        Snackbar updateIsAvailableSnackbar = Snackbar.make(rootView, label, Snackbar.LENGTH_INDEFINITE);
        updateIsAvailableSnackbar.setAction(R.string.update_download, v -> {
            new DownloadUtil(this).downloadFile(updateUrl, name);
            Snackbar updateIsDownloadingSnackbar = Snackbar.make(rootView, R.string.update_is_downloading, Snackbar.LENGTH_LONG);
            updateIsDownloadingSnackbar.show();
        });
        updateIsAvailableSnackbar.show();
    }
}