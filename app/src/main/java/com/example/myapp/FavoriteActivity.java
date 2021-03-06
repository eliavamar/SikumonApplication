package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView listView=(ListView) findViewById(R.id.list_view);
        DB.favoriteList(listView,FavoriteActivity.this);
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
                startActivity(new Intent(FavoriteActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(FavoriteActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(FavoriteActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(FavoriteActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(FavoriteActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(FavoriteActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}