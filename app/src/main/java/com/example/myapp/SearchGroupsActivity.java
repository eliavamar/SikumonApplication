package com.example.myapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchGroupsActivity extends AppCompatActivity {
    public Spinner department_spinner,course_spinner;
    public Button searchGroupButton;
    public List<String> courses_list= new ArrayList<>();
    DB dataBase;
    EditText GroupKey,Email;
    int SearchOption=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_groups);
        GroupKey = findViewById(R.id.GroupKey);
        Email = findViewById(R.id.Email);
        department_spinner = findViewById(R.id.department);
        searchGroupButton = findViewById(R.id.searchGroup);
        dataBase = new DB();
        course_spinner = findViewById(R.id.course);
        DepartmentsManagement departmentsManagement =new DepartmentsManagement();
        List<String> departments_list= departmentsManagement.get_department_list();
        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,departments_list);
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        departmentsManagement.setSpinner(adapter_1,department_spinner,course_spinner, courses_list, adapter_2);
        searchGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (department_spinner.getSelectedItem().toString().equals("Choose Department")) {
                    Toast.makeText(SearchGroupsActivity.this, "Must choose department", Toast.LENGTH_LONG).show();
                } else if (course_spinner.getSelectedItem() == null || course_spinner.getSelectedItem().toString().equals("Choose Course")) {
                    Toast.makeText(SearchGroupsActivity.this, "Must choose course", Toast.LENGTH_LONG).show();
                }
                else {
                    String GroupKeyString,EmailString, courseName, departmentName;
                    GroupKeyString = GroupKey.getText().toString();
                    EmailString = Email.getText().toString();
                    courseName = course_spinner.getSelectedItem().toString();
                    departmentName = department_spinner.getSelectedItem().toString();
                    dataBase=new DB();

                    //dataBase.CheckGroupEmail(departmentName,courseName,EmailString,SearchOption,SearchGroupsActivity.this);
                    //startActivity(new Intent(SearchGroupsActivity.this,ViewStudyGroupsActivity.class));
                    if(!(TextUtils.isEmpty(GroupKey.getText().toString()))){
                        SearchOption=1;
                        dataBase.CheckGroupKey(departmentName,courseName,GroupKeyString,SearchOption,SearchGroupsActivity.this);
                    }
                    else if (!(TextUtils.isEmpty(Email.getText().toString()))){
                        SearchOption=2;
                        dataBase.CheckGroupEmail(departmentName,courseName,EmailString,SearchOption,SearchGroupsActivity.this);
                    }
                    else{
                        dataBase.CheckGroupAll(departmentName,courseName,GroupKeyString,SearchOption,SearchGroupsActivity.this);
                    }



                }
            }
        });
    }
}