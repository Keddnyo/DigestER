package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.GITHUB_REPOSITORY_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.adapters.RecyclerViewAdapter;
import io.github.keddnyo.digester.repositories.Forums;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.choose_forum));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.addForums(new Forums().getForumArrayList());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(GITHUB_REPOSITORY_URL));
        startActivity(intent);

        return true;
    }

}