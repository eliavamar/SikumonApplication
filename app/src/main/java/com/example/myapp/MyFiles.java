package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFiles extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<HashMap<String,String>> PDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        PDF=new ArrayList<>();
        listView=findViewById(R.id.list_view);
        retrievePDFFiles();
    }

    private void retrievePDFFiles() {
    }
}