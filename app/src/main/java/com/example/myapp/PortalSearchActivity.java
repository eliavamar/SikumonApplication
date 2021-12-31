package com.example.myapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PortalSearchActivity extends AppCompatActivity {
    Button Filesbtn,Studygroupsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                startActivity(new Intent(PortalSearchActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(PortalSearchActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(PortalSearchActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(PortalSearchActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(PortalSearchActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(PortalSearchActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}