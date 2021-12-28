package com.example.myapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class ViewStudyGroupsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items;
    DB dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study_groups);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String department = (String)i.getSerializableExtra("Department");
        Intent j = getIntent();
        String course = (String)j.getSerializableExtra("Course");
        Intent z = getIntent();
        String ID = (String)z.getSerializableExtra("ID");
        Intent x = getIntent();
        int SearchOption = (int)x.getSerializableExtra("SearchOption");
        listView=findViewById(R.id.list_view);
        items=new ArrayList<>();
        dataBase=new DB();
        if(SearchOption==1){
            dataBase.GroupViewByKey(listView, department, course, ID ,items, ViewStudyGroupsActivity.this);
        }
        else if(SearchOption==2) {
            dataBase.GroupViewByEmail(listView, department, course, ID, items,ViewStudyGroupsActivity.this);
        }
        else{
            dataBase.AllGroupView(listView, department, course, items,ViewStudyGroupsActivity.this);
        }
    }
}