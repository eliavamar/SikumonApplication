package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class PortalActivity extends AppCompatActivity {
    Button study,upload,favorite,search,myStudyGroups,myFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        study=findViewById(R.id.StudyGroup);
        upload=findViewById(R.id.Upload);
        favorite=findViewById(R.id.Favorite);
        search=findViewById(R.id.Search);
        myStudyGroups=findViewById(R.id.studyGroup);
        myFiles=findViewById(R.id.myFiles);
        myFiles.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           startActivity(new Intent(PortalActivity.this, MyFiles.class));

                                       }
                                   });
        myStudyGroups.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PortalActivity.this, MyStudyGroups.class));
                    }
                });
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,StudyActivity.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,PortalSearchActivity.class));
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,FavoriteActivity.class));
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PortalActivity.this,UploadActivity.class));
            }
        });
    }

}