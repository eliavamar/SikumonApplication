package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFiles extends AppCompatActivity {
    ListView listView;
    List<String> PDF;
    List<String> PDFName;
    DB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        PDF=new ArrayList<>();
        PDFName=new ArrayList<>();
        listView=findViewById(R.id.list_view);
        dataBase=new DB();
        dataBase.myFileView(PDF,PDFName,listView,MyFiles.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyFiles.this, activity_view.class);
                Toast.makeText(MyFiles.this, PDF.get(i), Toast.LENGTH_SHORT).show();
                intent.putExtra("URL",PDF.get(i)); //Put your id to your next Intent
                startActivity(intent);
                intent.setData(Uri.parse(PDF.get(i)));
                startActivity(intent);

            }
        });
    }


}