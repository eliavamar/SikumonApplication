package com.example.myapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {
    EditText email, pass,passRepeat;
    Button RegisterBtn;
    RadioGroup radio;
    Spinner department;
    DB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RegisterBtn=findViewById(R.id.RegisterBtn);
        email=findViewById(R.id.Email);
        pass=findViewById(R.id.Password);
        radio=findViewById(R.id.radio);
        passRepeat=findViewById(R.id.PasswordRepeat);
        department=findViewById(R.id.department);
        dataBase=DB.getInstance();
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Password.isValid(pass.getText().toString())){
                    if(radio.getCheckedRadioButtonId()==-1){
                        String error="Must choose between Student/Teacher!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(department.getSelectedItem().toString().equals("Choose Department")){
                        String error="Must choose Department!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!pass.getText().toString().equals(passRepeat.getText().toString())){
                        String error="Passwords don't match!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!Email.isValid(email.getText().toString())){
                        String error="Invalid Email address!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    RadioButton OrientationBt = (RadioButton) findViewById(radio.getCheckedRadioButtonId());
                    Email mail=new Email(email.getText().toString());
                    User user=new User(mail, pass.getText().toString(),OrientationBt.getText().toString(),department.getSelectedItem().toString());
                    dataBase.register(user,RegisterActivity.this);
                }else{
                    String error="Password must be consisted of 8-20 chars using\n at least 1 capital letter and special character!";
                    Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(RegisterActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(RegisterActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(RegisterActivity.this,PortalSearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(RegisterActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(RegisterActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}