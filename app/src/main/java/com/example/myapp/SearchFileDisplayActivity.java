package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFileDisplayActivity extends AppCompatActivity {
    ListView listView;
    List<String> PDF;
    List<String> PDFName;
    List<Boolean> isOwner;
    List<String> path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=findViewById(R.id.list_view);
        Intent i = getIntent();
        PDF = (List<String>)i.getSerializableExtra("PDF");
        PDFName = (List<String>)i.getSerializableExtra("PDFName");
        isOwner=(List<Boolean>) i.getSerializableExtra("isOwner");
        path=(List<String>) i.getSerializableExtra("path");
        DB.setListViewAdapter(PDFName,listView,SearchFileDisplayActivity.this,isOwner,path);
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
                startActivity(new Intent(SearchFileDisplayActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(SearchFileDisplayActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(SearchFileDisplayActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(SearchFileDisplayActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(SearchFileDisplayActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(SearchFileDisplayActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}