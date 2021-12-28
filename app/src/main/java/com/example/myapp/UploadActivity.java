package com.example.myapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    Spinner department_spinner,course_spinner;
    RadioGroup radioGroup;
    List<String> courses_list= new ArrayList<>();
    Button selectFileBut, uploadFileBut;
    TextView notification;
    EditText FileName;
    DB dataBase;
    Uri pdfUri; // uri are actually URLs that are meant for local storage
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        department_spinner= findViewById(R.id.department);
        mAuth=FirebaseAuth.getInstance();
        course_spinner= findViewById(R.id.course);
        uploadFileBut =findViewById(R.id.uploadBtn);
        selectFileBut =findViewById(R.id.selectFileBut);
        notification=findViewById(R.id.notification);
        FileName=findViewById(R.id.FileName);
        radioGroup =findViewById(R.id.permission);
        dataBase=DB.getInstance();
        List<String> departments_list= new ArrayList<>();
        //initialize Spinners
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


        //Choose PDF file
        selectFileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }
                else{
                    ActivityCompat.requestPermissions(UploadActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        uploadFileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile(pdfUri);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            selectPdf();
        else
            Toast.makeText(UploadActivity.this, "Check your permissions please", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void selectPdf() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher=
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int result=activityResult.getResultCode();
                            Intent data =activityResult.getData();
                            if(result == RESULT_OK && data != null&& data.getData() != null){
                                pdfUri=data.getData(); // return the uri of the selected  PDF
                                notification.setText(data.getData().toString());
                            }
                            else
                                Toast.makeText(UploadActivity.this,"Please select a PDF file",Toast.LENGTH_LONG).show();
                        }
                    });

    private void uploadFile(Uri pdfUri) {
        String fileName=FileName.getText().toString();
        String department_name = department_spinner.getSelectedItem().toString();
        if(department_name.equals("Choose Department")){
            Toast.makeText(UploadActivity.this,"Must choose department",Toast.LENGTH_LONG).show();
            return;
        }
        if(course_spinner.getSelectedItem()==null||course_spinner.getSelectedItem().toString().equals("Choose Course")){
            Toast.makeText(UploadActivity.this,"Must choose course",Toast.LENGTH_LONG).show();
            return;
        }
        if(fileName.equals("")){
            Toast.makeText(UploadActivity.this,"Must enter a file name",Toast.LENGTH_LONG).show();
            return;
        }
        if(pdfUri == null){
            Toast.makeText(UploadActivity.this,"Must choose PDF file",Toast.LENGTH_LONG).show();
            return;
        }
        if(radioGroup ==null|| radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(UploadActivity.this,"Must choose file's permissions",Toast.LENGTH_LONG).show();
            return;
        }
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton =  radioGroup.findViewById(radioButtonID);
        String selectedText =  radioButton.getText().toString();
        String [] path={"PDF",department_name,course_spinner.getSelectedItem().toString(),fileName};
        HashMap<String,String> fileProperties= new HashMap<String,String>();
        fileProperties.put("Status",selectedText);
        dataBase.upLoadFile(UploadActivity.this,path,fileProperties,pdfUri);
    }

    public void fill_spinner(){
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses_list);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_2);
    }
}