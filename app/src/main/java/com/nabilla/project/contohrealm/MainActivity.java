package com.nabilla.project.contohrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nabilla.project.contohrealm.adapter.RecyclerviewAdapter;
import com.nabilla.project.contohrealm.model.ModelBarang;
import com.nabilla.project.contohrealm.realm.RealmDatabase;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RealmDatabase realmDatabase;
    private RecyclerviewAdapter recyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realmDatabase = new RealmDatabase(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
        getData();
    }

    private RealmResults<ModelBarang> getData(){
        RealmResults<ModelBarang> modelBarangs = (RealmResults<ModelBarang>) new RealmDatabase(this).getData(ModelBarang.class);

        recyclerviewAdapter = new RecyclerviewAdapter(this, modelBarangs);
        recyclerView.setAdapter(recyclerviewAdapter);

        return modelBarangs;
    }

}
