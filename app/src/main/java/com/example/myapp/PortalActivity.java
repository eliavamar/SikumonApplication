package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PortalActivity extends AppCompatActivity {
    Button study,upload,favorite,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        study=findViewById(R.id.StudyGroup);
        upload=findViewById(R.id.Upload);
        favorite=findViewById(R.id.Favorite);
        search=findViewById(R.id.Search);
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,StudyActivity.class));
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,SearchActivity.class));
                finish();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,FavoriteActivity.class));
                finish();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,UploadActivity.class));
                finish();
            }
        });
    }
}