package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}