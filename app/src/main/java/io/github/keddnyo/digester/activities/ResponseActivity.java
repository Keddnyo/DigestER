package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.DIGEST_HAS_NO_MORE_NEW_PROGRAMS;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_HAS_NO_MORE_UPDATES;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_TOPIC_LINK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.utils.Clipboard;

public class ResponseActivity extends AppCompatActivity {
    String digestTopicLink;
    String digestResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        TextView digestResponseTextView = findViewById(R.id.digestResponse);

        Intent intent = getIntent();
        digestResponse = intent.getStringExtra(RESPONSE);
        digestTopicLink = intent.getStringExtra(DIGEST_TOPIC_LINK);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        digestResponseTextView.setText(digestResponse);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (digestResponse.contains(DIGEST_HAS_NO_MORE_NEW_PROGRAMS) && digestResponse.contains(DIGEST_HAS_NO_MORE_UPDATES)) {
            menu.findItem(R.id.menuResponsePublish).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_response, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menuResponsePublish) {
            new Clipboard().copyText(this, RESPONSE, digestResponse);
            String textCopied = getString(R.string.response_copied_to_clipboard);
            Toast.makeText(this, textCopied, Toast.LENGTH_LONG).show();

            // Open digest topic on 4PDA
            Intent digestTopicIntent = new Intent(Intent.ACTION_VIEW);
            digestTopicIntent.setData(Uri.parse(digestTopicLink));
            startActivity(digestTopicIntent);
        } else {
            onBackPressed();
        }

        return true;
    }
}