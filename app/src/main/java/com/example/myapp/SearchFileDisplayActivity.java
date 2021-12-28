package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}