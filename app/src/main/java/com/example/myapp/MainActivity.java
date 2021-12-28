package com.example.myapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView RegisterLink,ForgetPass;
    EditText PasswordField,EmailField;
    Button LoginBtn;
    DB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase=DB.getInstance();
        // Checking if user is connected already.
//        if(user!=null){
//            System.out.println(user);
//            startActivity(new Intent(MainActivity.this,PortalActivity.class));
//
//        }
        ForgetPass=findViewById(R.id.ForgetPass);
        RegisterLink=findViewById(R.id.RegisterLink);
        PasswordField=findViewById(R.id.PasswordField);
        EmailField=findViewById(R.id.EmailField);
        LoginBtn=findViewById(R.id.LoginBtn);
        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgetPassActivity.class));
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=PasswordField.getText().toString();
                String email=EmailField.getText().toString();
                if(validEmail(email) && validPassword(pass))
                    dataBase.signIn(email,pass,MainActivity.this);
                else{
                    Toast.makeText(MainActivity.this, "Sign-In failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }
    boolean validEmail(String email){
        if(TextUtils.isEmpty(EmailField.getText().toString())){
            return false;
        }
        return true;
    }
    boolean validPassword(String pass){
        if(TextUtils.isEmpty(PasswordField.getText().toString())){
            return  false;
        }
        return true;
    }
}