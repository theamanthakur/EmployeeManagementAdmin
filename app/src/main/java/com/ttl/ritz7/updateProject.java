package com.ttl.ritz7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class updateProject extends AppCompatActivity {
    RecyclerView recyclerView;
    projectAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);
        recyclerView =(RecyclerView) this.findViewById(R.id.recyclerProject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<modelProject> options = new FirebaseRecyclerOptions.Builder<modelProject>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Project"),modelProject.class)
                .build();
        adapter = new projectAdapter(options,this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}