package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_ID;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.HAS_APPS;
import static io.github.keddnyo.digester.repositories.Constants.RECURSIVE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.requests.DigestRequest;
import io.github.keddnyo.digester.utils.DateUtils;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String forumTitle = intent.getStringExtra(FORUM_TITLE);
        String forumSubtitle = intent.getStringExtra(FORUM_SUBTITLE);
        int forumId = intent.getIntExtra(FORUM_ID, 212);
        int recursive = intent.getIntExtra(RECURSIVE, 0);
        boolean hasApps = intent.getBooleanExtra(HAS_APPS, false);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.specify_date_range));
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(forumTitle + " - " + forumSubtitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText requestPeriodStart, requestPeriodEnd;
        Button requestSubmitButton;

        requestPeriodStart = findViewById(R.id.requestPeriodStart);
        requestPeriodEnd = findViewById(R.id.requestPeriodEnd);
        requestSubmitButton = findViewById(R.id.requestSubmitButton);

        requestSubmitButton.setOnClickListener(v -> {
            String periodStart, periodEnd;

            periodStart = requestPeriodStart.getText().toString();
            periodEnd = requestPeriodEnd.getText().toString();

            String dateIncorrect = getString(R.string.date_incorrect);

            DateUtils dateUtils = new DateUtils();

            if (dateUtils.isDateInvalid(periodStart)) {
                requestPeriodStart.setError(dateIncorrect);
            } else if (dateUtils.isDateInvalid(periodEnd)) {
                requestPeriodEnd.setError(dateIncorrect);
            } else {
                new DigestRequest(this, forumId, forumTitle, forumSubtitle, recursive, hasApps, periodStart, periodEnd).getDigest();
                Toast.makeText(this, getString(R.string.request_sent), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}