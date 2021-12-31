package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GroupKey = findViewById(R.id.GroupKey);
        Email = findViewById(R.id.Email);
        department_spinner = findViewById(R.id.department);
        searchGroupButton = findViewById(R.id.searchGroup);
        dataBase =DB.getInstance();
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
                    dataBase=DB.getInstance();
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
                startActivity(new Intent(SearchGroupsActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(SearchGroupsActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(SearchGroupsActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(SearchGroupsActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(SearchGroupsActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(SearchGroupsActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}