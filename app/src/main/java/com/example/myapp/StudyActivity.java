package com.example.myapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        List<String> departments_list= new ArrayList<>();
        departments_list.add("Choose Department");
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
    public void fill_spinner(){
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
    }

}