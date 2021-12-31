package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
                                           startActivity(new Intent(PortalActivity.this, MyFilesActivity.class));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.upload:
                startActivity(new Intent(PortalActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(PortalActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(PortalActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(PortalActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(PortalActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(PortalActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}