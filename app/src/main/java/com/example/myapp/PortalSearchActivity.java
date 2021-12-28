package com.example.myapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PortalSearchActivity extends AppCompatActivity {
    Button Filesbtn,Studygroupsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_search);
        Filesbtn=findViewById(R.id.Files);
        Studygroupsbtn=findViewById(R.id.studyGroup);
        Filesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalSearchActivity.this,SearchActivity.class));
            }
        });
        Studygroupsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalSearchActivity.this,SearchGroupsActivity.class));
            }
        });

    }
}