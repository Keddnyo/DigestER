package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import io.github.keddnyo.digester.R;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Intent intent = getIntent();

        Objects.requireNonNull(getSupportActionBar()).setTitle(intent.getStringExtra(FORUM_TITLE));
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(intent.getStringExtra(FORUM_SUBTITLE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String digestResponse = intent.getStringExtra(RESPONSE);

        TextView digestResponseTextView = findViewById(R.id.digestResponse);
        digestResponseTextView.setText(digestResponse);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}