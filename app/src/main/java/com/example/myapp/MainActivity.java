package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView RegisterLink,ForgetPass;
    EditText PasswordField,EmailField;
    Button LoginBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mAuth = FirebaseAuth.getInstance();
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
                if(validEmail(email) && validPassword(pass)){
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(MainActivity.this, "Sign-In success.",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,PortalActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Sign-In failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
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