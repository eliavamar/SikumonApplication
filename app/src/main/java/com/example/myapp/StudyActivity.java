package com.example.myapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyActivity extends AppCompatActivity {
    Spinner department_spinner,course_spinner;
    List<String> courses_list= new ArrayList<>();
    Button create;
    RadioGroup permissions, state;
    DB dataBase;
    Map<String,Object> studyGroupData=new HashMap<>();
    EditText comment,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        department_spinner= findViewById(R.id.department);
        course_spinner= findViewById(R.id.course);
        create=findViewById(R.id.uploadBtn);
        comment=findViewById(R.id.comment);
        link=findViewById(R.id.link);
        dataBase=DB.getInstance();
        permissions=findViewById(R.id.permission);
        DepartmentsManagement departmentsManagement =new DepartmentsManagement();
        List<String> departments_list= departmentsManagement.get_department_list();
        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,departments_list);
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        departmentsManagement.setSpinner(adapter_1,department_spinner,course_spinner, courses_list, adapter_2);
        state=findViewById(R.id.state);
        state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.online:
                        link.setVisibility(View.VISIBLE);
                        break;
                    case R.id.hybrid:
                        link.setVisibility(View.VISIBLE);
                        break;
                    case R.id.frontal:
                        link.setVisibility(View.INVISIBLE);
                        link.getText().clear();

                        break;
                    default:
                        System.err.println("Error not valid permission option!");
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissions.getCheckedRadioButtonId()==-1 || state.getCheckedRadioButtonId()==-1){
                    Toast.makeText(StudyActivity.this,"You must choose all fields!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(department_spinner.getSelectedItem().equals("Choose Department") || course_spinner.getSelectedItem().equals("Choose Course")){
                    Toast.makeText(StudyActivity.this,"You must choose Department and Course!",Toast.LENGTH_SHORT).show();
                    return;
                }
                fillMapData();
                dataBase.setStudyGroup(studyGroupData,StudyActivity.this);
            }
        });
    }
    public void fillMapData(){
        switch (state.getCheckedRadioButtonId()){
            case R.id.online:
                studyGroupData.put("State","Online");
                break;
            case R.id.hybrid:
                studyGroupData.put("State","Hybrid");
                break;
            case R.id.frontal:
                studyGroupData.put("State","Frontal");
                break;
            default:
                System.err.println("Error not valid state option!");
        }
        switch (permissions.getCheckedRadioButtonId()){
            case R.id.publicState:
                studyGroupData.put("Permission","Public");
                break;
            case R.id.privateState:
                studyGroupData.put("Permission","Private");
                break;
            default:
                System.err.println("Error not valid permission option!");
        }
        if(!TextUtils.isEmpty(link.getText().toString())) {
            studyGroupData.put("Link", link.getText().toString());
        }
        studyGroupData.put("Department",department_spinner.getSelectedItem().toString());
        studyGroupData.put("Course",course_spinner.getSelectedItem().toString());
        if (TextUtils.isEmpty(comment.getText().toString())){
            studyGroupData.put("Comment","N/A");
        }else {
            studyGroupData.put("Comment", comment.getText().toString());
        }
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
                startActivity(new Intent(StudyActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(StudyActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(StudyActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(StudyActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(StudyActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(StudyActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}