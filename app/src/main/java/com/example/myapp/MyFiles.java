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
    DatabaseReference databaseReference;
    List<String> PDF;
    List<String> PDFName;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        PDF=new ArrayList<>();
        PDFName=new ArrayList<>();
        listView=findViewById(R.id.list_view);
        mAuth = FirebaseAuth.getInstance();
        retrievePDFFiles();
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

    private void retrievePDFFiles() {
        databaseReference=FirebaseDatabase.getInstance().getReference("PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0)return;
                for (DataSnapshot dep:snapshot.getChildren()) {
                    for (DataSnapshot courses:dep.getChildren()) {
                        for (DataSnapshot file_data:courses.getChildren()) {
                            if(file_data.child("Email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                                    PDFName.add(file_data.getKey());
                                    PDF.add(file_data.child("URL").getValue(String.class));

                                }
                        }
                    }
                }
                ArrayAdapter arrayAdapter =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,PDFName)
                {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                       View view=super.getView(position, convertView, parent);
                        TextView textView=view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return  view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }
}