package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {
    Spinner department_spinner,course_spinner;
    List<String> courses_list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        department_spinner= findViewById(R.id.department);
        course_spinner= findViewById(R.id.course);

        List<String> departments_list= new ArrayList<>();
        //departments_list.add("Choose Department");
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
                    fillspinner();

                }
                else if(adapterView.getItemAtPosition(i).equals("Electrical Engineering")){
                    courses_list.clear();
                    courses_list.add("Electro-magnetism");
                    courses_list.add("Control systems");
                    courses_list.add("Electricity Technology and Machines");
                    fillspinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Mechanical Engineering")){
                    courses_list.clear();
                    courses_list.add("Manufacturing and Design");
                    courses_list.add("Dynamics");
                    courses_list.add("Instrumentation and Data Acquisition");
                    fillspinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Structural Engineering")){
                    courses_list.clear();
                    courses_list.add("Computer-Aided Design of Structures");
                    courses_list.add("Computer Analysis of Structures");
                    courses_list.add("Storage and Industrial Structures");
                    fillspinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Physiotherapy")){
                    courses_list.clear();
                    courses_list.add("Introduction to Treatment");
                    courses_list.add("General Surgery");
                    courses_list.add("Neurophysiology");
                    fillspinner();
                }
                else if(adapterView.getItemAtPosition(i).equals("Psychology")){
                    courses_list.clear();
                    courses_list.add("General Psychology");
                    courses_list.add("History of Psychology");
                    courses_list.add("Statistics");
                    fillspinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void fillspinner(){
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
    }
}