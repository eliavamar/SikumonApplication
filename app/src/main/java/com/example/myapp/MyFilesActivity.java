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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyFilesActivity extends AppCompatActivity {
    ListView listView;
    List<String> PDF;
    List<String> PDFName;
    DB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PDF=new ArrayList<>();
        PDFName=new ArrayList<>();
        listView=findViewById(R.id.list_view);
        dataBase=DB.getInstance();
        dataBase.myFileView(PDF,PDFName,listView, MyFilesActivity.this);
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
                startActivity(new Intent(MyFilesActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(MyFilesActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(MyFilesActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(MyFilesActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(MyFilesActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(MyFilesActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}