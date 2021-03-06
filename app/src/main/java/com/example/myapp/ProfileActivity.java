package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView followers,following,phone,email,department,orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        followers=(TextView) findViewById(R.id.followers);
        following=(TextView) findViewById(R.id.following);
        phone=(TextView) findViewById(R.id.phone);
        email=(TextView) findViewById(R.id.email);
        department=(TextView) findViewById(R.id.department);
        orientation=(TextView) findViewById(R.id.orientation);
        DB.fillProfile(orientation,email,phone,followers,following,department);
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
                startActivity(new Intent(ProfileActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(ProfileActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(ProfileActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(ProfileActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(ProfileActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}