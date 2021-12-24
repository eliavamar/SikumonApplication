package com.example.myapp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsManagement {
    List<String> departments_list = new ArrayList<>();
    public DepartmentsManagement(){
        departments_list.add("Choose Department");
        departments_list.add("CS");
        departments_list.add("Electrical Engineering");
        departments_list.add("Mechanical Engineering");
        departments_list.add("Structural Engineering");
        departments_list.add("Physiotherapy");
        departments_list.add("Psychology");
    }
    public  List<String> get_department_list(){
        return this.departments_list;
    }
    public void setSpinner(ArrayAdapter<String> adapter_1, Spinner department_spinner, Spinner course_spinner, List<String> courses_list, ArrayAdapter<String> adapter_2)
    {
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
                    fill_spinner(course_spinner,adapter_2);

                }
                else if(adapterView.getItemAtPosition(i).equals("Electrical Engineering")){
                    courses_list.clear();
                    courses_list.add("Electro-magnetism");
                    courses_list.add("Control systems");
                    courses_list.add("Electricity Technology and Machines");
                    fill_spinner(course_spinner,adapter_2);
                }
                else if(adapterView.getItemAtPosition(i).equals("Mechanical Engineering")){
                    courses_list.clear();
                    courses_list.add("Manufacturing and Design");
                    courses_list.add("Dynamics");
                    courses_list.add("Instrumentation and Data Acquisition");
                    fill_spinner(course_spinner,adapter_2);
                }
                else if(adapterView.getItemAtPosition(i).equals("Structural Engineering")){
                    courses_list.clear();
                    courses_list.add("Computer-Aided Design of Structures");
                    courses_list.add("Computer Analysis of Structures");
                    courses_list.add("Storage and Industrial Structures");
                    fill_spinner(course_spinner,adapter_2);
                }
                else if(adapterView.getItemAtPosition(i).equals("Physiotherapy")){
                    courses_list.clear();
                    courses_list.add("Introduction to Treatment");
                    courses_list.add("General Surgery");
                    courses_list.add("Neurophysiology");
                    fill_spinner(course_spinner,adapter_2);
                }
                else if(adapterView.getItemAtPosition(i).equals("Psychology")){
                    courses_list.clear();
                    courses_list.add("General Psychology");
                    courses_list.add("History of Psychology");
                    courses_list.add("Statistics");
                    fill_spinner(course_spinner,adapter_2);
                }
                courses_list.add(0,"Choose Course");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }
    public void fill_spinner(Spinner course_spinner, ArrayAdapter<String> adapter_2){
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
    }
}
