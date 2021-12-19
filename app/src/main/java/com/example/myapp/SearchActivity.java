package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public Spinner department_spinner,course_spinner;
    public Button searchFileButton;
    public List<String> courses_list= new ArrayList<>();
//    public PDFView pdfView;
    EditText FileName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FileName=findViewById(R.id.FileName);
        department_spinner= findViewById(R.id.department);
        searchFileButton=findViewById(R.id.searchFile);
        List<String> departments_list= new ArrayList<>();
        course_spinner= findViewById(R.id.course); departments_list.add("Choose Department");
        departments_list.add("CS");
        departments_list.add("Electrical Engineering");
        departments_list.add("Mechanical Engineering");
        departments_list.add("Structural Engineering");
        departments_list.add("Physiotherapy");
        departments_list.add("Psychology");
        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,departments_list);
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_spinner.setAdapter(adapter_1);
        department_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("CS")){
                    courses_list.clear();
                    courses_list.add("Data Bases");
                    courses_list.add("OS");
                    courses_list.add("OOP");
                    fill_spinner();

                }
                else if(adapterView.getItemAtPosition(i).equals("Electrical Engineering")){
                    courses_list.clear();
                    courses_list.add("Electro-magnetism");
                    courses_list.add("Control systems");
                    courses_list.add("Electricity Technology and Machines");
                    fill_spinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Mechanical Engineering")){
                    courses_list.clear();
                    courses_list.add("Manufacturing and Design");
                    courses_list.add("Dynamics");
                    courses_list.add("Instrumentation and Data Acquisition");
                    fill_spinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Structural Engineering")){
                    courses_list.clear();
                    courses_list.add("Computer-Aided Design of Structures");
                    courses_list.add("Computer Analysis of Structures");
                    courses_list.add("Storage and Industrial Structures");
                    fill_spinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Physiotherapy")){
                    courses_list.clear();
                    courses_list.add("Introduction to Treatment");
                    courses_list.add("General Surgery");
                    courses_list.add("Neurophysiology");
                    fill_spinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Psychology")){
                    courses_list.clear();
                    courses_list.add("General Psychology");
                    courses_list.add("History of Psychology");
                    courses_list.add("Statistics");
                    fill_spinner();
                }
                courses_list.add(0,"Choose Course");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        searchFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(department_spinner.getSelectedItem().toString().equals("Choose Department")){
                    Toast.makeText(SearchActivity.this,"Must choose department",Toast.LENGTH_LONG).show();
                }
                else if(course_spinner.getSelectedItem()==null||course_spinner.getSelectedItem().toString().equals("Choose Course")){
                    Toast.makeText(SearchActivity.this,"Must choose course",Toast.LENGTH_LONG).show();
                }
                else if(FileName.getText().toString().equals("")){
                    Toast.makeText(SearchActivity.this,"Must enter a file name",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(SearchActivity.this, activity_view.class);
                    String fileName,courseName,departmentName;
                    fileName=FileName.getText().toString();
                    courseName=course_spinner.getSelectedItem().toString();
                    departmentName=department_spinner.getSelectedItem().toString();
                    databaseReference= FirebaseDatabase.getInstance().getReference(departmentName).child(courseName).child(fileName);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,String> map=new HashMap<>();
                            for (DataSnapshot data:snapshot.getChildren()) {
                                map.put(data.getKey(),data.getValue(String.class));
                            }
                            Toast.makeText(SearchActivity.this, map.get("URL"), Toast.LENGTH_SHORT).show();
                            intent.putExtra("URL",map.get("URL")); //Put your id to your next Intent
                            startActivity(intent);
                            finish();
//                            new RetrievePdfStream().execute(map.get("URL"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SearchActivity.this, "Wrong path", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



    }
    public void fill_spinner(){
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
    }

//    class  RetrievePdfStream extends AsyncTask<String,Void, InputStream> {
//
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            InputStream inputStream= null;
//            try {
//                URL uRl=new URL(strings[0]);
//                HttpURLConnection urlConnection= (HttpURLConnection) uRl.openConnection();
//                if(urlConnection.getResponseCode()==200){
//                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
//
//                }
//            }
//            catch (IOException E){
//                return  null;
//            }
//            return inputStream;
//        }
//        protected void onPostExecute(InputStream inputStream){
//            PDFView.Configurator temp =pdfView.fromStream(inputStream);
//            if(temp==null)
//                Toast.makeText(SearchActivity.this,"null",Toast.LENGTH_LONG).show();
//            else
//                temp.load();
//        }
//    }
}
