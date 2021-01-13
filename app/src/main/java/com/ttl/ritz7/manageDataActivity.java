package com.ttl.ritz7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manageDataActivity extends AppCompatActivity {
        Button btnTask,btnPro,btnCli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);
        btnTask = findViewById(R.id.btntaskUpdate);
        btnCli = findViewById(R.id.btnCliUpdate);
        btnPro  = findViewById(R.id.btnProUpdate);
        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manageDataActivity.this,updateTask.class));
            }
        });
        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manageDataActivity.this,updateProject.class));
            }
        });
        btnCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manageDataActivity.this,updateClient.class));
            }
        });
    }
}