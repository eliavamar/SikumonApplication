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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public Spinner department_spinner,course_spinner;
    public Button searchFileButton;
    public List<String> courses_list= new ArrayList<>();
    PDFFile pdf;
    DB dataBase;
    EditText FileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FileName=findViewById(R.id.FileName);
        department_spinner= findViewById(R.id.department);
        searchFileButton=findViewById(R.id.searchFile);
        List<String> departments_list= new ArrayList<>();
        dataBase=DB.getInstance();
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
                    String fileName,courseName,departmentName;
                    fileName=FileName.getText().toString();
                    courseName=course_spinner.getSelectedItem().toString();
                    departmentName=department_spinner.getSelectedItem().toString();
                    String path="PDF/"+departmentName+"/"+courseName;
                    pdf=new PDFFile(path,null);
                    dataBase.searchFile(fileName,pdf,SearchActivity.this);
                }
            }
        });
    }

    public void fill_spinner(){
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
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
                startActivity(new Intent(SearchActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(SearchActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(SearchActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(SearchActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(SearchActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(SearchActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
