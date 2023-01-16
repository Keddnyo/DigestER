package io.github.keddnyo.digester.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

}